package be.abyx.aurora.engine.compare;

import android.graphics.Bitmap;

import junit.framework.Assert;

/**
 * @author Pieter Verschaffelt
 */
public class BitmapComparer {
    /**
     *
     * @param original A reference Bitmap.
     * @param toCompare A Bitmap that should be compared to the original one.
     * @param accuracy Indicates just how similar pixel values should be. The higher this value, the
     *                 more variation there might be between the 2 Bitmap's pixel values.
     */
    public void compareBitmaps(Bitmap original, Bitmap toCompare, double accuracy) {
        Assert.assertEquals("Width of both Bitmap's should be equal", original.getWidth(), toCompare.getWidth());
        Assert.assertEquals("Height of both Bitmap's should be equal", original.getHeight(), toCompare.getHeight());

        int[] originalPixels = new int[original.getWidth() * original.getHeight()];
        int[] toComparePixels = new int[toCompare.getHeight() * toCompare.getWidth()];

        original.getPixels(originalPixels, 0, original.getWidth(), 0, 0, original.getWidth(), original.getHeight());
        toCompare.getPixels(toComparePixels, 0, toCompare.getWidth(), 0, 0, toCompare.getWidth(), toCompare.getHeight());

        ColourComparer comparer = new ColourComparer();

        // Simply compare all pixel values...
        for (int i = 0; i < original.getHeight() * original.getWidth(); i++) {
            comparer.compareColors(originalPixels[i], toComparePixels[i], accuracy);
        }
    }
}
