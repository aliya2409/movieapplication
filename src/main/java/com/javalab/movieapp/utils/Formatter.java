package com.javalab.movieapp.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.javalab.movieapp.Constants.DATE_FORMAT_KEY;
import static com.javalab.movieapp.Constants.LOCALIZATION_BUNDLE;

public class Formatter {

    public static String formatDate(Locale locale, LocalDate date){
        ResourceBundle localizedInfo = ResourceBundle.getBundle(LOCALIZATION_BUNDLE, locale);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(localizedInfo.getString(DATE_FORMAT_KEY));
        return date.format(formatter);
    }
}
