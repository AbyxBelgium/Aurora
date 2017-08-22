package be.abyx.aurora.tests.aurora;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;

import be.abyx.aurora.aurora.AuroraFactory;
import be.abyx.aurora.aurora.BlurryAurora;
import be.abyx.aurora.aurora.CPUAuroraFactory;
import be.abyx.aurora.aurora.ParallelAuroraFactory;
import be.abyx.aurora.engine.compare.BitmapComparer;
import be.abyx.aurora.engine.compare.ColourComparer;
import be.abyx.aurora.engine.managers.AdvancedTestManager;
import be.abyx.aurora.engine.managers.BitmapManager;
import be.abyx.aurora.tests.TestConstants;

/**
 * Complete unit test of all methods in the CPUAuroraFactory class. This includes also the
 * methods that are private.
 *
 * @author Pieter Verschaffelt
 */
@RunWith(AndroidJUnit4.class)
public class CPUAuroraFactoryTest {
    @Test
    public void testMostOccurringColourLegal() throws Exception {
        AdvancedTestManager testManager = new AdvancedTestManager();
        Method mostOccurringColour = testManager.getMethodFromClass(CPUAuroraFactory.class, "determineDominantColour", Bitmap.class);

        BitmapManager bitmapManager = new BitmapManager();
        Bitmap red = bitmapManager.getBitmapFromDrawables(InstrumentationRegistry.getContext(), be.abyx.aurora.test.R.drawable.red);

        AuroraFactory factory = new CPUAuroraFactory(InstrumentationRegistry.getContext());

        int result = (int) mostOccurringColour.invoke(factory, red);

        ColourComparer comparer = new ColourComparer();
        comparer.compareColors(Color.rgb(248, 0, 0), result);

        red.recycle();

        Bitmap green = bitmapManager.getBitmapFromDrawables(InstrumentationRegistry.getContext(), be.abyx.aurora.test.R.drawable.mostly_blue);

        result = (int) mostOccurringColour.invoke(factory, green);
        comparer.compareColors(Color.rgb(24, 56, 96), result);
    }

    @Test
    public void testCreateAuroraBasedUponDrawableLegal() throws Exception {
        BitmapManager bitmapManager = new BitmapManager();
        Bitmap logo1 = bitmapManager.getBitmapFromDrawables(InstrumentationRegistry.getContext(), be.abyx.aurora.test.R.drawable.delhaize);

        AuroraFactory factory = new CPUAuroraFactory(InstrumentationRegistry.getContext());

        Bitmap result = factory.createAuroraBasedUponDrawable(logo1, new BlurryAurora(InstrumentationRegistry.getContext()), 1200, 1920);
        Bitmap expected = bitmapManager.loadBitmap(InstrumentationRegistry.getContext(), be.abyx.aurora.test.R.raw.delhaize_blurry);

        BitmapComparer comparer = new BitmapComparer();
        comparer.compareBitmaps(expected, result, TestConstants.COLOUR_ACCURACY);
    }
}
