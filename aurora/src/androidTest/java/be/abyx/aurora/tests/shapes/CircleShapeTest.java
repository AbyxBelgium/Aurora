package be.abyx.aurora.tests.shapes;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.shapes.Shape;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import be.abyx.aurora.engine.compare.BitmapComparer;
import be.abyx.aurora.engine.managers.BitmapManager;
import be.abyx.aurora.shapes.CPUShapeFactory;
import be.abyx.aurora.shapes.CircleShape;
import be.abyx.aurora.shapes.ParallelShapeFactory;
import be.abyx.aurora.shapes.RectangleShape;
import be.abyx.aurora.shapes.ShapeFactory;
import be.abyx.aurora.tests.TestConstants;

/**
 * @author Pieter Verschaffelt
 */
@RunWith(AndroidJUnit4.class)
public class CircleShapeTest {
    @Test
    public void testRenderLegal() throws Exception {
        BitmapManager bitmapManager = new BitmapManager();
        Bitmap logo1 = bitmapManager.getBitmapFromDrawables(InstrumentationRegistry.getContext(), be.abyx.aurora.test.R.drawable.delhaize);

        ShapeFactory shapeFactory = new CPUShapeFactory();

        Bitmap generated = shapeFactory.createShape(new CircleShape(InstrumentationRegistry.getContext()), logo1, Color.WHITE, 15);

        // TODO Load correct reference image here!
        Bitmap expected = bitmapManager.loadBitmap(InstrumentationRegistry.getContext(), be.abyx.aurora.test.R.raw.test_rectangle_shape_1);

        BitmapComparer comparer = new BitmapComparer();
        comparer.compareBitmaps(expected, generated, TestConstants.COLOUR_ACCURACY);
    }

    @Test
    public void testRenderParallelLegal() throws Exception {
        BitmapManager bitmapManager = new BitmapManager();
        Bitmap logo1 = bitmapManager.getBitmapFromDrawables(InstrumentationRegistry.getContext(), be.abyx.aurora.test.R.drawable.delhaize);

        ShapeFactory shapeFactory = new ParallelShapeFactory();
        ShapeFactory cpuShapeFactory = new CPUShapeFactory();

        Bitmap generated = shapeFactory.createShape(new CircleShape(InstrumentationRegistry.getContext()), logo1, Color.argb(143, 175, 175, 175), 15);
        Bitmap generated2 = cpuShapeFactory.createShape(new CircleShape(InstrumentationRegistry.getContext()), logo1, Color.argb(143, 175, 175, 175), 15);

        // TODO Load correct reference image here!
        Bitmap expected = bitmapManager.loadBitmap(InstrumentationRegistry.getContext(), be.abyx.aurora.test.R.raw.test_rectangle_shape_1);

        BitmapComparer comparer = new BitmapComparer();
        comparer.compareBitmaps(expected, generated, TestConstants.COLOUR_ACCURACY);
    }
}
