package client.movieapp.movieshowdata;

public class BridgeControllerInstance {

    private static final BridgeControllerInstance instance = new BridgeControllerInstance();
    private String movieName;
    private String movieTrailerPath;
    private String moviePoster;
    private String genre1;
    private String genre2;
    private String movieDescriptions;
    private String movieRating;
    private String movieReleaseDate;

    public String getMoviePoster() {

        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getGenre1() {
        return genre1;
    }

    public void setGenre1(String genre1) {
        this.genre1 = genre1;
    }

    public String getGenre2() {
        return genre2;
    }

    public void setGenre2(String genre2) {
        this.genre2 = genre2;
    }

    public String getMovieDescriptions() {
        return movieDescriptions;
    }

    public void setMovieDescriptions(String movieDescriptions) {
        this.movieDescriptions = movieDescriptions;
    }

    public String getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public String getMovieTrailerPath() {
        return movieTrailerPath;
    }

    public void setMovieTrailerPath(String movieTrailerPath) {
        this.movieTrailerPath = movieTrailerPath;
    }

    private BridgeControllerInstance() {
    }


    public static BridgeControllerInstance getInstance() {
        return instance;
    }


    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
}
