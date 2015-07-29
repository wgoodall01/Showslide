package view;

import pkg.Image;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PShape;

import java.util.List;


public class Slideshow extends View{

    //List<Image> imgs, PApplet pa inherited
    int index = 0;
    boolean pMousePressed;
    PShape arrow;


    /**
     * Creates a slideshow with the selected images and PApplet.
     * @param imgs images to show
     * @param pa PApplet to load images, etc with
     */
    public Slideshow(List<Image> imgs, PApplet pa) {
        super(imgs, pa);
        arrow = pa.loadShape("arrow.svg");
    }

    /**
     * Creates a slideshow with the selected images and PApplet starting at the specified index.
     * @param imgs images to show
     * @param pa PApplet to load images, etc with
     * @param index index of the default shown image
     */
    public Slideshow(List<Image> imgs, PApplet pa, int index){
        this(imgs, pa);
        this.index = index;
    }

    @Override
    public PGraphics getView() {
        PGraphics pg = pa.createGraphics(pa.width, pa.height);

        pg.beginDraw();//start draw
        pg.background(0);

        PGraphics ci = imgs.get(index).getImage();

        float imgWidth, imgHeight;
        float wRatio = (float) ci.width / (float) pa.width;
        float hRatio = (float) ci.height / (float) pa.height;

        if(wRatio > hRatio){
            imgHeight =  (ci.height/wRatio);
            imgWidth =   pa.width;
        }else{
            imgWidth =  (ci.width / hRatio);
            imgHeight = (pa.height);
        }

        pg.imageMode(PConstants.CENTER);
        pg.image(
                ci,
                pa.width/2,
                pa.height/2,
                imgWidth,
                imgHeight
        );

        //TODO filters & stuff

        //draw arrows
        leftArrow(pg);
        rightArrow(pg);

        pg.endDraw();//end draw

        //update pMousePressed
        pMousePressed = pa.mousePressed;

        return pg;
    }


    private void leftArrow(PGraphics pg){
        pg.pushStyle();
        if(pa.mouseX < 55){ //if mouse is on arrow

            //set formatting
            pg.fill(150, 150, 150, 150);
            pg.noStroke();

            //draw highlight
            pg.rect(0, 0, 55, pa.height);

            //if mouse is clicked
            if(pa.mousePressed && !pMousePressed){
                if(imgs.size() -1 == index){
                    index=0; //loop back around
                }else{
                    index++; //increment by 1
                }
            }
        }
        pg.shape(arrow, 20, pa.height/2 - 25,  25, 50);
        pg.noTint();
        pg.popStyle();
    }

    private void rightArrow(PGraphics pg) {
        pg.pushStyle();
        if(pa.mouseX > pa.width - 55){ //If mouse is on arrow

            //set formatting
            pg.fill(150, 150, 150, 150);
            pg.noStroke();

            //draw highlight
            pg.rect(pa.width - 55, 0, pa.width, pa.height);

            //if mouse is pressed
            if(pa.mousePressed && !pMousePressed){
                if(index == 0){
                    index = imgs.size()-1; //loop back around
                }else{
                    index--; //increment
                }
            }
        }
        pg.shape(arrow, pa.width - 20, pa.height/2 - 25, -25, 50);
        pa.noTint();
        pg.popStyle();
    }
}
