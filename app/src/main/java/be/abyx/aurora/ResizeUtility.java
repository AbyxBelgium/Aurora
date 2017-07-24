package be.abyx.aurora;

import android.graphics.Bitmap;

/**
 * ResizeUtility provides methods for resizing a certain
 *
 * @author Pieter Verschaffelt
 */
public class ResizeUtility {
    public Bitmap resizeAndSquare(Bitmap input, int dimension, int padding) {
        Bitmap squared = createSquareBitmapWithPadding(input, padding);
        return Bitmap.createScaledBitmap(squared, dimension, dimension, false);
    }

    /**
     * Create a Bitmap with both the width and height equal to the largest dimension (either
     * original width or original height) and center the original content inside of the new Bitmap.
     *
     * @param input The Bitmap that should be made square and centered.
     * @param padding An extra border that should be added around the original image (width in
     *                pixels).
     * @return A new Bitmap that's square and contains the original image in the center.
     */
    public Bitmap createSquareBitmapWithPadding(Bitmap input, int padding) {
        // We want to end up with a square Bitmap with some padding applied to it, so we use the
        // the length of the largest dimension (width or height) as the width of our square.
        int dimension = getLargestDimension(input.getWidth(), input.getHeight()) + 2 * padding;

        int[] outputPixels = new int[dimension * dimension];

        // No need to initialize the array because the transparent color is by default 0

        int[] inputPixels = new int[input.getWidth() * input.getHeight()];
        input.getPixels(inputPixels, 0, input.getWidth(), 0, 0, input.getWidth(), input.getHeight());

        int differenceWidth = (dimension - input.getWidth()) / 2;
        int differenceHeight = (dimension - input.getHeight()) / 2;

        // Copy the original image to the new image and center it.
        for (int x = 0; x < input.getWidth(); x++) {
            for (int y = 0; y < input.getHeight(); y++) {
                outputPixels[(y + differenceHeight) * dimension + x + differenceWidth] = inputPixels[y * input.getWidth() + x];
            }
        }

        return Bitmap.createBitmap(outputPixels, dimension, dimension, Bitmap.Config.ARGB_8888);
    }

    public int getLargestDimension(int width, int height) {
        // Dimension should always be odd! (This is necessary for easily finding the center of the
        // circle)
        if (width % 2 == 0) {
            width++;
        }

        if (height % 2 == 0) {
            height++;
        }

        if (width > height) {
            return width;
        } else {
            return height;
        }
    }
}
