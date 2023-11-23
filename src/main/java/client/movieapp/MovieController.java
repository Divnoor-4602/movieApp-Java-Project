package client.movieapp;

import client.movieapp.movieshowdata.ApplicationData;
import client.movieapp.movieshowdata.BridgeControllerInstance;
import client.movieapp.movieshowdata.MovieDefinition;
import client.movieapp.movieshowdata.ShowDefinition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The type Movie controller.
 */
public class MovieController implements Initializable {

    /**
     * The Main parent.
     */
    public AnchorPane mainParent;
    /**
     * The Genre view.
     */
    public ListView<String> genreView;
    /**
     * The Genre total.
     */
    String[] genreTotal = {"Action", "Adventure", "Animation", "Comedy", "Crime", "Documentary", "Drama", "Family", "Fantasy", "History", "Horror", "Music", "Mystery", "Romance", "Science", "Fiction", "TV", "Movie", "Thriller", "War", "Western"};
    /**
     * The Data.
     */
    BridgeControllerInstance data = BridgeControllerInstance.getInstance();
    /**
     * The Poster image view.
     */
// used to check the times API is being called
    ImageView posterImageView;
    ObservableList<String> currentGenre;
    /**
     * The Switch count.
     */
    static Integer switchCount = 0;
    private Parent root;
    private Scene scene;
    private Stage stage;
    /**
     * The App data object.
     */
    ApplicationData appDataObject = new ApplicationData();

    /**
     * The Movies to render.
     */
    static List<MovieDefinition> moviesToRender;

    /**
     * The Shows to render.
     */
    static List<ShowDefinition> showsToRender;

    /**
     * The Current page.
     */
    String currentPage = "Movies";
    /**
     * The Movie detail box.
     */
    VBox[] movieDetailBox = new VBox[40];

    @FXML
    private HBox movieBox = new HBox();
    @FXML
    private ScrollPane moviePane;
    @FXML
    private Button search;
    @FXML
    private TextField searchBox;
    @FXML
    private Button movieButton;
    @FXML
    private Button showButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // getting all the genres and setting up the genre sidebar
        // creating an application object instance to obtain the movie data

        // Define a vertical box with count 20 to render all the 20 movies returned by the API


