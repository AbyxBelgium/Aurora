package be.abyx.aurora;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.Type;

/**
 * A type of Shape that can be used for rendering circles with a specific background colour.
 *
 * @author Pieter Verschaffelt
 */
public class CircleShape implements ShapeType {
    private Context context;

    public CircleShape(Context context) {
        this.context = context;
    }

    @Override
    public Bitmap render(Bitmap input, int backgroundColour, int padding) {
        return null;
    }

    @Override
    public Bitmap renderParallel(Bitmap input, int backgroundColour, int padding) {
        // We want to end up with a square Bitmap with some padding applied to it, so we use the
        // the length of the largest dimension (width or height) as the width of our square.
        int dimension = getLargestDimension(input.getWidth(), input.getHeight()) + 2 * padding;

        Bitmap output = createSquareBitmapWithPadding(input, padding);
        output.setHasAlpha(true);

        RenderScript rs = RenderScript.create(this.context);

        Allocation inputAlloc = Allocation.createFromBitmap(rs, output);
        Type t = inputAlloc.getType();

        Allocation outputAlloc = Allocation.createTyped(rs, t);

        ScriptC_circle_render circleRenderer = new ScriptC_circle_render(rs);

        circleRenderer.set_centerX(dimension / 2);
        circleRenderer.set_centerY(dimension / 2);
        circleRenderer.set_radius(dimension / 2);
        circleRenderer.set_destinationA(((float) Color.alpha(backgroundColour)) / 255.0f);
        circleRenderer.set_destinationR(((float) Color.red(backgroundColour)) / 255.0f);
        circleRenderer.set_destinationG(((float) Color.green(backgroundColour)) / 255.0f);
        circleRenderer.set_destinationB(((float) Color.blue(backgroundColour)) / 255.0f);

        circleRenderer.forEach_circleRender(inputAlloc, outputAlloc);
        outputAlloc.copyTo(output);

        inputAlloc.destroy();
        outputAlloc.destroy();
        circleRenderer.destroy();
        rs.destroy();

        return output;
    }

    @Override
    public Bitmap renderCustomBackground(Bitmap input, Bitmap backgroundImage, int padding) {
        return null;
    }

    @Override
    public Bitmap renderParallelCustomBackground(Bitmap input, Bitmap backgroundImage, int padding) {
        // We want to end up with a square Bitmap with some padding applied to it, so we use the
        // the length of the largest dimension (width or height) as the width of our square.
        int dimension = getLargestDimension(input.getWidth(), input.getHeight()) + 2 * padding;

        Bitmap output = createSquareBitmapWithPadding(input, padding);
        output.setHasAlpha(true);

        RenderScript rs = RenderScript.create(this.context);

        Allocation inputAlloc = Allocation.createFromBitmap(rs, output);
        Type t = inputAlloc.getType();

        Bitmap resizedBackground = Bitmap.createBitmap(backgroundImage, 0, 0, dimension, dimension);

        Allocation backgroundAlloc = Allocation.createFromBitmap(rs, resizedBackground);

        Allocation outputAlloc = Allocation.createTyped(rs, t);

        ScriptC_circle_render_bitmap circleRenderer = new ScriptC_circle_render_bitmap(rs);
        circleRenderer.set_centerX(dimension / 2);
        circleRenderer.set_centerY(dimension / 2);
        circleRenderer.set_radius(dimension / 2);

        circleRenderer.forEach_circleRender(inputAlloc, backgroundAlloc, outputAlloc);

        outputAlloc.copyTo(output);

        inputAlloc.destroy();
        outputAlloc.destroy();
        backgroundAlloc.destroy();
        circleRenderer.destroy();
        rs.destroy();

        return output;
    }

    /**
     * Create a Bitmap with both the width and height equal to the largest dimension (either
     * original width or original height) and center the original content inside of the new Bitmap.
     *
     * @param input The Bitmap that should be made square and centered.
     * @param padding An extra border that should be added around the original image (width in
     *                pixels).
     * @return A new Bitmap that's square and contains the original image in the center.
     */
    // TODO: optimization: This function could also directly take place inside the RenderScript
    // TODO: kernel and thus speed up the execution...
    private Bitmap createSquareBitmapWithPadding(Bitmap input, int padding) {
        // We want to end up with a square Bitmap with some padding applied to it, so we use the
        // the length of the largest dimension (width or height) as the width of our square.
        int dimension = getLargestDimension(input.getWidth(), input.getHeight()) + 2 * padding;

        int[] outputPixels = new int[dimension * dimension];

        // No need to initialize the array because the transparent color is by default 0

        int[] inputPixels = new int[input.getWidth() * input.getHeight()];
        input.getPixels(inputPixels, 0, input.getWidth(), 0, 0, input.getWidth(), input.getHeight());

        int differenceWidth = (dimension - input.getWidth()) / 2;
        int differenceHeight = (dimension - input.getHeight()) / 2;

        // Copy the original image to the new image and center it.
        for (int x = 0; x < input.getWidth(); x++) {
            for (int y = 0; y < input.getHeight(); y++) {
                outputPixels[(y + differenceHeight) * dimension + x + differenceWidth] = inputPixels[y * input.getWidth() + x];
            }
        }

        return Bitmap.createBitmap(outputPixels, dimension, dimension, Bitmap.Config.ARGB_8888);
    }

    private int getLargestDimension(int width, int height) {
        // Dimension should always be odd! (This is necessary for easily finding the center of the
        // circle)
        if (width % 2 == 0) {
            width++;
        }

        if (height % 2 == 0) {
            height++;
        }

        if (width > height) {
            return width;
        } else {
            return height;
        }
    }
}
