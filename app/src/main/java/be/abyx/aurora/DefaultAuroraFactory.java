package be.abyx.aurora;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;

import java.util.HashMap;
import java.util.Map;

/**
 * This factory is a concrete AuroraFactory implementation that uses a simple CPU-based algorithm
 * to determine the most occurring colour.
 *
 * @author Pieter Verschaffelt
 */
public class DefaultAuroraFactory implements AuroraFactory {
    private Context context;
    private ColourPalette palette;
    private int accuracy = 50;

    public DefaultAuroraFactory(Context context) {
        this.context = context;
        ColourPaletteFactory paletteFactory = new ColourPaletteFactory(context);
        palette = paletteFactory.getDefaultColourPalette();
    }

    @Override
    public Bitmap createAuroraBasedUponDrawable(Bitmap input) {
        return null;
    }

    @Override
    public Bitmap createAuroraBasedUponColour(int colour) {
        AuroraRenderer renderer = new DefaultAuroraRenderer();
        return renderer.renderGradient(400, 800, colour);
    }

    private int determineDominantColour(Bitmap input) {
        int totalPixels = input.getWidth() * input.getHeight();

        int[] pixels = new int[input.getWidth() * input.getHeight()];
        input.getPixels(pixels, 0, input.getWidth(), 0, 0, input.getWidth(), input.getHeight());

        SparseIntArray colourMap = new SparseIntArray();

        for (int i = 0; i < totalPixels; i += accuracy) {
            Integer closestColour = palette.matchToClosestColour(pixels[i]);
            colourMap.put(closestColour, colourMap.get(closestColour, 0) + 1);
        }

        int largestValue = 0;
        int largestKey = 0;

        for (int i = 0; i < colourMap.size(); i++) {
            int key = colourMap.keyAt(i);
            int value = colourMap.get(key);

            if (value > largestValue) {
                largestValue = value;
                largestKey = key;
            }
        }

        return largestKey;
    }
}
