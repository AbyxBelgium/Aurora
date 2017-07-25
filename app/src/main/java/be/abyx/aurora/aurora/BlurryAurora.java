package be.abyx.aurora.aurora;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.Type;

import be.abyx.aurora.R;
import be.abyx.aurora.ScriptC_hue_shift;
import be.abyx.aurora.system.DebugSystem;

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
        // Force disable the automatic density scaling
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = false;

        Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.raw.aurora_fancy_template, opts);
        Bitmap sourceBitmap = Bitmap.createScaledBitmap(originalBitmap, width, height, true);

        int originalWidth = sourceBitmap.getWidth();
        int originalHeight = sourceBitmap.getHeight();

        // Get raw pixel values from the Bitmap
        int[] pixels = new int[originalWidth * originalHeight];
        sourceBitmap.getPixels(pixels, 0, originalWidth, 0, 0, originalWidth, originalHeight);

        // We're going to use this pixel as a reference for the hue and to determine the amount of shift that should be applied to the hue.
        int referencePixel = pixels[0];

        float hueShift = getHueShift(pixels[0], colour);
        float satShift = getSaturationShift(pixels[0], colour);

        int[] outputPixels = new int[height * width];

        DebugSystem.print("Progress 0.00%");

        float[] hsv = new float[3];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = pixels[i * originalWidth + j];
                convertColorToHSV(pixel, hsv);
                hsv[0] = (hsv[0] + hueShift) % 360;

                if (satShift >= 180) {
                    hsv[1] = 0;
                }

                outputPixels[i * width + j] = Color.HSVToColor(hsv);
            }

            double progress = ((double) i) / ((double) height);
            DebugSystem.print("\rProgress " + String.format("%.2f", progress));
        }

        DebugSystem.println("");

        return Bitmap.createBitmap(outputPixels, width, height, Bitmap.Config.RGB_565);
    }

    @Override
    public Bitmap renderParallel(int width, int height, int colour) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = false;

        DebugSystem.startTimer();
        Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.raw.aurora_fancy_template, opts);
        Bitmap sourceBitmap = Bitmap.createScaledBitmap(originalBitmap, width, height, true);
        DebugSystem.endTimer("Bitmap Factory");

        DebugSystem.startTimer();
        // We only need access to the first pixel of our sourceBitmap
        int[] sourcePixel = new int[1];
        sourceBitmap.getPixels(sourcePixel, 0, sourceBitmap.getWidth(), 0, 0, 1, 1);

        RenderScript rs = RenderScript.create(this.context);

        Allocation input = Allocation.createFromBitmap(rs, sourceBitmap);
        Type t = input.getType();

        Allocation output = Allocation.createTyped(rs, t);

        ScriptC_hue_shift hueShift = new ScriptC_hue_shift(rs);

        float shift = getHueShift(sourcePixel[0], colour);
        float satShift = getSaturationShift(sourcePixel[0], colour);

        hueShift.set_shift(shift);
        if (satShift >= 180) {
            hueShift.set_saturation(0);
        } else {
            hueShift.set_saturation(1);
        }

        hueShift.forEach_hueShift(input, output);
        // Reuse the sourceBitmap to save resources (we no longer need it)
        output.copyTo(sourceBitmap);

        // Destroy all variables because we're done computing
        output.destroy();
        input.destroy();
        hueShift.destroy();
        rs.destroy();
        DebugSystem.endTimer("Renderscript");

        return sourceBitmap;
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
        float[] referenceHSV = new float[3];
        convertColorToHSV(referencePixel, referenceHSV);
        float[] destinationHSV = new float[3];
        convertColorToHSV(destinationColour, destinationHSV);

        return (destinationHSV[0] - referenceHSV[0] + 360) % 360;
    }

    private float getSaturationShift(int referencePixel, int destinationColour) {
        float[] referenceHSV = new float[3];
        convertColorToHSV(referencePixel, referenceHSV);
        float[] destinationHSV = new float[3];
        convertColorToHSV(destinationColour, destinationHSV);

        return (destinationHSV[1] - referenceHSV[1] + 360) % 360;
    }

    private void convertColorToHSV(int colour, float[] result) {
        int referenceRed = Color.red(colour);
        int referenceGreen = Color.green(colour);
        int referenceBlue = Color.blue(colour);

        Color.RGBToHSV(referenceRed, referenceGreen, referenceBlue, result);
    }
}
