package be.abyx.aurora.aurora;

import android.graphics.Bitmap;
import android.graphics.Color;

import be.abyx.aurora.aurora.AuroraType;

/**
 * A default AuroraRenderer that runs completely off the CPU.
 *
 * @author Pieter Verschaffelt
 */
public class SimpleGradientAurora implements AuroraType {
    @Override
    public Bitmap render(int width, int height, int colour) {
        int[] pixels = new int[width * height];

        int redInitial = Color.red(colour);
        int greenInitial = Color.green(colour);
        int blueInitial = Color.blue(colour);

        for (int i = 0; i < height; i++) {
            int newRed = getInterpolatedColour(redInitial, i, height);
            int newGreen = getInterpolatedColour(greenInitial, i, height);
            int newBlue = getInterpolatedColour(blueInitial, i, height);

            int newColour = Color.argb(255, newRed, newGreen, newBlue);
            for (int j = 0; j < width; j++) {
                pixels[i * width + j] = newColour;
            }
        }

        // We use RGB_565 because alpha-values should be ignored.
        return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888);
    }

    @Override
    public Bitmap renderParallel(int width, int height, int colour) {
        throw new UnsupportedOperationException("Not yet implemented... Please use single threaded version.");
    }

    private int getInterpolatedColour(int value, int row, int totalRows) {
        return (int) (value * (1.0 - ((double) row) / ((double) totalRows)));
    }
}
