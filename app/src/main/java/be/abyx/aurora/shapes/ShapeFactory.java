package be.abyx.aurora.shapes;

import android.graphics.Bitmap;

/**
 * A factory for building new Shape-objects.
 *
 * @author Pieter Verschaffelt
 */
public interface ShapeFactory {
    /**
     * Generates a Bitmap where the given input Bitmap is used as the foreground layer and the
     * given background colour will be used as the background layer.
     *
     * @param type The type of shape that should be used for rendering the resulting Bitmap.
     * @param input Bitmap that will be used as foreground layer for the resulting Bitmap.
     * @param backgroundColour Colour that will be used as a background layer. Only the pixels from
     *                         the input Bitmap that are set to be transparent will be replaced.
     * @param shapePadding Padding or width of the resulting shape.
     * @return A new square Bitmap whose size is the longest dimension + the desired padding value
     * in each direction.
     */
    public Bitmap createShape(ShapeType type, Bitmap input, int backgroundColour, int shapePadding);

    /**
     * Generates a Bitmap where the given input Bitmap is used as the foreground layer and the
     * given background Bitmap will be used as the background for this Bitmap.
     *
     * @param type The type of shape that should be used for rendering the resulting Bitmap.
     * @param input Bitmap that will be used as foreground layer for the resulting Bitmap.
     * @param background Bitmap that will be used as background for the resulting Bitmap. Only the
     *                   pixels of the input Bitmap that are set to be transparent will be replaced.
     * @param shapePadding Padding or width of the resulting shape.
     * @return A new square Bitmap whose size is the longest dimension + the desired padding value
     * in each direction.
     */
    public Bitmap createShape(ShapeType type, Bitmap input, Bitmap background, int shapePadding);
}
