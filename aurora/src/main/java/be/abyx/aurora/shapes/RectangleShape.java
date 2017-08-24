package be.abyx.aurora.shapes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.Type;

import be.abyx.aurora.ScriptC_rectangle_render;
import be.abyx.aurora.shapes.ShapeType;

/**
 * This class represents a rectangular that can be used for drawing a rectangle around a Bitmap with
 * a certain padding or other parametric options.
 *
 * @author Pieter Verschaffelt
 */
public class RectangleShape implements ShapeType {
    private Context context;

    public RectangleShape(Context context) {
        this.context = context;
    }

    @Override
    public Bitmap render(Bitmap input, int backgroundColour, int padding) {
        int[] outputPixels = createCenteredBitmapPixels(input, padding);

        int width = getPaddedDimension(input.getWidth(), padding);
        int height = getPaddedDimension(input.getHeight(), padding);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (outputPixels[y * width + x] == Color.TRANSPARENT) {
                    outputPixels[y * width + x] = backgroundColour;
                }
            }
        }

        return Bitmap.createBitmap(outputPixels, width, height, Bitmap.Config.ARGB_8888);
    }

    @Override
    public Bitmap renderParallel(Bitmap input, int backgroundColour, int padding) {
        Bitmap output = createCenteredBitmapWithPadding(input, padding);
        output.setHasAlpha(true);

        RenderScript rs = RenderScript.create(this.context);

        Allocation inputAlloc = Allocation.createFromBitmap(rs, output);
        Type t = inputAlloc.getType();

        Allocation outputAlloc = Allocation.createTyped(rs, t);

        ScriptC_rectangle_render rectangleRenderer = new ScriptC_rectangle_render(rs);
        
        rectangleRenderer.set_destinationA(((float) Color.alpha(backgroundColour)) / 255.0f);
        rectangleRenderer.set_destinationR(((float) Color.red(backgroundColour)) / 255.0f);
        rectangleRenderer.set_destinationG(((float) Color.green(backgroundColour)) / 255.0f);
        rectangleRenderer.set_destinationB(((float) Color.blue(backgroundColour)) / 255.0f);

        rectangleRenderer.forEach_rectangleRender(inputAlloc, outputAlloc);

        outputAlloc.copyTo(output);

        inputAlloc.destroy();
        outputAlloc.destroy();
        rectangleRenderer.destroy();
        rs.destroy();

        return output;
    }

    @Override
    public Bitmap renderCustomBackground(Bitmap input, Bitmap backgroundImage, int padding) {
        throw new UnsupportedOperationException("Not yet implemented... No alternative version available.");
    }

    @Override
    public Bitmap renderParallelCustomBackground(Bitmap input, Bitmap backgroundImage, int padding) {
        throw new UnsupportedOperationException("Not yet implemented... No alternative version available.");
    }

    private int getPaddedDimension(int dimension, int padding) {
        return dimension + 2 * padding;
    }

    private int[] createCenteredBitmapPixels(Bitmap input, int padding) {
        int width = input.getWidth();
        int height = input.getHeight();

        int[] outputPixels = new int[getPaddedDimension(width, padding) * getPaddedDimension(height, padding)];

        int[] inputPixels = new int[input.getWidth() * input.getHeight()];
        input.getPixels(inputPixels, 0, input.getWidth(), 0, 0, input.getWidth(), input.getHeight());

        // Copy the original image to the new image and center it.
        for (int x = 0; x < input.getWidth(); x++) {
            for (int y = 0; y < input.getHeight(); y++) {
                outputPixels[(y + padding) * (width + 2 * padding) + x + padding] = inputPixels[y * input.getWidth() + x];
            }
        }
        return outputPixels;
    }


    private Bitmap createCenteredBitmapWithPadding(Bitmap input, int padding) {
        return Bitmap.createBitmap(createCenteredBitmapPixels(input, padding), getPaddedDimension(input.getWidth(), padding), getPaddedDimension(input.getHeight(), padding), Bitmap.Config.ARGB_8888);
    }
}
