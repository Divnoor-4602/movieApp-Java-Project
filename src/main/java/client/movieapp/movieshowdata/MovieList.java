package client.movieapp.movieshowdata;

import java.util.List;

import client.movieapp.movieshowdata.MovieDefinition;
import org.bson.codecs.pojo.annotations.BsonProperty;

/**
 * The type Movie list.
 */
public class MovieList {

    @BsonProperty("movie list")
    private List<MovieDefinition> movies;

    /**
     * Gets movies.
     *
     * @return the movies
     */
    public List<MovieDefinition> getMovies() {
        return movies;
    }

    /**
     * Sets movies.
     *
     * @param movies the movies
     */
    public void setMovies(List<MovieDefinition> movies) {
        this.movies = movies;
    }


}
