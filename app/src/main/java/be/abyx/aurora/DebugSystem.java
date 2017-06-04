package be.abyx.aurora;

/**
 * Derivative of the System-class that contains some methods that are only executed when the app
 * is set to DEBUG-mode.
 *
 * @author Pieter Verschaffelt
 */
public class DebugSystem {
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
}
