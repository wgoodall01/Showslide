package pkg;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;


/**
 * Represents an image, and is responsible for loading it and writing it, with filters, to a buffer.
 *
 * @author William Goodall
 */
public class Image {
    /**
     * By default, all images will have this tint across r, g, and b;
     */
    public static final int defaultTint = 255;

    /**
     * By default, all images will have this amount of blur
     */
    public static final int defaultBlur = 0;

    private final PApplet pa;
    private PGraphics pg;
    private final PImage img;
    private int r, g, b; //filter values

    private int blur; //blur amt

    private boolean isCached = false;
    private float pWidth, pHeight;
    private boolean pThumb = false;



    /**
     * Creates a new image with the specified image location and PApplet.
     * @param imgPath the path of the desired image
     * @param pa the PApplet to draw on
     */
    public Image(String imgPath, PApplet pa){
        this.pa = pa;
        img = pa.loadImage(imgPath);

        //init tints and blur to default
        r = g = b = defaultTint;
        blur = defaultBlur;
    }

    /**
     * Returns a PGraphics object with the image drawn to it, complete with any
     * filters or adjustments.
     *
     * @return PGraphics with image drawn to it.
     * @param width width of the container to draw the image to
     * @param height height of the container to draw the image to
     */
    public PGraphics getImage(float width, float height, boolean isThumb){
        //break cache if anything changed
        if(width != pWidth || height != pHeight || isThumb != pThumb){isCached = false;}

        if(isCached){
            return pg; //if we have a cached copy, just use it.
        } else { //if not, start over
            //load and initialize image
            pg = pa.createGraphics((int)width, (int)height);
            pg.beginDraw();

            //apply filter to PGraphics - can only come before image
            pg.tint(r, b, g);

            //Scale image to fit window
            float imgWidth, imgHeight;
            float wRatio = (float) img.width / (float) pg.width;
            float hRatio = (float) img.height / (float) pg.height;

            //Decides how to scale - e.g. whether to fill the bounding box or fit in it.
            boolean comp = isThumb ? wRatio < hRatio : wRatio > hRatio;

            if(comp){
                imgHeight = (img.height/wRatio);
                imgWidth = pg.width;
            }else{
                imgWidth = (img.width / hRatio);
                imgHeight = pg.height;
            }

            //Draws image
            pg.imageMode(PConstants.CENTER);
            pg.image(img, pg.width/2, pg.height/2, imgWidth, imgHeight);

            //adds blur
            pg.filter(PConstants.BLUR, blur);

            pg.endDraw();

            //set cache values
            isCached = true;
            pWidth = width;
            pHeight = height;
            pThumb = isThumb;
            return pg;
        }
    }
    public PGraphics getImage(float width, float height){return getImage(width, height, false);}

    //R, G, B, getters and setters
    public int getR() {return r;}
    public void setR(int r) {
        isCached = false;
        this.r = r;
    }

    public int getG() {return g;}
    public void setG(int g) {
        isCached = false;
        this.g = g;
    }

    public int getB() {return b;}
    public void setB(int b) {
        isCached = false;
        this.b = b;
    }


    public int getBlur() {return blur;}

    public void setBlur(int blur) {
        isCached =  false;
        this.blur = blur;
    }

}
