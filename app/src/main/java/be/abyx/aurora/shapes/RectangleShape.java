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
 * @author Pieter Verschaffelt
 */
public class RectangleShape implements ShapeType {
    private Context context;

    public RectangleShape(Context context) {
        this.context = context;
    }

    @Override
    public Bitmap render(Bitmap input, int backgroundColour, int padding) {
        return null;
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
        return null;
    }

    @Override
    public Bitmap renderParallelCustomBackground(Bitmap input, Bitmap backgroundImage, int padding) {
        return null;
    }

    private Bitmap createCenteredBitmapWithPadding(Bitmap input, int padding) {
        int width = input.getWidth();
        int height = input.getHeight();

        int[] outputPixels = new int[(width + 2 * padding) * (height + 2 * padding)];

        int[] inputPixels = new int[input.getWidth() * input.getHeight()];
        input.getPixels(inputPixels, 0, input.getWidth(), 0, 0, input.getWidth(), input.getHeight());

        // Copy the original image to the new image and center it.
        for (int x = 0; x < input.getWidth(); x++) {
            for (int y = 0; y < input.getHeight(); y++) {
                outputPixels[(y + padding) * (width + 2 * padding) + x + padding] = inputPixels[y * input.getWidth() + x];
            }
        }

        return Bitmap.createBitmap(outputPixels, width + 2 * padding, height + 2 * padding, Bitmap.Config.ARGB_8888);
    }
}
