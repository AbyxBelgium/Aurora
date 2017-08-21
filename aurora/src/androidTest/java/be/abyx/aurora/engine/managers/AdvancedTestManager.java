package be.abyx.aurora.engine.managers;

import java.lang.reflect.Method;

/**
 * @author Pieter Verschaffelt
 */
public class AdvancedTestManager {
    public <T> Method getMethodFromClass(Class<T> c, String methodName, Class ... arguments) throws NoSuchMethodException {
        Method method = c.getDeclaredMethod(methodName, arguments);
        method.setAccessible(true);
        return method;
    }
}
