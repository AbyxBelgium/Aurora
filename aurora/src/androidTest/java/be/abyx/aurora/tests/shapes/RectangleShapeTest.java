package be.abyx.aurora.tests.shapes;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import be.abyx.aurora.FactoryManager;
import be.abyx.aurora.engine.managers.BitmapManager;
import be.abyx.aurora.shapes.CPUShapeFactory;
import be.abyx.aurora.shapes.ParallelShapeFactory;
import be.abyx.aurora.shapes.RectangleShape;
import be.abyx.aurora.shapes.ShapeFactory;

/**
 * @author Pieter Verschaffelt
 */
@RunWith(AndroidJUnit4.class)
public class RectangleShapeTest {
    @Test
    public void testCreateRectangleLegal() throws Exception {
        BitmapManager bitmapManager = new BitmapManager();
        Bitmap logo1 = bitmapManager.getBitmapFromDrawables(InstrumentationRegistry.getContext(), be.abyx.aurora.test.R.drawable.delhaize);

        ShapeFactory shapeFactory = new CPUShapeFactory();

        Bitmap generated = shapeFactory.createShape(new RectangleShape(InstrumentationRegistry.getContext()), logo1, Color.WHITE, 15);

        // TODO complete this test!
        int k = 0;
    }
}
