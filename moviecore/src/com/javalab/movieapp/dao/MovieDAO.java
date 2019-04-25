package com.javalab.movieapp.dao;

import com.javalab.movieapp.entities.Movie;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MovieDAO implements AbstractDAO<Long, Movie> {

    private static final String MOVIE_TITLE_ORIGINAL_COLUMN = "movie_title_original";
    private static final String MOVIE_DURATION_COLUMN = "movie_duration";
    private static final String MOVIE_BUDGET_COLUMN = "movie_budget";
    private static final String MOVIE_RELEASE_DATE_COLUMN = "movie_release_date";
    private static final String MOVIE_IMAGE_COLUMN = "movie_image";
    private static final String MOVIE_DESCRIPTION_COLUMN = "movie_description";
    private static final String MOVIE_TITLE_TRANSLATED_COLUMN = "movie_title_translated";
    private static final String MOVIE_ID_COLUMN = "movie_id";
    private static final String MOVIE_RATE_COLUMN = "movie_rate";
    private static final String MOVIE_RATING_COLUMN = "movie_rating";
    private static final String IS_LIKED_COLUMN = "is_liked";

    private static final String UPDATE_MOVIE_RATING_SQL_QUERY = "UPDATE movie SET movie_rating = (SELECT FORMAT(AVG (movie_rate), 1) FROM movie_rate WHERE movie_rate.movie_id = ?) WHERE movie_id = ?;";
    private static final String LIKE_MOVIE_FIRST_TIME_SQL_QUERY = "INSERT INTO movie_rate (user_id, movie_id, is_liked) VALUES (?, ?, 1);";
    private static final String LIKE_MOVIE_UPDATE_SQL_QUERY = "UPDATE movie_rate SET is_liked = 1 WHERE user_id = ? AND movie_id = ?;";
    private static final String UNLIKE_MOVIE_SQL_QUERY = "UPDATE movie_rate SET is_liked = 0 WHERE user_id = ? AND movie_id = ?;";
    private static final String ADD_MOVIE_INFO_SQL_QUERY = "INSERT INTO movie_info (movie_id, language_id, movie_title_translated, movie_description) VALUES ((SELECT m.movie_id FROM movie m WHERE m.movie_title_original = ?), ?, ?, ?);";
    private static final String UPDATE_MOVIE_INFO_SQL_QUERY = "UPDATE movie_info SET movie_title_translated = ?, movie_description = ? WHERE movie_id = (SELECT m.movie_id FROM movie m WHERE m.movie_title_original = ?) AND language_id = ?;";
    private static final String FIND_ALL_MOVIES_SQL_QUERY = "SELECT m.movie_id, m.movie_title_original, m.movie_duration, m.movie_budget, m.movie_release_date, m.movie_image, m.movie_rating, (SELECT mi.movie_title_translated FROM movie_info mi WHERE m.movie_id = mi.movie_id AND mi.language_id = ?) AS movie_title_translated, (SELECT mi.movie_description FROM movie_info mi WHERE m.movie_id = mi.movie_id AND mi.language_id = ?) AS movie_description FROM movie m;";
    private static final String FIND_MOVIE_BY_ID_SQL_QUERY = "SELECT m.movie_id, m.movie_title_original, m.movie_duration, m.movie_budget, m.movie_release_date, m.movie_image, m.movie_rating, (SELECT mi.movie_title_translated FROM movie_info mi WHERE mi.movie_id = ? AND mi.language_id = ?) AS movie_title_translated, (SELECT mi.movie_description FROM movie_info mi WHERE mi.movie_id = ? AND mi.language_id = ?) AS movie_description FROM movie m WHERE m.movie_id = ?;";
    private static final String DELETE_MOVIE_BY_ID_SQL_QUERY = "DELETE FROM movie WHERE movie_id = ?;";
    private static final String ADD_MOVIE_SQL_QUERY = "INSERT INTO movie(movie_title_original, movie_duration, movie_budget, movie_release_date, movie_image) VALUES (?, ?, ?, ?, ?);";
    private static final String UPDATE_MOVIE_SQL_QUERY = "UPDATE movie SET movie_title_original = ?, movie_duration = ?, movie_budget = ?, movie_release_date = ?, movie_image = ? WHERE movie_id = ?;";
    private static final String ADD_MOVIE_RATE_SQL_QUERY = "INSERT INTO movie_rate (movie_id, user_id, movie_rate) VALUES (?, ?, ?);";
    private static final String UPDATE_MOVIE_RATE_SQL_QUERY = "UPDATE movie_rate SET movie_rate = ? WHERE movie_id = ? AND user_id = ?;";
    private static final String FIND_LIKED_MOVIES_SQL_QUERY = "SELECT m.movie_id, m.movie_title_original, m.movie_duration, m.movie_budget, m.movie_release_date, m.movie_image, m.movie_rating, (SELECT mi.movie_title_translated FROM movie_info mi WHERE m.movie_id = mi.movie_id AND mi.language_id = ?) AS movie_title_translated, (SELECT mi.movie_description FROM movie_info mi WHERE m.movie_id = mi.movie_id AND mi.language_id = ?) AS movie_description FROM movie m JOIN movie_rate mr USING (movie_id) WHERE mr.user_id = ? AND mr.is_liked = 1;";
    private static final String FIND_MOVIE_RATE_RECORD_SQL_QUERY = "SELECT user_id FROM movie_rate WHERE movie_id = ? AND user_id = ?;";
    private static final String FIND_USERS_LIKE_RATE_SQL_QUERY = "SELECT movie_rate, is_liked FROM movie_rate WHERE movie_id = ? AND user_id = ?";
    private static final String SEARCH_MOVIES_SQL_QUERY = "SELECT m.movie_id, m.movie_title_original, m.movie_duration, m.movie_budget, m.movie_release_date, m.movie_image, m.movie_rating, mi.movie_title_translated, mi.movie_description FROM movie m JOIN movie_info mi USING (movie_id) WHERE (m.movie_title_original LIKE ? AND mi.language_id = ?) OR (mi.movie_title_translated LIKE ? AND mi.language_id = ?);";

    private final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();


    public void addMovieInfo(Movie movie, long languageId) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(ADD_MOVIE_INFO_SQL_QUERY)) {
            st.setString(1, movie.getOriginalTitle());
            st.setLong(2, languageId);
            st.setString(3, movie.getTranslatedTitle());
            st.setString(4, movie.getDescription());
            st.executeUpdate();
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
    }

    public void updateMovieInfo(Movie movie, long languageId) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(UPDATE_MOVIE_INFO_SQL_QUERY)) {
            st.setString(1, movie.getTranslatedTitle());
            st.setString(2, movie.getDescription());
            st.setString(3, movie.getOriginalTitle());
            st.setLong(4, languageId);
            st.executeUpdate();
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
    }

    public List<Movie> searchMovie(String searchingTitle, Long languageId) throws SQLException, IOException {
        List<Movie> movies = new ArrayList<>();
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(SEARCH_MOVIES_SQL_QUERY)) {
            st.setString(1, "%" + searchingTitle + "%");
            st.setLong(2, languageId);
            st.setString(3, "%" + searchingTitle + "%");
            st.setLong(4, languageId);
            movies = getFoundMovies(st, movies);
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return movies;
    }

    public Movie findUsersLikeRate(Movie movie, long userId) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(FIND_USERS_LIKE_RATE_SQL_QUERY)) {
            st.setLong(1, movie.getId());
            st.setLong(2, userId);
            try (ResultSet resultSet =
                         st.executeQuery()) {
                while (resultSet.next()) {
                    movie.setRate(resultSet.getInt(MOVIE_RATE_COLUMN));
                    movie.setLiked(resultSet.getBoolean(IS_LIKED_COLUMN));
                }
            }
        }
        return movie;
    }

    public void rateMovie(long movieId, long userId, int rate) throws SQLException {
        if (!hasMovieRateRecord(movieId, userId)) {
            addMovieRate(movieId, userId, rate);
        } else {
            updateMovieRate(movieId, userId, rate);
        }
    }

    public void likeMovie(long movieId, long userId) throws SQLException {
        if (!hasMovieRateRecord(movieId, userId)) {
            likeMovieFirstTime(movieId, userId);
        } else {
            likeMovieUpdate(movieId, userId);
        }
    }


    public void unlikeMovie(long movieId, long userId) throws SQLException {
        likeUnlikeMovie(movieId, userId, UNLIKE_MOVIE_SQL_QUERY);
    }

    private void addMovieRate(long movieId, long userId, int rate) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(ADD_MOVIE_RATE_SQL_QUERY)) {
            st.setInt(3, rate);
            st.setLong(2, userId);
            st.setLong(1, movieId);
            st.executeUpdate();
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
    }

    private void updateMovieRate(long movieId, long userId, int rate) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(UPDATE_MOVIE_RATE_SQL_QUERY)) {
            st.setInt(1, rate);
            st.setLong(3, userId);
            st.setLong(2, movieId);
            st.executeUpdate();
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
    }

    public List<Movie> findLikedMovies(long userId, long languageId) throws SQLException, IOException {
        List<Movie> likedMovies = new ArrayList<>();
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(FIND_LIKED_MOVIES_SQL_QUERY)) {
            st.setLong(1, languageId);
            st.setLong(2, languageId);
            st.setLong(3, userId);
            getFoundMovies(st, likedMovies);
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return likedMovies;
    }

    @Override
    public List<Movie> findAll(long languageId) throws SQLException, IOException {
        List<Movie> movies = new ArrayList<>();
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(FIND_ALL_MOVIES_SQL_QUERY)) {
            st.setLong(1, languageId);
            st.setLong(2, languageId);
            movies = getFoundMovies(st, movies);
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return movies;
    }

    private List<Movie> getFoundMovies(PreparedStatement st, List<Movie> movies) throws IOException, SQLException {
        try (ResultSet resultSet =
                     st.executeQuery()) {
            while (resultSet.next()) {
                Movie movie = new Movie();
                setMovieParameters(movie, resultSet);
                movies.add(movie);
            }
        }
        return movies;
    }

    @Override
    public Movie findEntityById(Long movieId, long languageId) throws SQLException, IOException {
        Movie movie = new Movie();
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(FIND_MOVIE_BY_ID_SQL_QUERY)) {
            st.setLong(1, movieId);
            st.setLong(2, languageId);
            st.setLong(3, movieId);
            st.setLong(4, languageId);
            st.setLong(5, movieId);
            try (ResultSet resultSet = st.executeQuery()) {
                while (resultSet.next()) {
                    setMovieParameters(movie, resultSet);
                }
            }
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return movie;
    }

    @Override
    public void delete(Long id) throws SQLException {
        delete(id, DELETE_MOVIE_BY_ID_SQL_QUERY);

    }

    @Override
    public void create(Movie entity) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(ADD_MOVIE_SQL_QUERY)) {
            setMovieParameters(entity, st);
            st.executeUpdate();
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
    }

    @Override
    public void update(Movie entity) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(UPDATE_MOVIE_SQL_QUERY)) {
            setMovieParameters(entity, st);
            st.setLong(6, entity.getId());
            st.executeUpdate();
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
    }

    private void setMovieParameters(Movie movie, ResultSet resultSet) throws SQLException, IOException {
        movie.setId(resultSet.getLong(MOVIE_ID_COLUMN));
        movie.setOriginalTitle(resultSet.getString(MOVIE_TITLE_ORIGINAL_COLUMN));
        movie.setDuration(resultSet.getTime(MOVIE_DURATION_COLUMN).toLocalTime());
        movie.setBudget(resultSet.getLong(MOVIE_BUDGET_COLUMN));
        movie.setReleaseDate(resultSet.getDate(MOVIE_RELEASE_DATE_COLUMN).toLocalDate());
        movie.setImage(resultSet.getBytes(MOVIE_IMAGE_COLUMN));
        byte[] imageByteArray = movie.getImage();
        movie.setImageBase64(new Base64().encodeToString(imageByteArray));
        movie.setRating(resultSet.getFloat(MOVIE_RATING_COLUMN));
        movie.setTranslatedTitle(resultSet.getString(MOVIE_TITLE_TRANSLATED_COLUMN));
        movie.setDescription(resultSet.getString(MOVIE_DESCRIPTION_COLUMN));
    }

    private void setMovieParameters(Movie entity, PreparedStatement st) throws SQLException {
        st.setString(1, entity.getOriginalTitle());
        st.setTime(2, Time.valueOf(entity.getDuration()));
        st.setLong(3, entity.getBudget());
        st.setDate(4, Date.valueOf(entity.getReleaseDate()));
        st.setBytes(5, entity.getImage());
    }

    public void updateMovieRating(long movieId) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(UPDATE_MOVIE_RATING_SQL_QUERY)) {
            st.setLong(1, movieId);
            st.setLong(2, movieId);
            st.executeUpdate();
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
    }

    private void likeUnlikeMovie(long movieId, long userId, String sqlQuery) throws SQLException {
        executeUpdateWithTwoId(userId, movieId, sqlQuery);
    }

    private Boolean hasMovieRateRecord(long movieId, long userId) throws SQLException {
        Boolean hasRecord;
        Connection cn = CONNECTION_POOL.takeConnection();

        try (PreparedStatement st = cn.prepareStatement(FIND_MOVIE_RATE_RECORD_SQL_QUERY)) {
            st.setLong(1, movieId);
            st.setLong(2, userId);
            try (ResultSet resultSet = st.executeQuery()) {
                resultSet.beforeFirst();
                hasRecord = resultSet.next();
            }
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return hasRecord;
    }

    private void likeMovieUpdate(long movieId, long userId) throws SQLException {
        likeUnlikeMovie(movieId, userId, LIKE_MOVIE_UPDATE_SQL_QUERY);
    }

    private void likeMovieFirstTime(long movieId, long userId) throws SQLException {
        likeUnlikeMovie(movieId, userId, LIKE_MOVIE_FIRST_TIME_SQL_QUERY);
    }
}
