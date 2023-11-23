package client.movieapp;

import client.movieapp.movieshowdata.ApplicationData;
import client.movieapp.movieshowdata.MovieDefinition;
import client.movieapp.movieshowdata.ShowDefinition;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Search bar algorithm.
 */
public class SearchBarAlgorithm {

    /**
     * Search algorithm movies list.
     *
     * @param referenceSearchText the reference search text
     * @param movieListToSearch   the movie list to search
     * @return the list
     */
    static List<MovieDefinition> searchAlgorithmMovies(String referenceSearchText, List<MovieDefinition> movieListToSearch) {
        List<MovieDefinition> movieObjectsToReturn = new ArrayList<>();

        for (MovieDefinition movie : movieListToSearch) {
            if (movie.getMovie_title().toLowerCase().contains(referenceSearchText.toLowerCase())) {
                movieObjectsToReturn.add(movie);
            }
        }
        return movieObjectsToReturn;
    }

    /**
     * Search algorithm shows list.
     *
     * @param referenceSearchText the reference search text
     * @param showListToSearch    the show list to search
     * @return the list
     */
    static List<ShowDefinition> searchAlgorithmShows(String referenceSearchText, List<ShowDefinition> showListToSearch) {
        List<ShowDefinition> showObjectsToReturn = new ArrayList<>();

        for (ShowDefinition show : showListToSearch) {
            if (show.getShow_title().toLowerCase().contains(referenceSearchText.toLowerCase())) {
                showObjectsToReturn.add(show);
            }
        }
        return showObjectsToReturn;
    }
}
