package be.abyx.aurora;

import android.graphics.Bitmap;

/**
 * The AuroraRenderer is able to render all sort of different Aurora's based upon some colour.
 *
 * @author Pieter Verschaffelt
 */
public interface AuroraRenderer {
    public Bitmap renderGradient(int width, int height, int colour);
}
