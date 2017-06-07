package be.abyx.aurora;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import java.util.Stack;

/**
 * This class provides several methods for advanced image manipulation.
 *
 * @author Pieter Verschaffelt
 */
public class ImageUtils {
    private Context context;

    public ImageUtils(Context context) {
        this.context = context;
    }

    /**
     * This function will cut out logo's or other images based upon a full coloured background out
     * of the background. This means that coloured borders and corners can be automatically removed
     * from an image.
     *
     * @param input A Bitmap that should be automatically cropped.
     * @param colour The colour of the edges that should be removed.
     */
    public void magicCrop(Bitmap input, int colour) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = false;

        Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.raw.aurora_fancy_template, opts);

        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();

        int[] pixels = new int[width * height];
        originalBitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        // We're working with a Stack-based approach of the Flood Fill algorithm to determine
        // the edges that should get removed.
        Stack<ImageCoordinate> coordinates = new Stack<>();
        // Start filling from the four corners of the image
        coordinates.push(new ImageCoordinate(0, 0));
        coordinates.push(new ImageCoordinate(0, height - 1));
        coordinates.push(new ImageCoordinate(width - 1, height - 1));
        coordinates.push(new ImageCoordinate(width - 1, 0));

        while (!coordinates.isEmpty()) {
            ImageCoordinate current = coordinates.pop();

            if (pixels[current.getY() * height + current.getX()] == colour) {
                pixels[current.getY() * height + current.getX()] = Color.TRANSPARENT;

                // Reuse current ImageCoordinate-object.
                current.setY(current.getY() + 1);
                coordinates.push(current);
                coordinates.push(new ImageCoordinate(current.getX(), current.getY() - 1));
                coordinates.push(new ImageCoordinate(current.getX() + 1, current.getY()));
                coordinates.push(new ImageCoordinate(current.getX() - 1, current.getY()));
            }
        }

        input.setPixels(pixels, 0, width, 0, 0, width, height);
    }
}
