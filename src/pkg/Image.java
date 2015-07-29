package pkg;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Image {

    public final String imgPath;
    private PApplet pa;

    PGraphics pg;
    PImage img;
    List<Filter> filters = new ArrayList<>();
    List<Tint> tints = new ArrayList<>();

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
     */
    public PGraphics getImage(){
        //load and initialize image
        PGraphics pg = pa.createGraphics(img.width, img.height);
        pg.beginDraw();

        //apply tints to PGraphics - can only come before image
        for(Tint t : tints){
            pg.tint(t.r, t.b, t.g);
        }

        //Draws image
        pg.image(img, 0, 0);

        //apply filters to PGraphics - can only come after
        for(Filter f : filters){
            if(f.filterType == PConstants.GRAY){pg.filter(PConstants.GRAY);}
            else{pg.filter(f.filterType, f.param);}
        }

        pg.endDraw();
        return pg;
    }

    /**
     * Adds a tint to the image.
     * @param r Red value for tint
     * @param g Blue value for tint
     * @param b Green value for tint
     */
    public void addTint(float r, float g, float b){
        tints.add(new Tint(r, g, b));
    }

    /**
     * Adds a filter to the image.
     * @param filterType Type of filter, e.g. PConstants.GRAY
     * @param param Filter parameter
     */
    public void addFilter(int filterType, float param){
        filters.add(new Filter(filterType, param));
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
