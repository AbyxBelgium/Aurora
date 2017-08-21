package be.abyx.aurora.engine.managers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import java.io.InputStream;

/**
 * @author Pieter Verschaffelt
 */
public class BitmapManager {
    public Bitmap getBitmapFromDrawables(Context context, int id) {
        DrawableManager drawableManager = new DrawableManager();
        BitmapDrawable temp = (BitmapDrawable) drawableManager.getDrawable(context, null, id);
        return temp.getBitmap();
    }

    public Bitmap loadBitmap(Context context, int id) {
        InputStream is = context.getResources().openRawResource(id);
        return BitmapFactory.decodeStream(is);
    }
}
