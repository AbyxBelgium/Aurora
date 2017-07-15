package be.abyx.aurora;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import java.util.Random;
import java.util.Stack;


/**
 * This class provides several methods for advanced image manipulation.
 *
 * @author Pieter Verschaffelt
 */
public class ImageUtils {
    private Context context;

    public ImageUtils(Context context) {
        this.context = context;
    }

    /**
     * Add random white noise to a Bitmap.
     *
     * @param input The Bitmap to which white noise should be added.
     * @param amount The amount of white noise that should be added to the image. This must be a
     *               value between 0 and 1 where 0 represents no noise and 1 the maximum amount
     *               of noise.
     * @param accuracy The amount of pixels that can be skipped between noise spots.
     * @return A new Bitmap that represents the input but with extra white noise added.
     */
    public Bitmap addNoise(Bitmap input, float amount, int accuracy) {
        Bitmap output = input.copy(input.getConfig(), true);
        int[] pixels = new int[output.getHeight() * output.getWidth()];
        output.getPixels(pixels, 0, output.getWidth(), 0, 0, output.getWidth(), output.getHeight());

        Random random = new Random();

        for (int i = 0; i < output.getWidth() * output.getHeight(); i += (random.nextInt(accuracy) + 1)) {
            int noise = (int) (random.nextInt(255) * amount);

            int pixel = pixels[i];

            int red = Color.red(pixel) + noise;
            int green = Color.green(pixel) + noise;
            int blue = Color.blue(pixel) + noise;

            pixels[i] = Color.rgb(red, green, blue);
        }

        output.setPixels(pixels, 0, output.getWidth(), 0, 0, output.getWidth(), output.getHeight());
        return output;
    }
}
