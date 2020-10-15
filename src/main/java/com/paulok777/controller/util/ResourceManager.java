package com.paulok777.controller.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceManager {
    private static final String BUNDLE_NAME = "messages";
    private static final ResourceBundle resourceBundleEn = ResourceBundle.getBundle(BUNDLE_NAME, Locale.ENGLISH);
    private static final ResourceBundle resourceBundleUa = ResourceBundle.getBundle(BUNDLE_NAME, new Locale("ua"));

    public static String getMessage(String key, Locale locale) {
        return locale.equals(Locale.ENGLISH) ? resourceBundleEn.getString(key) :
                resourceBundleUa.getString(key);
    }
}
