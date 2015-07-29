package pkg;

import processing.core.*;
import view.Slideshow;
import view.View;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main extends PApplet{
    public static File imgDir = new File("imgs");
    List<Image> images = new ArrayList<>();

    Thread loaderThread;

    View view;

    float animIndex = 0;

    public void setup(){
        size(500, 500, P2D);
        frameRate(120);

        //make window resizable if running as an app
        if (frame != null) {
            frame.setResizable(true);
        }

        //choose folder
        chooseFolder();
        println("Folder: " + imgDir.getPath());

        //import all images in image dir, using separate thread
        ImageLoader il = new ImageLoader(imgDir, images, this);
        loaderThread = new Thread(il, "Image Loader");
        loaderThread.start();

        //setup slideshow view
        view = new Slideshow(images, this);
    }

    public void draw(){
        if(!loaderThread.isAlive()){ //Images are loaded and ready
            image(view.getView(), 0, 0, width, height);
        }else{ //Images are being loaded
            pushStyle();
            background(0, 200, 200);
            float radius = map(sin(animIndex), -1, 1, 0, 200);
            ellipse(width/2, height/2, radius, radius);
            animIndex += 0.01;

            textSize(20);
            text("Loading Images...", 10, height-10);
            popStyle();
        }
    }

    private void chooseFolder(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fileChooser.showOpenDialog(frame);

        if(returnVal == JFileChooser.APPROVE_OPTION){ //set file dir if they approve it
            imgDir = fileChooser.getSelectedFile();
        }
    }

    public static void main(String args[]){
        PApplet.main(new String[] {
                pkg.Main.class.getName()
        });
    }

}
