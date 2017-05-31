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
    private List<Integer> colours;

    public ColourPalette(List<Integer> colours) {
        this.colours = colours;
    }

    public Integer matchToClosestColour(Integer input) {
        return null;
    }
}
