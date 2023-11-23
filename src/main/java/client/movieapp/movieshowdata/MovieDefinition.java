package client.movieapp.movieshowdata;

/**
 * The type Movie definition.
 */
public class MovieDefinition {

    private String movie_title;
    private String genre_1;
    private String genre_2;
    private String movie_id;
    private String movie_poster_path;
    private String movie_desc;
    private String movie_ratings;
    private String movie_release_date;

    public String getGenre_1() {
        return genre_1;
    }

    public void setGenre_1(String genre_1) {
        this.genre_1 = genre_1;
    }

    public String getGenre_2() {
        return genre_2;
    }

    public void setGenre_2(String genre_2) {
        this.genre_2 = genre_2;
    }

    public String getMovie_ratings() {
        return movie_ratings;
    }

    public void setMovie_ratings(String movie_ratings) {
        this.movie_ratings = movie_ratings;
    }

    public String getMovie_release_date() {
        return movie_release_date;
    }

    public void setMovie_release_date(String movie_release_date) {
        this.movie_release_date = movie_release_date;
    }

    public String getMovie_youtube_url() {
        return movie_youtube_url;
    }

    public void setMovie_youtube_url(String movie_youtube_url) {
        this.movie_youtube_url = movie_youtube_url;
    }

    private String movie_youtube_url;


    /**
     * Gets movie desc.
     *
     * @return the movie desc
     */
    public String getMovie_desc() {
        return movie_desc;
    }

    /**
     * Sets movie desc.
     *
     * @param movie_desc the movie desc
     */
    public void setMovie_desc(String movie_desc) {
        this.movie_desc = movie_desc;
    }


    /**
     * Gets movie title.
     *
     * @return the movie title
     */
    public String getMovie_title() {
        return movie_title;
    }

    /**
     * Sets movie title.
     *
     * @param movie_title the movie title
     */
    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }


    /**
     * Gets movie id.
     *
     * @return the movie id
     */
    public String getMovie_id() {
        return movie_id;
    }

    /**
     * Sets movie id.
     *
     * @param movie_id the movie id
     */
    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    /**
     * Gets movie poster path.
     *
     * @return the movie poster path
     */
    public String getMovie_poster_path() {
        return movie_poster_path;
    }

    /**
     * Sets movie poster path.
     *
     * @param movie_poster_path the movie poster path
     */
    public void setMovie_poster_path(String movie_poster_path) {
        this.movie_poster_path = movie_poster_path;
    }


}
