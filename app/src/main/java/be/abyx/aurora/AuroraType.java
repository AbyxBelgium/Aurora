package be.abyx.aurora;

import android.graphics.Bitmap;

/**
 * An interface representing the type of an Aurora.
 *
 * @author Pieter Verschaffelt
 */
public interface AuroraType {
    public Bitmap render(int width, int height, int colour);

    /**
     * Massively parallel implementation of the render method for increased performance.
     *
     * @param width Desired width of the output image.
     * @param height Desired height of the output image.
     * @param colour Desired colour of the output image.
     * @return A new Aurora that's been rendered using a massively parallel implementation.
     */
    public Bitmap renderParallel(int width, int height, int colour);
}
