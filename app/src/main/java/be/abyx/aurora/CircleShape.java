package be.abyx.aurora;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

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
        int dimension = getLargestDimension(input.getWidth(), input.getHeight());

        Bitmap output = createSquareBitmapWithPadding(input, padding);





        return null;
    }

    // TODO: optimization: This function could also directly take place inside the RenderScript
    // TODO: kernel and thus speed up the execution...
    private Bitmap createSquareBitmapWithPadding(Bitmap input, int padding) {
        // We want to end up with a square Bitmap with some padding applied to it, so we use the
        // the length of the largest dimension (width or height) as the width of our square.
        int dimension = getLargestDimension(input.getWidth(), input.getHeight());

        int[] outputPixels= new int[(dimension + 2 * padding) * (dimension + 2 * padding)];

        // Initialize the array to all transparent.
        for (int i = 0; i < outputPixels.length; i++) {
            outputPixels[i] = Color.TRANSPARENT;
        }

        int[] inputPixels = new int[input.getWidth() * input.getHeight()];
        input.getPixels(inputPixels, 0, input.getWidth(), 0, 0, input.getWidth(), input.getHeight());

        int differenceWidth = dimension - input.getWidth();
        int differenceHeight = dimension - input.getHeight();

        // Copy the original image to the new image and center it.
        for (int x = 0; x < input.getWidth(); x++) {
            for (int y = 0; y < input.getHeight(); y++) {
                outputPixels[(y + differenceHeight) * dimension + x + differenceWidth] = inputPixels[y * input.getWidth() + x];
            }
        }

        return Bitmap.createBitmap(outputPixels, dimension, dimension, Bitmap.Config.ARGB_8888);
    }

    private int getLargestDimension(int width, int height) {
        if (width > height) {
            return width;
        } else {
            return height;
        }
    }
}
