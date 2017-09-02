package be.abyx.aurora.tests;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.Random;

import be.abyx.aurora.aurora.AuroraFactory;
import be.abyx.aurora.aurora.BlurryAurora;
import be.abyx.aurora.aurora.CPUAuroraFactory;
import be.abyx.aurora.aurora.ParallelAuroraFactory;
import be.abyx.aurora.shapes.CPUShapeFactory;
import be.abyx.aurora.shapes.CircleShape;
import be.abyx.aurora.shapes.ParallelShapeFactory;
import be.abyx.aurora.shapes.RectangleShape;
import be.abyx.aurora.shapes.ShapeFactory;
import be.abyx.aurora.utilities.CropUtility;
import be.abyx.aurora.utilities.NoiseUtility;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @author Pieter Verschaffelt
 */
@RunWith(AndroidJUnit4.class)
public class MainInstrumentedTest {
    @Test
    public void testMostOccurringColour() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        Drawable redDrawable = getInstrumentation().getContext().getResources().getDrawable(be.abyx.aurora.test.R.drawable.red);
        AuroraFactory factory = new CPUAuroraFactory(appContext);

        Bitmap redImage = ((BitmapDrawable) redDrawable).getBitmap();

        Method mostOccurringColour = getMethodFromClass(factory.getClass(), "determineDominantColour", Bitmap.class);
        int output = (int) mostOccurringColour.invoke(factory, redImage);

        assertEquals("Colour should be equal to red", Color.argb(255, 255, 0, 0), output);

        Drawable greenDrawable = getInstrumentation().getContext().getResources().getDrawable(be.abyx.aurora.test.R.drawable.mostly_blue);

        Bitmap greenImage = ((BitmapDrawable) greenDrawable).getBitmap();

