package com.saucedemo.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Listener to apply retry analyzer to all tests
 */
public class RetryListener implements IAnnotationTransformer {
    private static final Logger logger = LogManager.getLogger(RetryListener.class);

    /**
     * Transform test annotations to add retry analyzer
     * @param annotation test annotation
     * @param testClass test class
     * @param testConstructor test constructor
     * @param testMethod test method
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void transform(
            ITestAnnotation annotation,
            Class testClass,
            Constructor testConstructor,
            Method testMethod) {

        Class<?> retryAnalyzer = annotation.getRetryAnalyzerClass();
        if (retryAnalyzer == null) {
            logger.debug("Setting retry analyzer for method: {}", testMethod.getName());
            annotation.setRetryAnalyzer(RetryAnalyzer.class);
        }
    }
}