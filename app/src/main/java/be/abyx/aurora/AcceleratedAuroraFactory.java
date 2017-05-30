package be.abyx.aurora;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicHistogram;

/**
 * This factory is a concrete AuroraFactory implementation that uses RenderScript and other advanced
 * techniques to speed up the creation of these Aurora's.
 *
 * @author Pieter Verschaffelt
 */
public class AcceleratedAuroraFactory implements AuroraFactory {
    private Context context;

    public AcceleratedAuroraFactory(Context context) {
        this.context = context;
    }

    @Override
    public Drawable createAuroraBasedUponDrawable(Bitmap input) {
        return null;
    }

    @Override
    public Drawable createAuroraBasedUponColour(Color colour) {
        return null;
    }

    private void determineMostOccurringColour(Bitmap input) {
        RenderScript rs = RenderScript.create(this.context);

        Allocation allocatedInput = Allocation.createFromBitmap(rs, input);

        ScriptIntrinsicHistogram histogramScript = ScriptIntrinsicHistogram.create(rs, Element.U8_4(rs));
        histogramScript.setOutput();
    }
}