        output = (int) mostOccurringColour.invoke(factory, greenImage);
        assertEquals("Colour should be equal to green", Color.argb(255, 95, 135, 0), output);
    }

    @Test
    public void testSimpleGradientAurora() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        Drawable redDrawable = getInstrumentation().getContext().getResources().getDrawable(be.abyx.aurora.test.R.drawable.red);
        AuroraFactory factory = new CPUAuroraFactory(appContext);

        Bitmap redImage = ((BitmapDrawable) redDrawable).getBitmap();

        Method mostOccurringColour = getMethodFromClass(factory.getClass(), "determineDominantColour", Bitmap.class);
        int output = (int) mostOccurringColour.invoke(factory, redImage);

        Bitmap gradient = factory.createAuroraBasedUponColour(output, 400, 800);

        saveImageToExternalStorage(gradient, Bitmap.CompressFormat.PNG);
    }

    @Test
    public void testBlurryAurora() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        Drawable redDrawable = getInstrumentation().getContext().getResources().getDrawable(be.abyx.aurora.test.R.drawable.red);
        AuroraFactory factory = new ParallelAuroraFactory(appContext);

        Bitmap redImage = ((BitmapDrawable) redDrawable).getBitmap();

        Bitmap gradient = factory.createAuroraBasedUponDrawable(redImage, new BlurryAurora(appContext), 1200, 1920);

        saveImageToExternalStorage(gradient, Bitmap.CompressFormat.PNG);
    }

    @Test
    public void testBlurryAuroraNoSaturation() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        AuroraFactory factory = new ParallelAuroraFactory(appContext);

        Bitmap gradient = factory.createAuroraBasedUponColour(Color.BLACK, new BlurryAurora(appContext), 1200, 1920);

        saveImageToExternalStorage(gradient, Bitmap.CompressFormat.JPEG);
    }

    @Test
    public void testMagicCrop() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        Drawable logo = getInstrumentation().getContext().getResources().getDrawable(be.abyx.aurora.test.R.drawable.delhaize);
        CropUtility cropUtility = new CropUtility();

        Bitmap cropped = cropUtility.magicCrop(((BitmapDrawable) logo).getBitmap(), Color.WHITE, 0.25f);
        saveImageToExternalStorage(cropped, Bitmap.CompressFormat.PNG);
    }

    @Test
    public void testCircleShape() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        Drawable logo = getInstrumentation().getContext().getResources().getDrawable(be.abyx.aurora.test.R.drawable.delhaize);

        CropUtility cropUtility = new CropUtility();
        Bitmap cropped = cropUtility.magicCrop(((BitmapDrawable) logo).getBitmap(), Color.WHITE, 0.25f);

        ShapeFactory factory = new ParallelShapeFactory();
        Bitmap result = factory.createShape(new CircleShape(appContext), cropped, Color.argb(143, 255, 255, 255), 150);

        saveImageToExternalStorage(result, Bitmap.CompressFormat.PNG);
    }

    @Test
    public void testCircleShapeRectangleInput() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        Drawable logo = getInstrumentation().getContext().getResources().getDrawable(be.abyx.aurora.test.R.drawable.spar);

        CropUtility cropUtility = new CropUtility();
        Bitmap cropped = cropUtility.magicCrop(((BitmapDrawable) logo).getBitmap(), Color.WHITE, 0.25f);

        ShapeFactory factory = new ParallelShapeFactory();
        Bitmap result = factory.createShape(new CircleShape(appContext), cropped, Color.argb(143, 255, 255, 255), 150);

        saveImageToExternalStorage(result, Bitmap.CompressFormat.PNG);
    }

    @Test
    public void testCircleShapeBackground() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        Drawable logo = getInstrumentation().getContext().getResources().getDrawable(be.abyx.aurora.test.R.drawable.delhaize);

        CropUtility cropUtility = new CropUtility();
        Bitmap cropped = cropUtility.magicCrop(((BitmapDrawable) logo).getBitmap(), Color.WHITE, 0.25f);

        AuroraFactory factory = new CPUAuroraFactory(appContext);
        // Make our Bitmap large enough!
        Bitmap gradient = factory.createAuroraBasedUponColour(Color.parseColor("#4C4F5C"), 1000, 1000);

        NoiseUtility utils = new NoiseUtility(appContext);
        Bitmap noise = utils.addNoise(gradient, 0.4f, 10);

        ShapeFactory shapeFactory = new ParallelShapeFactory();
        Bitmap result = shapeFactory.createShape(new CircleShape(appContext), cropped, noise, 150);

        saveImageToExternalStorage(result, Bitmap.CompressFormat.PNG);
    }

    @Test
    public void testRectangularShape() throws Exception {
        Drawable logo = getInstrumentation().getContext().getResources().getDrawable(be.abyx.aurora.test.R.drawable.delhaize);

        ShapeFactory factory = new ParallelShapeFactory();
        Bitmap blab = ((BitmapDrawable) logo).getBitmap();
        Bitmap result = factory.createShape(new RectangleShape(InstrumentationRegistry.getContext()), ((BitmapDrawable) logo).getBitmap(), Color.argb(143, 255, 255, 255), 15);

        saveImageToExternalStorage(result, Bitmap.CompressFormat.PNG);
    }

    @Test
    public void testRectangularCrop() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        Drawable logo = getInstrumentation().getContext().getResources().getDrawable(be.abyx.aurora.test.R.drawable.spar2);
        CropUtility cropUtility = new CropUtility();

        Bitmap cropped = cropUtility.rectangularCrop(((BitmapDrawable) logo).getBitmap(), Color.WHITE, 0.25f);
        saveImageToExternalStorage(cropped, Bitmap.CompressFormat.PNG);
    }

    @Test
    public void testNoiseGradient() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        AuroraFactory factory = new CPUAuroraFactory(appContext);
        Bitmap gradient = factory.createAuroraBasedUponColour(Color.parseColor("#4C4F5C"), 1000, 1000);

        NoiseUtility utils = new NoiseUtility(appContext);
        Bitmap noise = utils.addNoise(gradient, 0.4f, 10);

        saveImageToExternalStorage(noise, Bitmap.CompressFormat.PNG);
    }

    @Test
    public void testBlackTransparentCrop() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        Drawable qr = getInstrumentation().getContext().getResources().getDrawable(be.abyx.aurora.test.R.drawable.qr);

        CropUtility cropUtility = new CropUtility();
        Bitmap output = cropUtility.rectangularCrop(((BitmapDrawable) qr).getBitmap(), Color.TRANSPARENT, 0);

        saveImageToExternalStorage(output, Bitmap.CompressFormat.PNG);
    }

    private void saveImageToExternalStorage(Bitmap finalBitmap, Bitmap.CompressFormat format) {
        File myDir = getInstrumentation().getContext().getExternalFilesDir("gradient");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname;
        if (format.equals(Bitmap.CompressFormat.JPEG)) {
            fname = "Image-" + n + ".jpg";
        } else {
            fname = "Image-" + n + ".png";
        }
        File file = new File(myDir, fname);
        System.out.println(file.getAbsolutePath());
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(format, 100, out);
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
