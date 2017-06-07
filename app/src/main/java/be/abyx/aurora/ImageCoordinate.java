package be.abyx.aurora;

/**
 * This class represents an (x, y)-position in an image.
 *
 * @author Pieter Verschaffelt
 */
public class ImageCoordinate {
    private int x;
    private int y;

    public ImageCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
