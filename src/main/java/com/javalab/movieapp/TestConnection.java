package com.javalab.movieapp;

import com.javalab.movieapp.dao.LanguageDAO;
import com.javalab.movieapp.entities.Language;

import java.sql.SQLException;
import java.util.List;

public class TestConnection {
    public static void main(String[] args) {
        LanguageDAO languageDAO = new LanguageDAO();
        Language language = new Language();
        language.setName("test");
        try {
            languageDAO.create(language);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            List<Language> foundLanguages = languageDAO.findAll();
            for (Language language1 : foundLanguages) {
                System.out.println(language1.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
