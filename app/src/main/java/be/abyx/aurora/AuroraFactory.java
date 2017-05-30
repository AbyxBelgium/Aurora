package be.abyx.aurora;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

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
     * drawable. This results in a Drawable that perfectly matches the input to this function.
     *
     * @param input A Drawable whose most occurring colour should be used for building an Aurora.
     * @return An Aurora that's based upon the input's most occurring colour.
     */
    public Drawable createAuroraBasedUponDrawable(Drawable input);

    /**
     * Create a new Aurora whose color is equal to the given input.
     *
     * @param colour Colour that should be used for rendering the Aurora.
     * @return An Aurora based upon the given input colour.
     */
    public Drawable createAuroraBasedUponColour(Color colour);
}
