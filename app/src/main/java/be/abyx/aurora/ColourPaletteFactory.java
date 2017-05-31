package be.abyx.aurora;

import android.content.Context;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * @author Pieter Verschaffelt
 */
public class ColourPaletteFactory {
    private Context context;

    public ColourPaletteFactory(Context context) {
        this.context = context;
    }

    public ColourPalette getDefaultColourPalette() {
        try {
            ColourParser parser = new ColourParser();
            List<Integer> colours = parser.parseFile(context.getResources().openRawResource(R.raw.colour_palette));
            return new ColourPalette(colours);
        } catch (IOException | JSONException e) {
            throw new RuntimeException("Could not parse default colour palette!");
        }
    }
}
