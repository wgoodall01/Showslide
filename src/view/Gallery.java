package view;

import pkg.Image;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;

import java.util.List;

import static java.lang.Math.abs;

public class Gallery extends View {

    private final static int thumbSize = 100;
    //List<Image> imgs, PApplet pa

    private PGraphics strip, viewport;

    private int slideIndex = -1; //if <0, keep on slideshow. If not, go to slideshow at that index.

    private float scroll =  0;
    private float scrollVel = 0;
    private static final float scrollAccel = 10f; // px/s/s (I think...)


    public Gallery(List<Image> imgs, PApplet pa) {
        super(imgs, pa);
    }

    @Override
    public PGraphics getViewport() {
        checkInterface();
        updateStrip();
        updateHighlights();
        updateScroll();
        updateView();
        return viewport;
    }

    @Override
    public View getNewView() {
        if(slideIndex >= 0){
            return new Slideshow(imgs, pa, slideIndex);
        }else{
            return this;
        }
    }

    /**
     * Updates the viewport
     */
    private void updateView(){
        //Redraw view
        viewport = pa.createGraphics(pa.width, pa.height);
        viewport.beginDraw();
        viewport.background(0);
        viewport.image(strip, (viewport.width - strip.width) / 2, scroll);
        viewport.endDraw();
    }

    /**
     * Updates scroll value using momentum
     */
    private void updateScroll(){
        //Update scrolling
        scroll += scrollVel;
        float decel = scrollAccel*0.3f/pa.frameRate;
        if(abs(scrollVel) > 0.01) {
            scrollVel += scrollVel > 0 ? -decel : decel;
        }

        final float scrollBound = thumbSize-25; //distance from the end of the strip where scrolling will bounce
        final float bounce = abs(scrollVel) / 2; //magnitude of the bounce

        if(scroll < 0-strip.height + scrollBound){ //if user scrolls off the top
            scroll = 0-strip.height + scrollBound;
            scrollVel = bounce;
        }else if(scroll > pa.height - scrollBound){ //if user scrolls off the bottom
            scroll = viewport.height - scrollBound;
            scrollVel = -bounce;
        }
    }

    /**
     * Draws a highlight to the image the mouse pointer is over.
     * If an image is clicked, go to it in the slideshow.
     */
    private void updateHighlights(){
        strip.beginDraw();

        //loop through where the thumbs will be
        int i = 0;
        for(int y = 0; y < strip.height; y += thumbSize){
            for(int x = 0; x < strip.width; x += thumbSize){
                if(i < imgs.size()-1) {
                    if(pa.mouseY - scroll > y && pa.mouseY - scroll < y+thumbSize &&
                       pa.mouseX > x && pa.mouseX < x + thumbSize){
                        //if hivering over a thumb @ x, y

                        strip.pushStyle();
                        strip.strokeWeight(2);
                        strip.noFill();
                        strip.stroke(245);
                        strip.rect(x, y, thumbSize, thumbSize);
                        strip.stroke(10);
                        strip.rect(x-2, y-2, thumbSize + 4, thumbSize + 4);
                        strip.popStyle();

                        if(pa.mousePressed){
                            slideIndex = i; // go to slideshow
                        }
                    }
                    i++;
                }
            }
        }

        strip.endDraw();

    }

    /**
     * Draws all images to the strip taking into consideration the width of the window.
     */
    private void updateStrip(){
        //setup strip params
        int thumbsPerRow = pa.width / thumbSize;
        if(thumbsPerRow < 1) thumbsPerRow = 1; //make sure that there is at least 1 thumb per row
        int thumbsLength = (imgs.size() / thumbsPerRow) + 1;

        //init the strip
        strip = pa.createGraphics(thumbsPerRow * thumbSize, thumbsLength * thumbSize);
        strip.beginDraw();

        //draw all imgs to strip
        int i = 0;
        for(int y = 0; y < strip.height; y += thumbSize){
            for(int x = 0; x < strip.width; x += thumbSize){
                if(i < imgs.size()-1) {
                    strip.image(imgs.get(i).getImage(thumbSize, thumbSize, true), x, y);
                    i++;
                }
            }
        }
        strip.endDraw();
    }

    /**
     * Checks for the up and down keys, and changes the scroll velocity accordingly
     */
    private void checkInterface(){
        if(pa.keyPressed){
            if(pa.keyCode == PConstants.DOWN){
                scrollVel += scrollAccel / pa.frameRate;
            }else if(pa.keyCode == PConstants.UP){
                scrollVel -= scrollAccel / pa.frameRate;
            }
        }
    }
}
