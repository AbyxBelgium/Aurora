package be.abyx.aurora;

import android.graphics.Bitmap;

/**
 * Fast implementation of the ShapeFactory that prefers the execution of massively parallel
 * implementations of renderers.
 *
 * @author Pieter Verschaffelt
 */
public class ParallelShapeFactory implements ShapeFactory {
    @Override
    public Bitmap createShape(ShapeType type, Bitmap input, int backgroundColour, int shapePadding) {
        return type.renderParallel(input, backgroundColour, shapePadding);
    }
}
