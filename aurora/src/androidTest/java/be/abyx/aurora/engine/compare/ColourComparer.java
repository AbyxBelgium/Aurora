package be.abyx.aurora.engine.compare;

import android.graphics.Color;

import junit.framework.Assert;

/**
 * @author Pieter Verschaffelt
 */
public class ColourComparer {
    public void compareColors(int original, int toCompare) {
        Assert.assertEquals("Alpha component of colours should be equal", Color.alpha(original), Color.alpha(toCompare));
        Assert.assertEquals("Red component of colours should be equal", Color.red(original), Color.red(toCompare));
        Assert.assertEquals("Green component of colours should be equal", Color.green(original), Color.green(toCompare));
        Assert.assertEquals("Blue component of colours should be equal", Color.blue(original), Color.blue(toCompare));
    }

    public void compareColors(int original, int toCompare, double accuracy) {
        compareComponent("Alpha", Color.alpha(original), Color.alpha(toCompare), accuracy);
        compareComponent("Red", Color.red(original), Color.red(toCompare), accuracy);
        compareComponent("Green", Color.green(original), Color.green(toCompare), accuracy);
        compareComponent("Blue", Color.blue(original), Color.blue(toCompare), accuracy);
    }

    private void compareComponent(String name, int original, int toCompare, double accuracy) {
        double lowerBound = Math.max(0, original - 255 * accuracy);
        double higherBound = Math.min(255, original + 255 * accuracy);

        if (lowerBound > toCompare || higherBound < toCompare) {
            Assert.failNotSame(name + " component of colours should be equal", original, toCompare);
        }
    }
}
