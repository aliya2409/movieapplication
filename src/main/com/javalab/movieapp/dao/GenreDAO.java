package com.javalab.movieapp.dao;

import com.javalab.movieapp.entities.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO implements AbstractDAO<Long, Genre> {

    public static final String GENRE_ID_COLUMN = "genre_id";
    public static final String GENRE_NAME_COLUMN = "genre_name";

    public static final String FIND_ALL_GENRES_SQL_QUERY = "SELECT genre_id, genre_name FROM genre WHERE language_id = ?;";
    public static final String FIND_GENRE_BY_ID_SQL_QUERY = "SELECT genre_id, genre_name FROM genre WHERE language_id = ? AND genre_id = ?;";
    public static final String DELETE_GENRE_BY_ID_SQL_QUERY = "DELETE FROM genre WHERE genre_id = ?;";
    public static final String ADD_GENRE_SQL_QUERY = "INSERT INTO genre(genre_name, language_id) VALUES (?, ?);";
    public static final String UPDATE_GENRE_SQL_QUERY = "UPDATE genre SET genre_name = ? WHERE genre_id = ? AND language_id = ?;";
    public static final String FIND_MOVIE_GENRES_SQL_QUERY = "SELECT g.genre_id, g.genre_name FROM genre g JOIN movie_genre USING (genre_id) WHERE movie_id = ? AND language_id = ?;";
    public static final String ADD_GENRE_TO_MOVIE_SQL_QUERY = "INSERT INTO movie_genre (movie_id, genre_id) VALUES (?, (SELECT genre_id FROM genre WHERE genre_name = ?));";
    public static final String DELETE_MOVIE_GENRE_SQL_QUERY = "DELETE FROM movie_genre WHERE genre_id = ? AND movie_id = ?;";
    public static final String ADD_GENRE_LOCALE_SQL_QUERY = "INSERT INTO genre(genre_id, language_id, genre_name) VALUES (?, ?, ?);";

    private final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();

    public List<Genre> findMovieGenres(long movieId, long languageId) throws SQLException {
        List<Genre> genres = new ArrayList<>();
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(FIND_MOVIE_GENRES_SQL_QUERY)) {
            st.setLong(1, movieId);
            st.setLong(2, languageId);
            try (ResultSet resultSet = st.executeQuery()) {
                genres = fillListWithGenres(genres, resultSet);
            }
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return genres;
    }

    public boolean addGenreToMovie(long movieId, String genreName) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(ADD_GENRE_TO_MOVIE_SQL_QUERY)) {
            st.setLong(1, movieId);
            st.setString(2, genreName);
            st.executeUpdate();
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return true;
    }

    public boolean deleteMovieGenre(long movieId, long genreId) throws SQLException {
        return executeUpdateWithTwoId(genreId, movieId, DELETE_MOVIE_GENRE_SQL_QUERY);
    }

    @Override
    public List<Genre> findAll(long languageId) throws SQLException {
        List<Genre> genres = new ArrayList<>();
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(FIND_ALL_GENRES_SQL_QUERY)) {
            st.setLong(1, languageId);
            try (ResultSet resultSet = st.executeQuery()) {
                genres = fillListWithGenres(genres, resultSet);
            }
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return genres;
    }

    @Override
    public Genre findEntityById(Long genreId, long languageId) throws SQLException {
        Genre genre = new Genre();
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(FIND_GENRE_BY_ID_SQL_QUERY)) {
            st.setLong(1, languageId);
            st.setLong(2, genreId);
            try (ResultSet resultSet = st.executeQuery()) {
                while (resultSet.next()) {
                    genre.setId(resultSet.getLong(GENRE_ID_COLUMN));
                    genre.setName(resultSet.getString(GENRE_NAME_COLUMN));
                }
            }
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return genre;
    }

    public boolean addGenreLocale(Long genreId, Long languageId, String genreName) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(ADD_GENRE_LOCALE_SQL_QUERY)) {
            st.setLong(1, genreId);
            st.setLong(2, languageId);
            st.setString(3, genreName);
            st.executeUpdate();
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return true;
    }

    public boolean create(Genre entity, Long languageId) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(ADD_GENRE_SQL_QUERY)) {
            st.setString(1, entity.getName());
            st.setLong(2, languageId);
            st.executeUpdate();
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return true;
    }

    public boolean update(Genre entity, Long languageId) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(UPDATE_GENRE_SQL_QUERY)) {
            st.setString(1, entity.getName());
            st.setLong(2, entity.getId());
            st.setLong(3, languageId);
            st.executeUpdate();
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return true;
    }

    private List<Genre> fillListWithGenres(List<Genre> genres, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Genre genre = new Genre();
            genre.setId(resultSet.getLong(GENRE_ID_COLUMN));
            genre.setName(resultSet.getString(GENRE_NAME_COLUMN));
            genres.add(genre);
        }
        return genres;
    }

    @Override
    public boolean delete(Long id) throws SQLException {
        boolean isDeleted = delete(id, DELETE_GENRE_BY_ID_SQL_QUERY);
        return isDeleted;
    }

    public Genre findEntityById(Long id) {
        return null;
    }

    @Override
    public boolean create(Genre entity) {
        return false;
    }

    @Override
    public boolean update(Genre entity) {
        return false;
    }

}
