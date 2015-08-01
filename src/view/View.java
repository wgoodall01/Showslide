package view;

import pkg.Image;
import processing.core.PApplet;
import processing.core.PGraphics;

import java.util.List;


public abstract class View {
    List<Image> imgs;
    PApplet pa;

    public View(List<Image> imgs, PApplet pa){
        this.imgs = imgs;
        this.pa = pa;
    }

    /**
     * Called every frame, similar to draw() except it draws to a PGraphics object which it then returns.
     * @return PGraphics object containing the frame.
     */
    public abstract PGraphics getViewport();

    /**
     * Called every frame. Main will display the view returned from this method.
     * To keep the view unchanged, return self.
     * @return View to display next frame
     */
    public abstract View getNewView();
}
