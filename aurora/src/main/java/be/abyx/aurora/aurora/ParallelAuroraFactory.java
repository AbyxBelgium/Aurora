package be.abyx.aurora.aurora;

import android.content.Context;
import android.graphics.Bitmap;

import be.abyx.aurora.aurora.AuroraType;
import be.abyx.aurora.aurora.DefaultAuroraFactory;

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
