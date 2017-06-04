package be.abyx.aurora;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.res.ResourcesCompat;

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
        BitmapDrawable drawable = (BitmapDrawable) ResourcesCompat.getDrawable(context.getResources(), R.drawable.fancy, null);
        Bitmap sourceBitmap = drawable.getBitmap();
        int[] pixels = new int[sourceBitmap.getHeight() * sourceBitmap.getWidth()];
        sourceBitmap.getPixels(pixels, 0, sourceBitmap.getWidth(), 0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight());

        // We're going to use this pixel as a reference for the hue and to determine the amount of shift that should be applied to the hue.
        int referencePixel = pixels[0];

        float hueShift = getHueShift(pixels[0], colour);

        int[] outputPixels = new int[height * width];

        DebugSystem.print("Progress 0.00%");

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = pixels[i * sourceBitmap.getWidth() + j];
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

        return destinationHSV[0] - referenceHSV[0];
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
