package be.abyx.aurora.engine.managers;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;

/**
 * @author Pieter Verschaffelt
 */
public class DrawableManager {
    public Drawable getDrawable(Context context, Resources.Theme theme, int drawable) {
        // The way to retrieve drawables changed since Lollipop. We also need to support older
        // Android versions and thus need this version check.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(drawable, theme);
        } else {
            return context.getResources().getDrawable(drawable);
        }
    }
}
