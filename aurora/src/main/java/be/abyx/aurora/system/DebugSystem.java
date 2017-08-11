package be.abyx.aurora.system;

import be.abyx.aurora.BuildConfig;

/**
 * Derivative of the System-class that contains some methods that are only executed when the app
 * is set to DEBUG-mode.
 *
 * @author Pieter Verschaffelt
 */
public class DebugSystem {
    private static long time = 0;

    public static void println(String data) {
        if (BuildConfig.DEBUG) {
            System.out.println(data);
        }
    }

    public static void print(String data) {
        if (BuildConfig.DEBUG) {
            System.out.print(data);
        }
    }

    /**
     * Start the System's timer. A call to <pre>endTimer()</pre> must always have a corresponding
     * call to <pre>startTimer()</pre> before it.
     */
    public static void startTimer() {
        time = System.currentTimeMillis();
    }

    /**
     * End the System's timer and output the measured execution time to the console. Time will only
     * be outputted when the build configuration is set to debug.
     *
     * @param message An extra message that will be printed when outputting the execution time. This
     *                message is useful for identifying multiple time outputs in one console.
     */
    public static void endTimer(String message) {
        long duration = System.currentTimeMillis() - time;
        if (BuildConfig.DEBUG) {
            System.out.println(message + " took " + duration + "ms");
        }
    }
}
