package be.abyx.aurora;

import android.graphics.Bitmap;

/**
 * An interface representing the type of an Aurora.
 *
 * @author Pieter Verschaffelt
 */
public interface AuroraType {
    public Bitmap render(int width, int height, Integer colour);
}
