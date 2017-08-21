package be.abyx.aurora.aurora;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * An AuroraFactory that prefers to execute massively parallel implementations of render-functions.
 *
 * @author Pieter Verschaffelt
 */
public class ParallelAuroraFactory extends CPUAuroraFactory {
    public ParallelAuroraFactory(Context context) {
        super(context);
    }

    @Override
    public Bitmap createAuroraBasedUponColour(int colour, AuroraType type, int width, int height) {
        return type.renderParallel(width, height, colour);
    }
}
