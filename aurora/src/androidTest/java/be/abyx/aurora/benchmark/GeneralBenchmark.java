package be.abyx.aurora.benchmark;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import be.abyx.aurora.aurora.AuroraFactory;
import be.abyx.aurora.aurora.AuroraType;
import be.abyx.aurora.aurora.BlurryAurora;
import be.abyx.aurora.aurora.CPUAuroraFactory;
import be.abyx.aurora.aurora.ParallelAuroraFactory;
import be.abyx.aurora.engine.managers.BitmapManager;
import be.abyx.aurora.shapes.CPUShapeFactory;
import be.abyx.aurora.shapes.CircleShape;
import be.abyx.aurora.shapes.ParallelShapeFactory;
import be.abyx.aurora.shapes.RectangleShape;
import be.abyx.aurora.shapes.ShapeFactory;
import be.abyx.aurora.shapes.ShapeType;

/**
 * @author Pieter Verschaffelt
 */
@RunWith(AndroidJUnit4.class)
public class GeneralBenchmark {
    // The amount of tests that should be ran
    public static final int TEST_AMOUNT = 100;

    @Test
    public void blurryAuroraParallelBenchmark() {
        testAuroraType(new BlurryAurora(InstrumentationRegistry.getContext()), new ParallelAuroraFactory(InstrumentationRegistry.getContext()), "BlurryAurora parallel");
    }

    @Test
    public void blurryAuroraSingleBenchmark() {
        testAuroraType(new BlurryAurora(InstrumentationRegistry.getContext()), new CPUAuroraFactory(InstrumentationRegistry.getContext()), "BlurryAurora single");
    }

    @Test
    public void circleShapeSingleBenchmark() {
        testShapeType(new CircleShape(InstrumentationRegistry.getContext()), new CPUShapeFactory(), "CircleShape single");
    }

    @Test
    public void circleShapeParallelBenchmark() {
        testShapeType(new CircleShape(InstrumentationRegistry.getContext()), new ParallelShapeFactory(), "CircleShape parallel");
    }

    @Test
    public void rectangleShapeSingleBenchmark() {
        testShapeType(new RectangleShape(InstrumentationRegistry.getContext()), new CPUShapeFactory(), "RectangleShape single");
    }

    @Test
    public void rectangleShapeParallelBenchmark() {
        testShapeType(new RectangleShape(InstrumentationRegistry.getContext()), new ParallelShapeFactory(), "RectangleShape parallel");
    }

    private void testAuroraType(final AuroraType type, final AuroraFactory factory, String title) {
        runTimedBenchmark(new TimedBitmapOperation() {
            @Override
            public Bitmap run(Bitmap input) {
                return factory.createAuroraBasedUponDrawable(input, type, 1200, 1920);
            }
        }, title);
    }

    private void testShapeType(final ShapeType type, final ShapeFactory factory, String title) {
        runTimedBenchmark(new TimedBitmapOperation() {
            @Override
            public Bitmap run(Bitmap input) {
                return factory.createShape(type, input, Color.CYAN, 150);
            }
        }, title);
    }

    private interface TimedBitmapOperation {
        public Bitmap run(Bitmap input);
    }

    private void runTimedBenchmark(TimedBitmapOperation operation, String title) {
        BitmapManager bitmapManager = new BitmapManager();
        Bitmap logo = bitmapManager.getBitmapFromDrawables(InstrumentationRegistry.getContext(), be.abyx.aurora.test.R.drawable.delhaize);

        long totalTime = 0;

        for (int i = 0; i < TEST_AMOUNT; i++) {
            long start = System.currentTimeMillis();
            Bitmap result = operation.run(logo);
            long end = System.currentTimeMillis();
            result.recycle();
            totalTime += (end - start);
        }

        System.out.println(title + " benchmark total: " + totalTime + "ms");
        System.out.println(title + " benchmark average: " + totalTime / TEST_AMOUNT + "ms");
    }
}