        // never show vertical scroll bar for movie pane
        moviePane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        //  sidebar
        genreView.getItems().addAll(genreTotal);
        //  Getting the genre and filtering the movies or shows list
        genreView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            genreView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            currentGenre = genreView.getSelectionModel().getSelectedItems();
            filterByGenre(currentGenre);
        });
        // getting all the movies and assigning them to movies to render
        if (switchCount == 0) {
            try {
                moviesToRender = appDataObject.getRealTimeMovies();
                showsToRender = appDataObject.getRealTimeShows();
            } catch (URISyntaxException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        setMoviesInHbox(moviesToRender);
        // increasing the switch count so that api is not called again and again;
        switchCount++;
    }

    /**
     * Handle switch scenes movies.
     *
     * @param movieToSend the movie to send
     * @param event       the event
     * @throws IOException the io exception
     */
    void handleSwitchScenesMovies(MovieDefinition movieToSend, ActionEvent event) throws IOException {
        // setting the properties of bridge controller to retrieve them in other scene
        data.setMovieName(movieToSend.getMovie_title());
        data.setGenre1(movieToSend.getGenre_1());
        data.setGenre2(movieToSend.getGenre_2());
        data.setMoviePoster(movieToSend.getMovie_poster_path());
        data.setMovieRating(movieToSend.getMovie_ratings());
        data.setMovieDescriptions(movieToSend.getMovie_desc());
        data.setMovieReleaseDate(movieToSend.getMovie_release_date());
        data.setMovieTrailerPath(movieToSend.getMovie_youtube_url());
        //  getting the stage of the event to put the new scene in it
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //  get the second scene's fxml file and load it into the root
        FXMLLoader loader = new FXMLLoader(getClass().getResource("movieSceneIndividual.fxml"));
        root = loader.load();
        //   setting the scene with new fxml
        String css = Objects.requireNonNull(this.getClass().getResource("appHome.css")).toExternalForm();
        // add the stylesheet to the scene
        scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
    }

    /**
     * Handle switch scene shows.
     *
     * @param showToSend the show to send
     * @param event      the event
     * @throws IOException the io exception
     */
    void handleSwitchSceneShows(ShowDefinition showToSend, ActionEvent event) throws IOException {
        // setting the properties of bridge controller to retrieve them in other scene
        data.setMovieName(showToSend.getShow_title());
        data.setGenre1(showToSend.getGenre_1());
        data.setGenre2(showToSend.getGenre_2());
        data.setMoviePoster(showToSend.getShow_poster_path());
        data.setMovieRating(showToSend.getShow_ratings());
        data.setMovieDescriptions(showToSend.getShow_desc());
        data.setMovieReleaseDate(showToSend.getRelease_date());
        data.setMovieTrailerPath(showToSend.getShow_youtube_url());
        //  getting the stage of the event to put the new scene in it
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //  get the second scene's fxml file and load it into the root
        FXMLLoader loader = new FXMLLoader(getClass().getResource("movieSceneIndividual.fxml"));
        root = loader.load();
        //   setting the scene with new fxml
        String css = Objects.requireNonNull(this.getClass().getResource("appHome.css")).toExternalForm();
        // add the stylesheet to the scene
        scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
    }

    /**
     * Label formatter string.
     *
     * @param title the title
     * @return the string
     */
    String labelFormatter(String title) {

        // formats the number of lines shown in the title label of the movies
        if (title.split(" ").length > 5) {
            String[] titleArray = title.split(" ");
            String[] finalArray = new String[5];
            for (int i = 0; i < titleArray.length; i++) {
                if (i < 5) {
                    finalArray[i] = titleArray[i];
                }
            }
            return title.substring(0, String.join("", finalArray).length()) + "...";
        } else {
            return title;
        }
    }

    /**
     * Sets movies in hbox.
     *
     * @param movies the movies
     */
    void setMoviesInHbox(List<MovieDefinition> movies) {
        for (int i = 0; i < movies.size(); i++) {
            // movie object
            MovieDefinition movieToPass = movies.get(i);
            // Setting up hyperlink as the movie titles
            Hyperlink movieTitle = new Hyperlink(labelFormatter(movies.get(i).getMovie_title()));

            // Adding an action listener on the title to switch movies to the individual scenes and sending the data
            // to the function defined to handle switch scenes
            movieTitle.setOnAction(event -> {
                try {
                    handleSwitchScenesMovies(movieToPass, event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            // url to retrieve the posters for the movies after getting the paths from API call
            String posterPathUrl = "https://image.tmdb.org/t/p/original";
            // getting the poster path to render on the page
            Image posterMovie = new Image(posterPathUrl + movies.get(i).getMovie_poster_path(), 1920, 1080, true, false);
            posterImageView = new ImageView(posterMovie);
            posterImageView.setFitHeight(187);
            posterImageView.setFitWidth(121);
            // Adding each movie as a VBox and adding poster and title to the vbox
            movieDetailBox[i] = new VBox();
            //  setting alignment
            movieDetailBox[i].setAlignment(javafx.geometry.Pos.CENTER);
            movieBox.setAlignment(javafx.geometry.Pos.CENTER);
            // giving id to the movie box
            movieBox.setId("movieBoxD");
            //  adding elements to the movie box
            movieDetailBox[i].getChildren().add(posterImageView);
            movieDetailBox[i].getChildren().add(movieTitle);
            //  render the genres which are available
            // System.out.println(movieToPass.getGenre_1() + " " + movieToPass.getGenre_2());
            Label genres = new Label(movies.get(i).getGenre_1() + " " + (movies.get(i).getGenre_2() == null ? "" : "| " + movies.get(i).getGenre_2()));


            // adding genre and their style class
            genres.getStyleClass().add("genres");
            // Setting id to vbox to apply css to it
            movieDetailBox[i].setId("movieDetailsV");
            // Add the movie vbox to horizontal scroll pane
            movieDetailBox[i].getStyleClass().add("anchor-pane");
            movieButton.getStyleClass().remove("light");
            movieButton.getStyleClass().add("primary");
            showButton.getStyleClass().removeAll("primary", "corner");
            showButton.getStyleClass().add("light");
            mainParent.getStyleClass().add("anchor-pane");
            moviePane.getStyleClass().add("anchor-pane");
            movieBox.getStyleClass().add("anchor-pane");
            search.getStyleClass().add("success");
            movieDetailBox[i].getChildren().add(genres);
            movieBox.getChildren().add(movieDetailBox[i]);
        }
    }

    /**
     * Sets shows in h box.
     *
     * @param shows the shows
     */
    void setShowsInHBox(List<ShowDefinition> shows) {
        for (int i = 0; i < shows.size(); i++) {
            // movie object
            ShowDefinition showToPass = shows.get(i);
            // Setting up hyperlink as the movie titles
            Hyperlink showTitle = new Hyperlink(labelFormatter(shows.get(i).getShow_title()));

            // Adding an action listener on the title to switch movies to the individual scenes and sending the data
            // to the function defined to handle switch scenes
            showTitle.setOnAction(event -> {
                try {
                    handleSwitchSceneShows(showToPass, event);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            // url to retrieve the posters for the movies after getting the paths from API call
            String posterPathUrl = "https://image.tmdb.org/t/p/original";
            // getting the poster path to render on the page
            Image posterMovie = new Image(posterPathUrl + shows.get(i).getShow_poster_path(), 1920, 1080, true, false);
            posterImageView = new ImageView(posterMovie);
            posterImageView.setFitHeight(187);
            posterImageView.setFitWidth(121);
            // Adding each movie as a VBox and adding poster and title to the vbox
            movieDetailBox[i] = new VBox();
            //  setting alignment
            movieDetailBox[i].setAlignment(javafx.geometry.Pos.CENTER);
            movieBox.setAlignment(javafx.geometry.Pos.CENTER);
            // giving id to the movie box
            movieBox.setId("movieBoxD");
            //  adding elements to the movie box
            movieDetailBox[i].getChildren().add(posterImageView);
            movieDetailBox[i].getChildren().add(showTitle);
            //  render the genres which are available
            // System.out.println(movieToPass.getGenre_1() + " " + movieToPass.getGenre_2());
            Label genres = new Label(shows.get(i).getGenre_2() + " " + (shows.get(i).getGenre_1() == null ? "" : "| " + shows.get(i).getGenre_1()));


            // adding genre and their style class
            genres.getStyleClass().add("genres");
            // Setting id to vbox to apply css to it
            movieDetailBox[i].setId("movieDetailsV");
            // Add the movie vbox to horizontal scroll pane
            movieDetailBox[i].getStyleClass().add("anchor-pane");
            movieButton.getStyleClass().remove("light");
            movieButton.getStyleClass().add("primary");
            showButton.getStyleClass().removeAll("primary", "corner");
            showButton.getStyleClass().add("light");
            mainParent.getStyleClass().add("anchor-pane");
            moviePane.getStyleClass().add("anchor-pane");
            movieBox.getStyleClass().add("anchor-pane");
            search.getStyleClass().add("success");
            movieDetailBox[i].getChildren().add(genres);
            movieBox.getChildren().add(movieDetailBox[i]);
        }
    }

    /**
     * Search.
     *
     * @param event the event
     */
    @FXML
    void search(ActionEvent event) {
        // get the search query
        String searchQuery = searchBox.getText().toLowerCase();
        // if the search query is an empty string render all the movies else render the search results
        if (!searchQuery.isEmpty()) {
            //    Empty the hbox to re-render the search results
            movieBox.getChildren().removeAll(movieBox.getChildren());
            if (currentPage.equals("Shows")) {
                List<ShowDefinition> searchedShows = SearchBarAlgorithm.searchAlgorithmShows(searchQuery, showsToRender);
                // setting the searched movies in the hbox
                setShowsInHBox(searchedShows);
            } else {
                //   fetching the movies that fit the search by using a search function
                List<MovieDefinition> searchedMovies = SearchBarAlgorithm.searchAlgorithmMovies(searchQuery, moviesToRender);
                // setting the searched movies in the hbox
                setMoviesInHbox(searchedMovies);
            }
        } else {
            //    Empty the hbox to re-render the search results
            movieBox.getChildren().removeAll(movieBox.getChildren());
            setMoviesInHbox(moviesToRender);
        }
    }


    /**
     * Show shows.
     *
     * @param event the event
     */
    public void showShows(ActionEvent event) {
        movieBox.getChildren().removeAll(movieBox.getChildren());
        currentPage = "Shows";
        // getting all the genres and setting up the genre sidebar
        // never show vertical scroll bar for movie pane
        moviePane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        // getting all the movies and assigning them to movies to render
        movieButton.getStyleClass().removeAll("light", "primary", "corner");
        movieButton.getStyleClass().add("light");
        movieButton.getStyleClass().removeAll("light", "primary", "corner");
        showButton.getStyleClass().add("primary");
        mainParent.getStyleClass().add("anchor-pane");
        moviePane.getStyleClass().add("anchor-pane");
        movieBox.getStyleClass().add("anchor-pane");
        search.getStyleClass().add("success");
        setShowsInHBox(showsToRender);
        // increasing the switch count so that api is not called again and again;
        switchCount++;


    }

    /**
     * Show movies.
     *
     * @param event the event
     */
    public void showMovies(ActionEvent event) {
        movieBox.getChildren().removeAll(movieBox.getChildren());
        currentPage = "Movies";
        // getting all the genres and setting up the genre sidebar
        // never show vertical scroll bar for movie pane
        moviePane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        // getting all the movies and assigning them to movies to render
        movieButton.getStyleClass().removeAll("light", "primary", "corner");
        movieButton.getStyleClass().add("primary");
        movieButton.getStyleClass().removeAll("light", "primary", "corner");
        showButton.getStyleClass().add("light");
        mainParent.getStyleClass().add("anchor-pane");
        moviePane.getStyleClass().add("anchor-pane");
        movieBox.getStyleClass().add("anchor-pane");
        search.getStyleClass().add("success");
        setMoviesInHbox(moviesToRender);
        // increasing the switch count so that api is not called again and again;
        switchCount++;
    }

    void filterByGenre(ObservableList<String> genres) {
        int count = 0;
        //  remove everything and re render
        movieBox.getChildren().removeAll(movieBox.getChildren());
        List<MovieDefinition> filteredMovie = new ArrayList<>();
        List<ShowDefinition> filteredShow = new ArrayList<>();
        if (currentPage.equals("Movies")) {
            for (String genre :
                    genres
            ) {
                for (MovieDefinition movie :
                        moviesToRender) {
                    if (!filteredMovie.contains(movie)) {
                        if (movie.getGenre_1().equals(genre)) {
                            System.out.println("comparing genre " + genre + ", movie: " + movie.getGenre_1());
                            filteredMovie.add(movie);
                            count += 1;
                        } else if ((movie.getGenre_2() != null) && movie.getGenre_2().equals(genre)) {
                            filteredMovie.add(movie);

                            count += 1;
                        }
                    }
                }
            }
            // pass to movies render box
            setMoviesInHbox(filteredMovie);
        } else if (currentPage.equals("Shows")) {
            for (String genre :
                    genres
            ) {
                for (ShowDefinition show :
                        showsToRender) {
                    if (!filteredShow.contains(show)) {
                        if (show.getGenre_1().equals(genre)) {
                            filteredShow.add(show);
                        } else if (show.getGenre_2() != null && show.getGenre_2().equals(genre)) {
                            filteredShow.add(show);
                        }
                    }
                }
            }
            //     pass to the render shows function
            setShowsInHBox(filteredShow);

        }
        System.out.println(count);


    }
}



