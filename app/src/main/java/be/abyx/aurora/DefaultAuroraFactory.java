package be.abyx.aurora;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Debug;
import android.support.v7.graphics.Palette;
import android.util.SparseIntArray;

/**
 * This factory is a concrete AuroraFactory implementation that uses a simple CPU-based algorithm
 * to determine the most occurring colour.
 *
 * @author Pieter Verschaffelt
 */
public class DefaultAuroraFactory implements AuroraFactory {
    private Context context;

    public DefaultAuroraFactory(Context context) {
        this.context = context;
    }

    @Override
    public Bitmap createAuroraBasedUponDrawable(Bitmap input, int width, int height) {
        AuroraType type = new SimpleGradientAurora();
        return createAuroraBasedUponDrawable(input, type, width, height);
    }

    @Override
    public Bitmap createAuroraBasedUponDrawable(Bitmap input, AuroraType type, int width, int height) {
        int colour = determineDominantColour(input);
        return createAuroraBasedUponColour(colour, type, width, height);
    }

    @Override
    public Bitmap createAuroraBasedUponColour(int colour, int width, int height) {
        AuroraType type = new SimpleGradientAurora();
        return createAuroraBasedUponColour(colour, type, width, height);
    }

    @Override
    public Bitmap createAuroraBasedUponColour(int colour, AuroraType type, int width, int height) {
        return type.render(width, height, colour);
    }

    private int determineDominantColour(Bitmap input) {
        Palette p = Palette.from(input).addFilter(new Palette.Filter() {
            @Override
            public boolean isAllowed(int rgb, float[] hsl) {
                // We don't want white as the dominant colour.
                return rgb != Color.WHITE;
            }
        }).generate();

        return p.getDominantColor(Color.GRAY);
    }
}
