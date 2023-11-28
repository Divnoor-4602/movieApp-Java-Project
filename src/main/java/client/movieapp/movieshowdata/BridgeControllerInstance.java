package client.movieapp.movieshowdata;

/**
 * The type Bridge controller instance.
 */
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
    private String currentUser;
    private String currentUserEmail;
    private String currentAppPage;

    /**
     * Gets shows to send.
     *
     * @return the shows to send
     */
    public ShowDefinition getShowsToSend() {
        return showsToSend;
    }

    /**
     * Sets shows to send.
     *
     * @param showsToSend the shows to send
     */
    public void setShowsToSend(ShowDefinition showsToSend) {
        this.showsToSend = showsToSend;
    }

    private ShowDefinition showsToSend;

    /**
     * Gets movie to send.
     *
     * @return the movie to send
     */
    public MovieDefinition getMovieToSend() {
        return movieToSend;
    }

    /**
     * Sets movie to send.
     *
     * @param movieToSend the movie to send
     */
    public void setMovieToSend(MovieDefinition movieToSend) {
        this.movieToSend = movieToSend;
    }

    private MovieDefinition movieToSend;

    /**
     * Gets movie poster.
     *
     * @return the movie poster
     */
    public String getMoviePoster() {

        return moviePoster;
    }

    /**
     * Sets movie poster.
     *
     * @param moviePoster the movie poster
     */
    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    /**
     * Gets genre 1.
     *
     * @return the genre 1
     */
    public String getGenre1() {
        return genre1;
    }

    /**
     * Sets genre 1.
     *
     * @param genre1 the genre 1
     */
    public void setGenre1(String genre1) {
        this.genre1 = genre1;
    }

    /**
     * Gets genre 2.
     *
     * @return the genre 2
     */
    public String getGenre2() {
        return genre2;
    }

    /**
     * Sets genre 2.
     *
     * @param genre2 the genre 2
     */
    public void setGenre2(String genre2) {
        this.genre2 = genre2;
    }

    /**
     * Gets movie descriptions.
     *
     * @return the movie descriptions
     */
    public String getMovieDescriptions() {
        return movieDescriptions;
    }

    /**
     * Sets movie descriptions.
     *
     * @param movieDescriptions the movie descriptions
     */
    public void setMovieDescriptions(String movieDescriptions) {
        this.movieDescriptions = movieDescriptions;
    }

    /**
     * Gets movie rating.
     *
     * @return the movie rating
     */
    public String getMovieRating() {
        return movieRating;
    }

    /**
     * Sets movie rating.
     *
     * @param movieRating the movie rating
     */
    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }

    /**
     * Gets movie release date.
     *
     * @return the movie release date
     */
    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    /**
     * Sets movie release date.
     *
     * @param movieReleaseDate the movie release date
     */
    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    /**
     * Gets movie trailer path.
     *
     * @return the movie trailer path
     */
    public String getMovieTrailerPath() {
        return movieTrailerPath;
    }

    /**
     * Sets movie trailer path.
     *
     * @param movieTrailerPath the movie trailer path
     */
    public void setMovieTrailerPath(String movieTrailerPath) {
        this.movieTrailerPath = movieTrailerPath;
    }

    private BridgeControllerInstance() {
    }


    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static BridgeControllerInstance getInstance() {
        return instance;
    }


    /**
     * Gets movie name.
     *
     * @return the movie name
     */
    public String getMovieName() {
        return movieName;
    }

    /**
     * Sets movie name.
     *
     * @param movieName the movie name
     */
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    /**
     * Gets current user.
     *
     * @return the current user
     */
    public String getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets current user.
     *
     * @param currentUser the current user
     */
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Gets current user email.
     *
     * @return the current user email
     */
    public String getCurrentUserEmail() {
        return currentUserEmail;
    }

    /**
     * Sets current user email.
     *
     * @param currentUserEmail the current user email
     */
    public void setCurrentUserEmail(String currentUserEmail) {
        this.currentUserEmail = currentUserEmail;
    }

    /**
     * Gets current app page.
     *
     * @return the current app page
     */
    public String getCurrentAppPage() {
        return currentAppPage;
    }

    /**
     * Sets current app page.
     *
     * @param currentAppPage the current app page
     */
    public void setCurrentAppPage(String currentAppPage) {
        this.currentAppPage = currentAppPage;
    }
}
