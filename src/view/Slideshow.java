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
    boolean pMousePressed = false;
    boolean pKeyPressed = false;
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


        //Scale image to fit window
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
        drawArrow(pg, true);
        drawArrow(pg, false);

        checkInterface();

        pg.endDraw();//end draw

        return pg;
    }

    /**
     * Checks whether any buttons have been clicked, keys pressed, etc.
     * Called every frame.
     */
    private void checkInterface(){

        //check for mouse pressed
        if(pa.mousePressed && !pMousePressed){
            if(pa.mouseX < 55){ //on left arrow
                incrementSlideshow(-1);
            }
            if(pa.mouseX > pa.width - 55){ //on right arrow
                incrementSlideshow(1);
            }
        }

        //check for key pressed
        if(pa.keyPressed && !pKeyPressed){
            if(pa.keyCode == PConstants.LEFT){ //left arrow key
                incrementSlideshow(-1);
            }
            if(pa.keyCode == PConstants.RIGHT){ //right arrow key
                incrementSlideshow(1);
            }
            if(pa.keyCode == PConstants.DOWN){
                index = 0;
            }
            if(pa.keyCode == PConstants.UP){
                index = imgs.size() -1;
            }
        }


        pMousePressed = pa.mousePressed;
        pKeyPressed = pa.keyPressed;
    }

    /**
     * Increments the slideshow by the specified amount, looping back to the start if it reaches the end.
     * @param i amount to increment by.
     */
    private void incrementSlideshow(int i){
        index += i;
        if(index < 0) index =  index + imgs.size();
        else if(index >= imgs.size()) index =  index % imgs.size();
    }

    /**
     * Draws an arrow to the screen, and highlights it if the mouse is over it.
     * @param pg PGraphics object to draw arrows to
     * @param isLeftSide True if the arrow is on the left side, false if it's on the right.
     */
    private void drawArrow(PGraphics pg, boolean isLeftSide) {
        pg.pushStyle();

        //draw arrow
        float aXPos  = isLeftSide ? 20 : pa.width - 20;
        float aWidth = isLeftSide ? 25 : -25;
        pg.shape(arrow, aXPos, pa.height/2 - 25, aWidth, 50);

        //draw highlight
        pg.fill(200, 50);
        pg.noStroke();
        float hXPos = isLeftSide ? 0 : pa.width-55;
        pg.rect(hXPos, 0, 55, pa.height);

        pg.popStyle();
    }
}
