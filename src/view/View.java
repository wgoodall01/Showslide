package view;

import pkg.Image;
import processing.core.PApplet;
import processing.core.PGraphics;

import java.util.List;

/**
 * Defines how other classes may implement View to be drawn to the screen by Main.
 *
 * @author William Goodall
 */
public abstract class View {
    List<Image> imgs;
    PApplet pa;

    /**
     * Creates a new view, using the specified PApplet and images to display.
     * @param imgs Images to display
     * @param   pa PApplet
     */
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
