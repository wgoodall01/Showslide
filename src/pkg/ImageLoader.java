package pkg;

import processing.core.PApplet;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ImageLoader implements Runnable{

    public static final String[] extensions = {"jpg", "jpeg", "png", "gif", "tga"};

    private File dir;
    private PApplet pa;

    public List<Image> images;

    public ImageLoader(File dir, List<Image> images, PApplet pa){
        this.dir = dir;
        this.images = images;
        this.pa = pa;
    }

    @Override
    public void run() {
        loadImagesInDir(dir);

        System.out.println("Done loading images");

    }

    private void loadImagesInDir(File dir){
        for(File f : dir.listFiles()){
            if(f.isFile()){
                //Get file extension and check if it's compatible
                String extension = "";
                int i = f.getName().lastIndexOf('.');
                if (i > 0) {
                    extension = f.getName().substring(i+1);
                }

                //if it's compatible, load the image and add Image instance to list
                if(Arrays.asList(extensions).contains(extension)){
                    images.add(new Image(f.getPath(), pa));
                    System.out.println("Loaded image: " + f.getPath());
                }
            }else if(f.isDirectory()){// If file is a directory
                loadImagesInDir(f);
            }
        }
    }
}
