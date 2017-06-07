package be.abyx.aurora;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseIntArray;

/**
 * An AuroraFactory that prefers to execute massively parallel implementations of render-functions.
 *
 * @author Pieter Verschaffelt
 */
public class ParallelAuroraFactory extends DefaultAuroraFactory {
    public ParallelAuroraFactory(Context context) {
        super(context);
    }

    @Override
    public Bitmap createAuroraBasedUponColour(int colour, AuroraType type, int width, int height) {
        return type.renderParallel(width, height, colour);
    }
}
