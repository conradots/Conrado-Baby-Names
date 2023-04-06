/**
 * ImageInversion creates new images that are photographic negatives (or inverted images) of selected images and save these new images
 * 
 * @author Ginny Dang
 * @version July 7th, 2022
 */

import edu.duke.*;
import java.io.*;

public class ImageInversion {
    public ImageResource makeInversion(ImageResource inImage) {
        ImageResource outImage = new ImageResource(inImage.getWidth(), inImage.getHeight());
        for (Pixel pixel : outImage.pixels()) {
            Pixel inPixel = inImage.getPixel(pixel.getX(), pixel.getY());
            pixel.setRed(255 - inPixel.getRed());
            pixel.setGreen(255 - inPixel.getGreen());
            pixel.setBlue(255 - inPixel.getBlue());
        }
        return outImage;
    }
    
    public void selectAndConvert () {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            // Make Inverted copy
            ImageResource inImage = new ImageResource(f);
            ImageResource inverted = makeInversion(inImage);
            // Draw and Save Inverted copy
            String fname = inImage.getFileName();
            String newName = "inverted-" + fname;
            inverted.setFileName("./images/" + newName);
            inverted.draw();
            inverted.save();
        }
    }
}