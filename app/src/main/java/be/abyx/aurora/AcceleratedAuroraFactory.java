package be.abyx.aurora;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

/**
 * This factory is a concrete AuroraFactory implementation that uses RenderScript and other advanced
 * techniques to speed up the creation of these Aurora's.
 *
 * @author Pieter Verschaffelt
 */
public class AcceleratedAuroraFactory implements AuroraFactory {
    @Override
    public Drawable createAuroraBasedUponDrawable(Drawable input) {
        return null;
    }

    @Override
    public Drawable createAuroraBasedUponColour(Color colour) {
        return null;
    }

    private void determineMostOccurringColour(Drawable input) {

    }
}
