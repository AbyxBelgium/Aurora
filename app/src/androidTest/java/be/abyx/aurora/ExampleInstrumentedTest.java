package be.abyx.aurora;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @author Pieter Verschaffelt
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void testMostOccurringColour() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        Drawable redDrawable = getInstrumentation().getContext().getResources().getDrawable(be.abyx.aurora.test.R.drawable.red, null);
        AuroraFactory factory = new DefaultAuroraFactory(appContext);

        Bitmap redImage = ((BitmapDrawable) redDrawable).getBitmap();

        Method mostOccurringColour = getMethodFromClass(factory.getClass(), "determineDominantColour", Bitmap.class);
        int output = (int) mostOccurringColour.invoke(factory, redImage);

        assertEquals("Colour should be equal to red", Color.argb(255, 255, 0, 0), output);

        Drawable greenDrawable = getInstrumentation().getContext().getResources().getDrawable(be.abyx.aurora.test.R.drawable.mostly_green, null);

        Bitmap greenImage = ((BitmapDrawable) greenDrawable).getBitmap();

        output = (int) mostOccurringColour.invoke(factory, greenImage);
        assertEquals("Colour should be equal to green", Color.argb(255, 95, 135, 0), output);
    }

    /**
     * We're using reflection here to test private methods from a class.
     */
    private <T> Method getMethodFromClass(Class<T> c, String methodName, Class ... arguments) throws NoSuchMethodException {
        Method method = c.getDeclaredMethod(methodName, arguments);
        method.setAccessible(true);
        return method;
    }
}
