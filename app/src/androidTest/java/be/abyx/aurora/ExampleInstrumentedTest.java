package be.abyx.aurora;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.mock.MockContext;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;
import java.lang.reflect.Method;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

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
        AuroraFactory factory = new AcceleratedAuroraFactory(appContext);

        Bitmap redImage = ((BitmapDrawable) redDrawable).getBitmap();

        Method mostOccurringColour = getMethodFromClass(factory.getClass(), "determineMostOccurringColour", Bitmap.class);
        mostOccurringColour.invoke(factory, redImage);
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
