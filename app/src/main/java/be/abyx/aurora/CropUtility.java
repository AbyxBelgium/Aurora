package be.abyx.aurora;

import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.Stack;

/**
 * Provides several different means for cropping a Bitmap.
 *
 * @author Pieter Verschaffelt
 */
public class CropUtility {
    /**
     * This function will cut out logo's or other images based upon a full coloured background out
     * of the background. This means that coloured borders and corners can be automatically removed
     * from an image.
     *
     * @param input A Bitmap that should be automatically cropped.
     * @param colour The colour of the edges that should be removed.
     * @param tolerance How much a colour can deviate from the real given value and should still
     *                  be removed (A value between 0 and 1 where 1 indicates full tolerance).
     * @return Bitmap A new Bitmap whose corners are removed.
     */
    public Bitmap magicCrop(Bitmap input, int colour, float tolerance) {
        int width = input.getWidth();
        int height = input.getHeight();

        int[] pixels = new int[width * height];
        input.getPixels(pixels, 0, width, 0, 0, width, height);

        // We're working with a Stack-based approach of the Flood Fill algorithm to determine
        // the edges that should get removed.
        Stack<ImageCoordinate> coordinates = new Stack<>();
        // Start filling from the four corners of the image
        coordinates.push(new ImageCoordinate(0, height - 1));
        coordinates.push(new ImageCoordinate(width - 1, height - 1));
        coordinates.push(new ImageCoordinate(width - 1, 0));
        coordinates.push(new ImageCoordinate(0, 0));

        int referenceRed = Color.red(colour);
        int referenceGreen = Color.green(colour);
        int referenceBlue = Color.blue(colour);
        int sum = referenceBlue + referenceGreen + referenceRed;
        float threshold = 3 * 255 * tolerance;

        while (!coordinates.isEmpty()) {
            ImageCoordinate current = coordinates.pop();

            if (validPosition(current.getX(), current.getY(), width, height)) {
                int pixel = pixels[current.getY() * width + current.getX()];
                int red = Color.red(pixel);
                int blue = Color.blue(pixel);
                int green = Color.green(pixel);

                float difference = Math.abs((red + blue + green) - sum);

                if (difference <= threshold) {
                    pixels[current.getY() * width + current.getX()] = Color.TRANSPARENT;

                    // Reuse current ImageCoordinate-object.
                    coordinates.push(new ImageCoordinate(current.getX() + 1, current.getY()));
                    coordinates.push(new ImageCoordinate(current.getX() - 1, current.getY()));
                    coordinates.push(new ImageCoordinate(current.getX(), current.getY() - 1));
                    current.setY(current.getY() + 1);
                    coordinates.push(current);
                }
            }
        }

        Bitmap output = input.copy(input.getConfig(), true);
        output.setHasAlpha(true);
        output.setPixels(pixels, 0, width, 0, 0, width, height);

        return output;
    }

    protected boolean validPosition(int x, int y, int width, int height) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    /**
     * Automatically remove white rectangler border from around an image. This is different from
     * magicCrop as changes the canvas size of the image and does not make pixels transparent.
     *
     * @param input
     * @param colour
     * @param tolerance
     * @return
     */
    public Bitmap rectangularCrop(Bitmap input, int colour, float tolerance) {
        int width = input.getWidth();
        int height = input.getHeight();

        int[] pixels = new int[width * height];
        input.getPixels(pixels, 0, width, 0, 0, width, height);

        int rowStart = 0;
        int colStart = 0;

        int rowEnd = 0;
        int colEnd = 0;

        float treshold = 3 * 255 * tolerance;

        int referenceRed = Color.red(colour);
        int referenceGreen = Color.green(colour);
        int referenceBlue = Color.blue(colour);
        int sum = referenceBlue + referenceGreen + referenceRed;

        // First we have to determine the four edges of the output Bitmap.
        for (int row = 0; row < input.getHeight(); row++) {
            for (int col = 0; col < input.getWidth(); col++) {
                int pixel = pixels[row * width + col];
                int red = Color.red(pixel);
                int blue = Color.blue(pixel);
                int green = Color.green(pixel);

                float difference = Math.abs((red + blue + green) - sum);

                if (difference > treshold) {
                    rowStart = Math.max(row, 0);
                }
            }
        }

        for (int col = 0; col < input.getWidth(); col++) {
            for (int row = 0; row < input.getHeight(); row++) {
                int pixel = pixels[row * width + col];
                int red = Color.red(pixel);
                int blue = Color.blue(pixel);
                int green = Color.green(pixel);

                float difference = Math.abs((red + blue + green) - sum);

                if (difference > treshold) {
                    colStart = Math.max(col, 0);
                }
            }
        }

        for (int row = input.getHeight() - 1; row >= 0; row--) {
            for (int col = 0; col < input.getWidth(); col++) {
                int pixel = pixels[row * width + col];
                int red = Color.red(pixel);
                int blue = Color.blue(pixel);
                int green = Color.green(pixel);

                float difference = Math.abs((red + blue + green) - sum);

                if (difference > treshold) {
                    rowEnd = Math.min(row, input.getHeight());
                }
            }
        }

        for (int col = input.getWidth() - 1; col >= 0; col--) {
            for (int row = 0; row < input.getHeight(); row++) {
                int pixel = pixels[row * width + col];
                int red = Color.red(pixel);
                int blue = Color.blue(pixel);
                int green = Color.green(pixel);

                float difference = Math.abs((red + blue + green) - sum);

                if (difference > treshold) {
                    colEnd = Math.min(col, input.getWidth());
                }
            }
        }



        // Now we copy the resulting part from the first Bitmap to the second Bitmap.
        return Bitmap.createBitmap(input, colEnd, rowEnd, colStart - colEnd, rowStart - rowEnd);
    }
}
