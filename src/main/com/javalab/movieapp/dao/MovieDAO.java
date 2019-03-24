package com.javalab.movieapp.dao;

import com.javalab.movieapp.entities.Movie;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MovieDAO implements AbstractDAO<Long, Movie> {

    public static final String MOVIE_TITLE_ORIGINAL_COLUMN = "movie_title_original";
    public static final String MOVIE_DURATION_COLUMN = "movie_duration";
    public static final String MOVIE_BUDGET_COLUMN = "movie_budget";
    public static final String MOVIE_RELEASE_DATE_COLUMN = "movie_release_date";
    public static final String MOVIE_IMAGE_COLUMN = "movie_image";
    public static final String MOVIE_DESCRIPTION_COLUMN = "movie_description";
    public static final String MOVIE_TITLE_TRANSLATED_COLUMN = "movie_title_translated";
    public static final String MOVIE_ID_COLUMN = "movie_id";
    public static final String MOVIE_RATE_COLUMN = "movie_rate";
    public static final String MOVIE_RATING_COLUMN = "movie_rating";
    public static final String IS_LIKED_COLUMN = "is_liked";

    public static final String UPDATE_MOVIE_RATING_SQL_QUERY = "UPDATE movie SET movie_rating = (SELECT FORMAT(AVG (movie_rate), 1) FROM movie_rate WHERE movie_rate.movie_id = ?) WHERE movie_id = ?;";
    public static final String LIKE_MOVIE_FIRST_TIME_SQL_QUERY = "INSERT INTO movie_rate (user_id, movie_id, is_liked) VALUES (?, ?, 1);";
    public static final String LIKE_MOVIE_UPDATE_SQL_QUERY = "UPDATE movie_rate SET is_liked = 1 WHERE user_id = ? AND movie_id = ?;";
    public static final String UNLIKE_MOVIE_SQL_QUERY = "UPDATE movie_rate SET is_liked = 0 WHERE user_id = ? AND movie_id = ?;";
    public static final String ADD_MOVIE_INFO_SQL_QUERY = "INSERT INTO movie_info (movie_id, language_id, movie_title_translated, movie_description) VALUES ((SELECT m.movie_id FROM movie m WHERE m.movie_title_original = ?), ?, ?, ?);";
    public static final String UPDATE_MOVIE_INFO_SQL_QUERY = "UPDATE movie_info SET movie_title_translated = ?, movie_description = ? WHERE movie_id = (SELECT m.movie_id FROM movie m WHERE m.movie_title_original = ?) AND language_id = ?;";
    public static final String FIND_ALL_MOVIES_SQL_QUERY = "SELECT m.movie_id, m.movie_title_original, m.movie_duration, m.movie_budget, m.movie_release_date, m.movie_image, m.movie_rating, (SELECT mi.movie_title_translated FROM movie_info mi WHERE m.movie_id = mi.movie_id AND mi.language_id = ?) AS movie_title_translated, (SELECT mi.movie_description FROM movie_info mi WHERE m.movie_id = mi.movie_id AND mi.language_id = ?) AS movie_description FROM movie m;";
    public static final String FIND_MOVIE_BY_ID_SQL_QUERY = "SELECT m.movie_id, m.movie_title_original, m.movie_duration, m.movie_budget, m.movie_release_date, m.movie_image, m.movie_rating, mi.movie_title_translated, mi.movie_description FROM movie m LEFT JOIN movie_info mi USING (movie_id) WHERE (mi.language_id = ? OR mi.language_id IS NULL) AND movie_id = ?;";
    public static final String DELETE_MOVIE_BY_ID_SQL_QUERY = "DELETE FROM movie WHERE movie_id = ?;";
    public static final String ADD_MOVIE_SQL_QUERY = "INSERT INTO movie(movie_title_original, movie_duration, movie_budget, movie_release_date, movie_image) VALUES (?, ?, ?, ?, ?);";
    public static final String UPDATE_MOVIE_SQL_QUERY = "UPDATE movie SET movie_title_original = ?, movie_duration = ?, movie_budget = ?, movie_release_date = ?, movie_image = ? WHERE movie_id = ?;";
    public static final String ADD_MOVIE_RATE_SQL_QUERY = "INSERT INTO movie_rate (movie_id, user_id, movie_rate) VALUES (?, ?, ?);";
    public static final String UPDATE_MOVIE_RATE_SQL_QUERY = "UPDATE movie_rate SET movie_rate = ? WHERE movie_id = ? AND user_id = ?;";
    public static final String FIND_LIKED_MOVIES_SQL_QUERY = "SELECT m.movie_id, m.movie_title_original, m.movie_duration, m.movie_budget, m.movie_release_date, m.movie_image, m.movie_rating, mi.movie_title_translated, mi.movie_description FROM movie m JOIN movie_info mi USING (movie_id) JOIN movie_rate mr USING (movie_id) WHERE mr.user_id = ? AND mr.is_liked = 1 AND mi.language_id = ?;";
    public static final String FIND_MOVIE_RATE_RECORD_SQL_QUERY = "SELECT user_id FROM movie_rate WHERE movie_id = ? AND user_id = ?;";
    public static final String FIND_USERS_LIKE_RATE_SQL_QUERY = "SELECT movie_rate, is_liked FROM movie_rate WHERE movie_id = ? AND user_id = ?";
    public static final String SEARCH_MOVIES_SQL_QUERY = "SELECT m.movie_id, m.movie_title_original, m.movie_duration, m.movie_budget, m.movie_release_date, m.movie_image, m.movie_rating, mi.movie_title_translated, mi.movie_description FROM movie m JOIN movie_info mi USING (movie_id) WHERE (m.movie_title_original LIKE ? AND mi.language_id = ?) OR (mi.movie_title_translated LIKE ? AND mi.language_id = ?);";

    private final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();


    public boolean addMovieInfo(Movie movie, long languageId) throws SQLException {
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
        return true;
    }

    public boolean updateMovieInfo(Movie movie, long languageId) throws SQLException {
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
        return true;
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

    public boolean rateMovie(long movieId, long userId, int rate) throws SQLException {
        boolean isSuccessful;
        if (!hasMovieRateRecord(movieId, userId)) {
            isSuccessful = addMovieRate(movieId, userId, rate);
        } else {
            isSuccessful = updateMovieRate(movieId, userId, rate);
        }
        return isSuccessful;
    }

    public boolean likeMovie(long movieId, long userId) throws SQLException {
        boolean isSuccessful;
        if (!hasMovieRateRecord(movieId, userId)) {
            isSuccessful = likeMovieFirstTime(movieId, userId);
        } else {
            isSuccessful = likeMovieUpdate(movieId, userId);
        }
        return isSuccessful;
    }


    public boolean unlikeMovie(long movieId, long userId) throws SQLException {
        boolean isUnliked = likeUnlikeMovie(movieId, userId, UNLIKE_MOVIE_SQL_QUERY);
        return isUnliked;
    }

    public boolean addMovieRate(long movieId, long userId, int rate) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(ADD_MOVIE_RATE_SQL_QUERY)) {
            st.setInt(3, rate);
            st.setLong(2, userId);
            st.setLong(1, movieId);
            st.executeUpdate();
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return true;
    }

    public boolean updateMovieRate(long movieId, long userId, int rate) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(UPDATE_MOVIE_RATE_SQL_QUERY)) {
            st.setInt(1, rate);
            st.setLong(3, userId);
            st.setLong(2, movieId);
            st.executeUpdate();
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return true;
    }

    public List<Movie> findLikedMovies(long userId, long languageId) throws SQLException, IOException {
        List<Movie> likedMovies = new ArrayList<>();
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(FIND_LIKED_MOVIES_SQL_QUERY)) {
            st.setLong(1, userId);
            st.setLong(2, languageId);
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
            st.setLong(1, languageId);
            st.setLong(2, movieId);
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
    public boolean delete(Long id) throws SQLException {
        boolean isDeleted = delete(id, DELETE_MOVIE_BY_ID_SQL_QUERY);
        return isDeleted;
    }

    @Override
    public boolean create(Movie entity) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(ADD_MOVIE_SQL_QUERY)) {
            setMovieParameters(entity, st);
            st.executeUpdate();
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return true;
    }

    @Override
    public boolean update(Movie entity) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(UPDATE_MOVIE_SQL_QUERY)) {
            setMovieParameters(entity, st);
            st.setLong(6, entity.getId());
            st.executeUpdate();
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return true;
    }

    private void setMovieParameters(Movie movie, ResultSet resultSet) throws SQLException, IOException {
        movie.setId(resultSet.getLong(MOVIE_ID_COLUMN));
        movie.setOriginalTitle(resultSet.getString(MOVIE_TITLE_ORIGINAL_COLUMN));
        movie.setDuration(resultSet.getTime(MOVIE_DURATION_COLUMN).toLocalTime());
        movie.setBudget(resultSet.getLong(MOVIE_BUDGET_COLUMN));
        movie.setReleaseDate(resultSet.getDate(MOVIE_RELEASE_DATE_COLUMN).toLocalDate());
        movie.setImage(resultSet.getBinaryStream(MOVIE_IMAGE_COLUMN));
        byte[] imageByteArray = IOUtils.toByteArray(movie.getImage());
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
        st.setBinaryStream(5, entity.getImage());
    }

    public boolean updateMovieRating(long movieId) throws SQLException {
        Connection cn = CONNECTION_POOL.takeConnection();
        try (PreparedStatement st = cn.prepareStatement(UPDATE_MOVIE_RATING_SQL_QUERY)) {
            st.setLong(1, movieId);
            st.setLong(2, movieId);
            st.executeUpdate();
        } finally {
            CONNECTION_POOL.releaseConnection(cn);
        }
        return true;
    }

    private boolean likeUnlikeMovie(long movieId, long userId, String sqlQuery) throws SQLException {
        return executeUpdateWithTwoId(userId, movieId, sqlQuery);
    }

    public Boolean hasMovieRateRecord(long movieId, long userId) throws SQLException {
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

    private boolean likeMovieUpdate(long movieId, long userId) throws SQLException {
        boolean isLiked = likeUnlikeMovie(movieId, userId, LIKE_MOVIE_UPDATE_SQL_QUERY);
        return isLiked;
    }

    private boolean likeMovieFirstTime(long movieId, long userId) throws SQLException {
        boolean isLiked = likeUnlikeMovie(movieId, userId, LIKE_MOVIE_FIRST_TIME_SQL_QUERY);
        return isLiked;
    }
}
