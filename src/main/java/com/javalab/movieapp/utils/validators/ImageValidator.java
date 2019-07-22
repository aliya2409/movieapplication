package com.javalab.movieapp.utils.validators;

import javax.imageio.ImageIO;
import java.io.InputStream;


public class ImageValidator {

    public static InputStream validateImage(InputStream image) throws IncorrectImageTypeException {
        try {
            //checking if it's an image (only BMP, GIF, JPG and PNG are recognized).
            ImageIO.read(image).toString();
            return image;
        } catch (Exception e) {
            throw new IncorrectImageTypeException();
        }
    }
}
