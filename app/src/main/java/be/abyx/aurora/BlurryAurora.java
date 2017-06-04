package be.abyx.aurora;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

/**
 * An Aurora based upon a blurry image whose hue is shifted to match the hue of a given color.
 *
 * @author Pieter Verschaffelt
 */
public class BlurryAurora implements AuroraType {
    private Context context;

    public BlurryAurora(Context context) {
        this.context = context;
    }

    @Override
    public Bitmap render(int width, int height, int colour) {
        if (width > 1200) {
            throw new RuntimeException("Only widths up to 1200 pixels are supported at the moment!");
        }

        if (height > 1920) {
            throw new RuntimeException("Only heights up to 1920 pixels are supported at the moment!");
        }

        // Force disable the automatic density scaling
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = false;

        Bitmap sourceBitmap = BitmapFactory.decodeResource(context.getResources(), R.raw.aurora_fancy_template, opts);

        int originalWidth = sourceBitmap.getWidth();
        int originalHeight = sourceBitmap.getHeight();

        // Get raw pixel values from the Bitmap
        int[] pixels = new int[originalWidth * originalHeight];
        sourceBitmap.getPixels(pixels, 0, originalWidth, 0, 0, originalWidth, originalHeight);

        // We're going to use this pixel as a reference for the hue and to determine the amount of shift that should be applied to the hue.
        int referencePixel = pixels[0];

        float hueShift = getHueShift(pixels[0], colour);

        int[] outputPixels = new int[height * width];

        DebugSystem.print("Progress 0.00%");

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = pixels[i * originalWidth + j];
                float[] hsv = convertColorToHSV(pixel);
                hsv[0] = (hsv[0] + hueShift) % 360;
                outputPixels[i * width + j] = Color.HSVToColor(hsv);
            }

            double progress = ((double) i) / ((double) height);
            DebugSystem.print("\rProgress " + String.format("%.2f", progress));
        }

        DebugSystem.println("");

        return Bitmap.createBitmap(outputPixels, width, height, Bitmap.Config.RGB_565);
    }

    /**
     * Calculate the amount of shift that should be applied to the referencePixel to match the
     * destination colour's hue.
     *
     * @param referencePixel Pixel whose hue should be matched to the destinationColour.
     * @param destinationColour The destinationColour that's used for determining the hue shift.
     * @return The difference in hue value between the referencePixel and the destinationColour.
     */
    private float getHueShift(int referencePixel, int destinationColour) {
        float[] referenceHSV = convertColorToHSV(referencePixel);
        float[] destinationHSV = convertColorToHSV(destinationColour);

        return (destinationHSV[0] - referenceHSV[0] + 360) % 360;
    }

    private float[] convertColorToHSV(int colour) {
        float[] referenceHSV = new float[3];

        int referenceRed = Color.red(colour);
        int referenceGreen = Color.green(colour);
        int referenceBlue = Color.blue(colour);

        Color.RGBToHSV(referenceRed, referenceGreen, referenceBlue, referenceHSV);
        return referenceHSV;
    }
}
