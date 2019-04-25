package com.javalab.movieapp.utils;

import java.util.HashMap;

/**
 * Created by Dauren_Altynbekov on 23-Apr-19.
 */
public class LanguageUtils {
    
    private static HashMap<String, String> languageIdToLocale = new HashMap<>();

    static {
        languageIdToLocale.put("1", "en");
        languageIdToLocale.put("2", "ru");
    }

    public static HashMap<String, String> getLanguageIdToLocale() {
        return languageIdToLocale;
    }
}
