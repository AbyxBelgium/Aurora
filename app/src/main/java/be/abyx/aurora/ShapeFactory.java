package be.abyx.aurora;

import android.graphics.Bitmap;

/**
 * A factory for building new Shape-objects.
 *
 * @author Pieter Verschaffelt
 */
public interface ShapeFactory {
    public Bitmap createShape(ShapeType type, Bitmap input, int backgroundColour, int shapePadding);
}
