package be.abyx.aurora.shapes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.Type;

import be.abyx.aurora.ScriptC_circle_render;
import be.abyx.aurora.ScriptC_circle_render_bitmap;
import be.abyx.aurora.shapes.ShapeType;
import be.abyx.aurora.utilities.ResizeUtility;

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
        throw new UnsupportedOperationException("Not yet implemented... Use multithreaded version.");
    }

    @Override
    public Bitmap renderParallel(Bitmap input, int backgroundColour, int padding) {
        ResizeUtility resizeUtility = new ResizeUtility();

        // We want to end up with a square Bitmap with some padding applied to it, so we use the
        // the length of the largest dimension (width or height) as the width of our square.
        int dimension = resizeUtility.getLargestDimension(input.getWidth(), input.getHeight()) + 2 * padding;

        Bitmap output = resizeUtility.createSquareBitmapWithPadding(input, padding);
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
        throw new UnsupportedOperationException("Not yet implemented... Use multithreaded version.");
    }

    @Override
    public Bitmap renderParallelCustomBackground(Bitmap input, Bitmap backgroundImage, int padding) {
        ResizeUtility resizeUtility = new ResizeUtility();

        // We want to end up with a square Bitmap with some padding applied to it, so we use the
        // the length of the largest dimension (width or height) as the width of our square.
        int dimension = resizeUtility.getLargestDimension(input.getWidth(), input.getHeight()) + 2 * padding;

        Bitmap output = resizeUtility.createSquareBitmapWithPadding(input, padding);
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
}
