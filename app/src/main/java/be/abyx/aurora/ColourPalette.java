package be.abyx.aurora;

import android.graphics.Color;

import java.util.List;

/**
 * A class containing a certain amount of colours and allows for easily retrieving the closest
 * colour inside this palette.
 *
 * @author Pieter Verschaffelt
 */
public class ColourPalette {
    private List<Integer> colours;

    public ColourPalette(List<Integer> colours) {
        this.colours = colours;
    }

    public Integer matchToClosestColour(Integer input) {
        Integer closest = null;
        double smallestDistance = Double.POSITIVE_INFINITY;

        double r1 = Color.red(input);
        double g1 = Color.green(input);
        double b1 = Color.blue(input);

        for (Integer colour : colours) {
            double r2 = Color.red(colour);
            double g2 = Color.green(colour);
            double b2 = Color.blue(colour);

            double avgR = (r1 + r2) / 2;
            double deltaR = r1 - r2;
            double deltaG = g1 - g2;
            double deltaB = b1 - b2;

            double redTerm = 2 + avgR / 256.0;
            double blueTerm = 2 + (255 - avgR) / 256.0;

            double distance = Math.sqrt(redTerm * Math.pow(deltaR, 2) + 4 * Math.pow(deltaG, 2) + blueTerm * Math.pow(deltaB, 2));

            if (distance <= smallestDistance) {
                smallestDistance = distance;
                closest = colour;
            }
        }

        return closest;
    }
}
