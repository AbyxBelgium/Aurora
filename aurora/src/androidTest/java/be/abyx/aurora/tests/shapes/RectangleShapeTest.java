package be.abyx.aurora.tests.shapes;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import be.abyx.aurora.FactoryManager;
import be.abyx.aurora.engine.compare.BitmapComparer;
import be.abyx.aurora.engine.managers.BitmapManager;
import be.abyx.aurora.shapes.CPUShapeFactory;
import be.abyx.aurora.shapes.ParallelShapeFactory;
import be.abyx.aurora.shapes.RectangleShape;
import be.abyx.aurora.shapes.ShapeFactory;
import be.abyx.aurora.tests.TestConstants;

/**
 * @author Pieter Verschaffelt
 */
@RunWith(AndroidJUnit4.class)
public class RectangleShapeTest {
    @Test
    public void testRenderLegal() throws Exception {
        BitmapManager bitmapManager = new BitmapManager();
        Bitmap logo1 = bitmapManager.getBitmapFromDrawables(InstrumentationRegistry.getContext(), be.abyx.aurora.test.R.drawable.delhaize);

        ShapeFactory shapeFactory = new CPUShapeFactory();

        Bitmap generated = shapeFactory.createShape(new RectangleShape(InstrumentationRegistry.getContext()), logo1, Color.argb(143, 255, 255, 255), 15);

        Bitmap expected = bitmapManager.loadBitmap(InstrumentationRegistry.getContext(), be.abyx.aurora.test.R.raw.test_rectangle_shape_1);

        BitmapComparer comparer = new BitmapComparer();
        comparer.compareBitmaps(expected, generated, TestConstants.COLOUR_ACCURACY);
    }

    @Test
    public void testRenderParallelLegal() throws Exception {
        BitmapManager bitmapManager = new BitmapManager();
        Bitmap logo1 = bitmapManager.getBitmapFromDrawables(InstrumentationRegistry.getContext(), be.abyx.aurora.test.R.drawable.delhaize);

        ShapeFactory shapeFactory = new ParallelShapeFactory();

        Bitmap generated = shapeFactory.createShape(new RectangleShape(InstrumentationRegistry.getContext()), logo1, Color.argb(143, 255, 255, 255), 15);

        Bitmap expected = bitmapManager.loadBitmap(InstrumentationRegistry.getContext(), be.abyx.aurora.test.R.raw.test_rectangle_shape_1);

        BitmapComparer comparer = new BitmapComparer();
        comparer.compareBitmaps(expected, generated, TestConstants.COLOUR_ACCURACY);
    }
}
