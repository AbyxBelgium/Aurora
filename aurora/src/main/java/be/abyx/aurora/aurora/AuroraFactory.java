package be.abyx.aurora.aurora;

import android.graphics.Bitmap;

/**
 * This interface defines methods that should be provided by all AuroraFactories. These methods can
 * be used by external callers for creating new Aurora Drawables, based upon a specific color,
 * another drawable or more.
 *
 * @author Pieter Verschaffelt
 */
public interface AuroraFactory {
    /**
     * Create a new Aurora whose color is based upon the colour that occurs the most in the given
     * Bitmap. This results in a Drawable that perfectly matches the input to this function.
     * The default Aurora type will be used (SimpleGradientAurora).
     *
     * @param input A Bitmap whose most occurring colour should be used for building an Aurora.
     * @param width The width in pixels of the Bitmap that should be generated.
     * @param height The height in pixels of the Bitmap that should be generated.
     * @return An Aurora that's based upon the input's most occurring colour.
     */
    public Bitmap createAuroraBasedUponDrawable(Bitmap input, int width, int height);

    /**
     * Create a new Aurora whose color is based upon the colour that occurs the most in the given
     * Bitmap. This results in a Drawable that perfectly matches the input to this function.
     *
     * @param input A Bitmap whose most occurring colour should be used for building an Aurora.
     * @param type The type of Aurora that should be created.
     * @param width The width in pixels of the Bitmap that should be generated.
     * @param height The height in pixels of the Bitmap that should be generated.
     * @return An Aurora that's based upon the input's most occurring colour.
     */
    public Bitmap createAuroraBasedUponDrawable(Bitmap input, AuroraType type, int width, int height);

    /**
     * Create a new Aurora whose color is equal to the given input.
     *
     * @param colour Colour that should be used for rendering the Aurora.
     * @param width The width in pixels of the Bitmap that should be generated.
     * @param height The height in pixels of the Bitmap that should be generated.
     * @return An Aurora based upon the given input colour.
     */
    public Bitmap createAuroraBasedUponColour(int colour, int width, int height);

    /**
     * Create a new Aurora whose color is equal to the given input.
     *
     * @param colour Colour that should be used for rendering the Aurora.
     * @param type The type of Aurora that should be created.
     * @param width The width in pixels of the Bitmap that should be generated.
     * @param height The height in pixels of the Bitmap that should be generated.
     * @return An Aurora based upon the given input colour.
     */
    public Bitmap createAuroraBasedUponColour(int colour, AuroraType type, int width, int height);
}
