package be.abyx.aurora.shapes;

import android.graphics.Bitmap;

/**
 * CPU-based implementation of the ShapeFactory interface that can be used on devices that do not
 * support RenderScript.
 *
 * @author Pieter Verschaffelt
 */
public class CPUShapeFactory implements ShapeFactory {
    @Override
    public Bitmap createShape(ShapeType type, Bitmap input, int backgroundColour, int shapePadding) {
        return type.render(input, backgroundColour, shapePadding);
    }

    @Override
    public Bitmap createShape(ShapeType type, Bitmap input, Bitmap background, int shapePadding) {
        return type.renderCustomBackground(input, background, shapePadding);
    }
}
