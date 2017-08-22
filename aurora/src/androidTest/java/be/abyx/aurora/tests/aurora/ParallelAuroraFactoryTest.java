package be.abyx.aurora.tests.aurora;

import android.graphics.Bitmap;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import be.abyx.aurora.aurora.AuroraFactory;
import be.abyx.aurora.aurora.BlurryAurora;
import be.abyx.aurora.aurora.ParallelAuroraFactory;
import be.abyx.aurora.engine.compare.BitmapComparer;
import be.abyx.aurora.engine.managers.BitmapManager;
import be.abyx.aurora.tests.TestConstants;

/**
 * @author Pieter Verschaffelt
 */
@RunWith(AndroidJUnit4.class)
public class ParallelAuroraFactoryTest  {
    @Test
    public void testCreateAuroraBasedUponDrawableLegal() throws Exception {
        BitmapManager bitmapManager = new BitmapManager();
        Bitmap logo1 = bitmapManager.getBitmapFromDrawables(InstrumentationRegistry.getContext(), be.abyx.aurora.test.R.drawable.delhaize);

        AuroraFactory factory = new ParallelAuroraFactory(InstrumentationRegistry.getContext());

        Bitmap result = factory.createAuroraBasedUponDrawable(logo1, new BlurryAurora(InstrumentationRegistry.getContext()), 1200, 1920);
        Bitmap expected = bitmapManager.loadBitmap(InstrumentationRegistry.getContext(), be.abyx.aurora.test.R.raw.delhaize_blurry);

        BitmapComparer comparer = new BitmapComparer();
        comparer.compareBitmaps(expected, result, TestConstants.COLOUR_ACCURACY);
    }
}
