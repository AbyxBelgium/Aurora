package be.abyx.aurora;

import android.graphics.Color;

import java.util.List;

/**
 * A class containing a certain amount of colours and allows for easily retrieving the closest
 * colour inside this palet.
 *
 * @author Pieter Verschaffelt
 */
public class ColourPalette {
    private List<Color> colours;

    public ColourPalette(List<Color> colours) {
        this.colours = colours;
    }

    public Color matchToClosestColour(Color input) {
        return null;
    }
}
