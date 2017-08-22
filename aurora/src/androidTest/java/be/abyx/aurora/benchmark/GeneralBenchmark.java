package be.abyx.aurora.benchmark;

import android.graphics.Bitmap;
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

    private void testAuroraType(AuroraType type, AuroraFactory factory, String title) {
        BitmapManager bitmapManager = new BitmapManager();
        Bitmap logo1 = bitmapManager.getBitmapFromDrawables(InstrumentationRegistry.getContext(), be.abyx.aurora.test.R.drawable.delhaize);

        long totalTime = 0;

        for (int i = 0; i < TEST_AMOUNT; i++) {
            long start = System.currentTimeMillis();
            Bitmap result = factory.createAuroraBasedUponDrawable(logo1, type, 1200, 1920);
            long end = System.currentTimeMillis();
            result.recycle();
            totalTime += (end - start);
        }

        System.out.println(title + " benchmark total: " + totalTime + "ms");
        System.out.println(title + " benchmark average: " + totalTime / TEST_AMOUNT + "ms");
    }
}
