package com.javalab.movieapp.action;

import com.javalab.movieapp.action.admin.*;
import com.javalab.movieapp.action.general.*;
import com.javalab.movieapp.action.user.*;

import java.util.HashMap;
import java.util.Map;

public class ActionFactory {

    static Map<String, Action> actions = new HashMap<>();

    {
        actions.put("/login", new LogInAction());
        actions.put("/logout", new LogOutAction());
        actions.put("/register", new RegisterAction());
        actions.put("/listMovie", new ListMovieAction());
        actions.put("/listPerson", new ListPersonAction());
        actions.put("/listGenre", new ListGenreAction());
        actions.put("/listUser", new ListUserAction());
        actions.put("/addMovie", new AddUpdateMovieAction());
        actions.put("/updateMovie", new AddUpdateMovieAction());
        actions.put("/deleteMovie", new DeleteMovieAction());
        actions.put("/listLanguage", new ListLanguageAction());
        actions.put("/changeLocale", new ChangeLocaleAction());
        actions.put("/updateUser", new AddUpdateUserAction());
        actions.put("/addUser", new AddUpdateUserAction());
        actions.put("/deleteUser", new DeleteUserAction());
        actions.put("/updateGenre", new AddUpdateGenreAction());
        actions.put("/addGenre", new AddUpdateGenreAction());
        actions.put("/addGenreLocale", new AddGenreLocaleAction());
        actions.put("/addLanguage", new AddUpdateLanguageAction());
        actions.put("/updateLanguage", new AddUpdateLanguageAction());
        actions.put("/deleteGenre", new DeleteGenreAction());
        actions.put("/deleteLanguage", new DeleteLanguageAction());
        actions.put("/updatePass", new UpdatePasswordAction());
        actions.put("/updateUserInfo", new UpdateUserInfoAction());
        actions.put("/movieInfo", new ShowMovieInfoAction());
        actions.put("/addMovieLocal", new AddUpdateMovieLocalAction());
        actions.put("/addMovieGenre", new AddMovieGenreAction());
        actions.put("/deleteMovieGenre", new DeleteMovieGenreAction());
        actions.put("/updateMovieLocal", new AddUpdateMovieLocalAction());
        actions.put("/addPerson", new AddUpdatePersonAction());
        actions.put("/deletePerson", new DeletePersonAction());
        actions.put("/updatePerson", new AddUpdatePersonAction());
        actions.put("/addPersonLocal", new AddUpdatePersonLocalAction());
        actions.put("/updatePersonLocal", new AddUpdatePersonLocalAction());
        actions.put("/movieCrewInfo", new ShowMovieCrewAction());
        actions.put("/deleteCrewMember", new DeleteCrewMemberAction());
        actions.put("/addCrewMember", new AddCrewMemberAction());
        actions.put("/likeMovie", new LikeMovieAction());
        actions.put("/unlikeMovie", new UnlikeMovieAction());
        actions.put("/rateMovie", new RateMovieAction());
        actions.put("/search", new SearchMovieAction());
        actions.put("/listFavorite", new ListLikedMovies());
        actions.put("/authorize", new LoadAuthorizationPageAction());
    }

    public Action getAction(String answerName) {

        Action action = actions.get(answerName);
        return action;
    }
}
