package be.abyx.aurora;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.deps.guava.util.concurrent.ExecutionError;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.Random;

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

    @Test
    public void testSimpleGradientAurora() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        Drawable redDrawable = getInstrumentation().getContext().getResources().getDrawable(be.abyx.aurora.test.R.drawable.red, null);
        AuroraFactory factory = new DefaultAuroraFactory(appContext);

        Bitmap redImage = ((BitmapDrawable) redDrawable).getBitmap();

        Method mostOccurringColour = getMethodFromClass(factory.getClass(), "determineDominantColour", Bitmap.class);
        int output = (int) mostOccurringColour.invoke(factory, redImage);

        Bitmap gradient = factory.createAuroraBasedUponColour(output, 400, 800);

        saveImageToExternalStorage(gradient);
    }

    @Test
    public void testBlurryAurora() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        Drawable redDrawable = getInstrumentation().getContext().getResources().getDrawable(be.abyx.aurora.test.R.drawable.red, null);
        AuroraFactory factory = new DefaultAuroraFactory(appContext);

        Bitmap redImage = ((BitmapDrawable) redDrawable).getBitmap();

        Method mostOccurringColour = getMethodFromClass(factory.getClass(), "determineDominantColour", Bitmap.class);
        int output = (int) mostOccurringColour.invoke(factory, redImage);

        Bitmap gradient = factory.createAuroraBasedUponColour(output, new BlurryAurora(appContext), 1200, 1920);

        saveImageToExternalStorage(gradient);
    }

    private void saveImageToExternalStorage(Bitmap finalBitmap) {
        File myDir = getInstrumentation().getContext().getExternalFilesDir("gradient");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        System.out.println(file.getAbsolutePath());
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 98, out);
            out.flush();
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

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
