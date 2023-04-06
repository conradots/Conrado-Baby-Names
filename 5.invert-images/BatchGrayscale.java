/**
 * BatchGrayscale batch processes several images, creates and saves new images (with new filenames) that are grayscale versions of each image.
 * 
 * @author Ginny Dang
 * @version July 7th, 2022
 */

import edu.duke.*;
import java.io.*;

public class BatchGrayscale {
    public ImageResource makeGray(ImageResource inImage) {
        ImageResource outImage = new ImageResource(inImage.getWidth(), inImage.getHeight());
        for (Pixel pixel : outImage.pixels()) {
            Pixel inPixel = inImage.getPixel(pixel.getX(), pixel.getY());
            int average = (inPixel.getRed() + inPixel.getBlue() + inPixel.getGreen())/3;
            pixel.setRed(average);
            pixel.setGreen(average);
            pixel.setBlue(average);
        }
        return outImage;
    }
    
    public void selectAndConvert () {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            // Make Gray copy
            ImageResource inImage = new ImageResource(f);
            ImageResource gray = makeGray(inImage);
            // Draw and Save Gray copy
            String fname = inImage.getFileName();
            String newName = "gray-" + fname;
            gray.setFileName("./images/" + newName);
            gray.draw();
            gray.save();
        }
    }
}