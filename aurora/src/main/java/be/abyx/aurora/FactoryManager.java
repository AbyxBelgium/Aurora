package be.abyx.aurora;

import android.content.Context;
import android.os.Build;

import be.abyx.aurora.aurora.AuroraFactory;
import be.abyx.aurora.aurora.ParallelAuroraFactory;
import be.abyx.aurora.aurora.SingleAuroraFactory;

/**
 * This class checks all system resources and available device API version and decides which factory
 * is most suitable for the device in question.
 *
 * @author Pieter Verschaffelt
 */
public class FactoryManager {
    public AuroraFactory getRecommendedAuroraFactory(Context context) {
        if (Build.VERSION.SDK_INT < 21) {
            return new SingleAuroraFactory(context);
        } else {
            return new ParallelAuroraFactory(context);
        }
    }


}
