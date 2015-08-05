package pkg;

import processing.core.PApplet;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Runnable in a separate thread, the ImageLoader will recursively
 * load all images in a directory and every one of its subdirectories.
 *
 * @author William Goodall
 */
class ImageLoader implements Runnable{

    private static final String[] extensions = {"jpg", "jpeg", "png", "gif", "tga"};

    private final File dir;
    private final PApplet pa;

    private final List<Image> images;


    /**
     * Creates a new ImageLoader in the specified directory.
     * @param    dir Directory to load the images from
     * @param images List to add images to
     * @param     pa PApplet
     */
    public ImageLoader(File dir, List<Image> images, PApplet pa){
        this.dir = dir;
        this.images = images;
        this.pa = pa;
    }

    /**
     * Starts the loader.
     */
    @Override
    public void run() {
        loadImagesInDir(dir);
        System.out.println("Done loading images");
    }

    /**
     * Adds all the files with an extension in extensions to
     * images. Recursively calls itself on any directories it
     * encounters.
     *
     * @param dir the directory to load images from
     */
    private void loadImagesInDir(File dir){
        try {
            for (File f : dir.listFiles()) {
                if (f.isFile()) {
                    //Get file extension and check if it's compatible
                    String extension = "";
                    int i = f.getName().lastIndexOf('.');
                    if (i > 0) {
                        extension = f.getName().substring(i + 1);
                    }

                    //if it's compatible, load the image and add Image instance to list
                    if (Arrays.asList(extensions).contains(extension)) {
                        images.add(new Image(f.getPath(), pa));
                        System.out.println("Loaded image: " + f.getPath());
                    }
                } else if (f.isDirectory()) {// If file is a directory
                    loadImagesInDir(f);
                }
            }
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }
}
