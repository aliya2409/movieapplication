package com.javalab.movieapp.dao;

import com.javalab.movieapp.entities.Language;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LanguageDAO implements AbstractDAO<Long, Language> {

    public static final String LANGUAGE_ID_COLUMN = "language_id";
    public static final String LANGUAGE_NAME_COLUMN = "language_name";

    public static final String FIND_ALL_LANGUAGES_SQL_QUERY = "SELECT * FROM language;";
    public static final String FIND_LANGUAGE_BY_ID_SQL_QUERY = "SELECT * FROM language WHERE language_id = ?;";
    public static final String DELETE_LANGUAGE_BY_ID_SQL_QUERY = "DELETE FROM language WHERE language_id = ?;";
    public static final String ADD_LANGUAGE_SQL_QUERY = "INSERT INTO language(language_name) VALUES (?);";
    public static final String UPDATE_LANGUAGE_SQL_QUERY = "UPDATE language SET language_name = ? WHERE language_id = ?;";

    private final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();

    public List<Language> findAll() throws SQLException {
        List<Language> languages = new ArrayList<>();
        Connection cn = CONNECTION_POOL.takeConnection();
        try (Statement st = cn.createStatement()) {
            try (ResultSet resultSet =
                         st.executeQuery(FIND_ALL_LANGUAGES_SQL_QUERY)) {
                while (resultSet.next()) {
                    Language language = new Language();
                    language.setId(resultSet.getLong(LANGUAGE_ID_COLUMN));
                    language.setName(resultSet.getString(LANGUAGE_NAME_COLUMN));
                    languages.add(language);
                }
            }
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return languages;
    }

    public Language findEntityById(Long id) throws SQLException {
        Language language = new Language();
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(FIND_LANGUAGE_BY_ID_SQL_QUERY)) {
            st.setLong(1, id);
            try (ResultSet resultSet =
                         st.executeQuery()) {
                while (resultSet.next()) {
                    language.setId(resultSet.getLong(LANGUAGE_ID_COLUMN));
                    language.setName(resultSet.getString(LANGUAGE_NAME_COLUMN));
                }
            }
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return language;
    }

    @Override
    public boolean delete(Long id) throws SQLException {
        boolean isDeleted = delete(id, DELETE_LANGUAGE_BY_ID_SQL_QUERY);
        return isDeleted;
    }

    @Override
    public boolean create(Language entity) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(ADD_LANGUAGE_SQL_QUERY)) {
            st.setString(1, entity.getName());
            st.executeUpdate();
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return true;
    }

    @Override
    public boolean update(Language entity) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(UPDATE_LANGUAGE_SQL_QUERY)) {
            st.setString(1, entity.getName());
            st.setLong(2, entity.getId());
            st.executeUpdate();
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return true;
    }

    @Override
    public List<Language> findAll(long languageId) {
        return null;  //todo throw exception
    }

    @Override
    public Language findEntityById(Long id, long languageId) {
        return null;
    }
}
