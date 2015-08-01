package pkg;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

public class Image {

    public final String imgPath;
    private PApplet pa;

    PGraphics pg;
    PImage img;
    List<Filter> filters = new ArrayList<>();
    List<Tint> tints = new ArrayList<>();

    boolean isCached = false;
    float pWidth, pHeight;
    boolean pThumb = false;

    /**
     * Creates a new image with the specified image location and PApplet.
     * @param imgPath the path of the desired image
     * @param pa the PApplet to draw on
     */
    public Image(String imgPath, PApplet pa){
        this.imgPath = imgPath;
        this.pa = pa;
        img = pa.loadImage(imgPath);
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
        //break cache if anything changes
        if(width != pWidth || height != pHeight || isThumb != pThumb){isCached = false;}

        if(isCached){
            return pg; //if we have a cached copy, just use it.
        } else { //if not, start over
            //load and initialize image
            pg = pa.createGraphics((int)width, (int)height);
            pg.beginDraw();

            //apply tints to PGraphics - can only come before image
            for (Tint t : tints) {
                pg.tint(t.r, t.b, t.g);
            }

            //Scale image to fit window
            float imgWidth, imgHeight;
            float wRatio = (float) img.width / (float) pg.width;
            float hRatio = (float) img.height / (float) pg.height;

            boolean comp = isThumb ? wRatio < hRatio : wRatio > hRatio;

            if(comp){
                imgHeight =  (img.height/wRatio);
                imgWidth =   pg.width;
            }else{
                imgWidth =  (img.width / hRatio);
                imgHeight = (pg.height);
            }

            //Draws image
            pg.imageMode(PConstants.CENTER);
            pg.image(img, pg.width/2, pg.height/2, imgWidth, imgHeight);

            //apply filters to PGraphics - can only come after
            for (Filter f : filters) {
                if (f.filterType == PConstants.GRAY) {
                    pg.filter(PConstants.GRAY);
                } else {
                    pg.filter(f.filterType, f.param);
                }
            }

            pg.endDraw();
            isCached = true;
            pWidth = width;
            pHeight = height;
            pThumb = isThumb;
            return pg;
        }
    }
    public PGraphics getImage(float width, float height){return getImage(width, height, false);}
    public PGraphics getImage(){return getImage(pa.width, pa.height);}

    /**
     * Adds a tint to the image.
     * @param r Red value for tint
     * @param g Blue value for tint
     * @param b Green value for tint
     */
    public void addTint(float r, float g, float b){
        tints.add(new Tint(r, g, b));
        isCached = false;
    }

    /**
     * Adds a filter to the image.
     * @param filterType Type of filter, e.g. PConstants.GRAY
     * @param param Filter parameter
     */
    public void addFilter(int filterType, float param){
        filters.add(new Filter(filterType, param));
        isCached = false;
    }

    private class Filter{
        public int filterType;
        public float param;

        public Filter(int filterType, float param){
            this.filterType = filterType;
            this.param = param;
        }
    }
    private class Tint{
        float r, g, b;

        public Tint(float r, float g, float b){
            this.r = r;
            this.b = b;
            this.g = g;
        }
    }

}
