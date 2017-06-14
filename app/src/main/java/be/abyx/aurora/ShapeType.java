package be.abyx.aurora;

import android.graphics.Bitmap;

/**
 * This interface defines common methods that are needed for rendering a Shape of a specific type.
 *
 * @author Pieter Verschaffelt
 */
public interface ShapeType {
    public Bitmap render(Bitmap input, int backgroundColour, int padding);
    public Bitmap renderParallel(Bitmap input, int backgroundColour, int padding);
    public Bitmap renderCustomBackground(Bitmap input, Bitmap backgroundImage, int padding);
    public Bitmap renderParallelCustomBackground(Bitmap input, Bitmap backgroundImage, int padding);
}
