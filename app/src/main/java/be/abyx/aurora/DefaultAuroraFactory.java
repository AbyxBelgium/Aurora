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
    public Drawable createAuroraBasedUponDrawable(Bitmap input) {
        return null;
    }

    @Override
    public Drawable createAuroraBasedUponColour(Color colour) {
        return null;
    }

    private int determineMostOccurringColour(Bitmap input) {
        int totalPixels = input.getWidth() * input.getHeight();

        int[] pixels = new int[input.getWidth() * input.getHeight()];
        input.getPixels(pixels, 0, input.getWidth(), 0, 0, input.getWidth(), input.getHeight());

        // Histogram contains r, g and b values in a row (so array will be like R G B R G B ...)
        int[] histogram = new int[256 * 3];

        for (int i = 0; i < totalPixels; i++) {
            int red = Color.red(pixels[i]);
            int green = Color.green(pixels[i]);
            int blue = Color.blue(pixels[i]);

            histogram[red * 3] += 1;
            histogram[green * 3 + 1] += 1;
            histogram[blue * 3 + 2] += 1;
        }

        int[] mostOccurring = new int[3];
        int[] mostAmount = new int[3];
        for (int i = 0; i < 256; i++) {
            for (int j = 0; j < 3; j++) {
                if (mostAmount[j] < histogram[i * 3 + j]) {
                    mostAmount[j] = histogram[i * 3 + j];
                    mostOccurring[j] = i;
                }
            }
        }

        int red = mostOccurring[0];
        int green = mostOccurring[1];
        int blue = mostOccurring[2];

        int output = Color.argb(255, red, green, blue);
        return output;
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

        String hexColor = "#" + Integer.toHexString(largestKey).substring(2);
        return largestKey;
    }
}
