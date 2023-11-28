package client.movieapp;

import client.movieapp.movieshowdata.ApplicationData;
import client.movieapp.movieshowdata.BridgeControllerInstance;
import client.movieapp.movieshowdata.MovieDefinition;
import client.movieapp.movieshowdata.ShowDefinition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static client.movieapp.MongoDatabaseControl.*;

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
     * The User name label.
     */
    @FXML
    public Label userNameLabel;
    /**
     * The Logout button.
     */
    @FXML
    public Button logoutButton;
    /**
     * The Genre title.
     */
    @FXML
    public Label genreTitle;
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
    /**
     * The Current genre.
     */
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
     * The Current searched filter movies.
     */
    static List<MovieDefinition> currentSearchedFilterMovies;

    /**
     * The Shows to render.
     */
    static List<ShowDefinition> showsToRender;

    /**
     * The Current searched filter shows.
     */
    static List<ShowDefinition> currentSearchedFilterShows;

    /**
     * The My movie list.
     */
    static List<MovieDefinition> myMovieList = new ArrayList<>();
    /**
     * The My show list.
     */
    static List<ShowDefinition> myShowList = new ArrayList<>();

    /**
     * The Current page.
     */
    static String currentPage = "Movies";
    /**
     * The Movie detail box.
     */
    VBox[] movieDetailBox = new VBox[40];
    /**
     * The My movie detail box.
     */
    VBox[] myMovieDetailBox = new VBox[40];

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
    @FXML
    private HBox myMovieBox = new HBox();
    @FXML
    private ScrollPane myMoviePane;
    @FXML
    private Label currentLabel;
    @FXML
    private Label myLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // getting all the genres and setting up the genre sidebar
        // creating an application object instance to obtain the movie data

        // Define a vertical box with count 20 to render all the 20 movies returned by the API


        // never show vertical scroll bar for movie pane
        moviePane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        userNameLabel.setText(data.getCurrentUser());
        //  sidebar
        genreView.getItems().addAll(genreTotal);
        genreView.getStyleClass().add("list-cell");
        //  Getting the genre and filtering the movies or shows list
        genreView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            genreView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            currentGenre = genreView.getSelectionModel().getSelectedItems();
            filterByGenre(currentGenre);
        });
        // getting all the movies and assigning them to movies to render
        System.out.println(switchCount);
        if (switchCount == 0) {
            try {
                moviesToRender = appDataObject.getRealTimeMovies();
                currentSearchedFilterMovies = moviesToRender;
                showsToRender = appDataObject.getRealTimeShows();
                currentSearchedFilterShows = showsToRender;
            } catch (URISyntaxException | IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        movieButton.getStyleClass().add("primary");
        myMovieBox.getChildren().removeAll(myMovieBox.getChildren());

        myMovieList = showPersonalMovies();
        myShowList = showPersonalShows();
        if (currentPage.equals("Movies")) {
            currentLabel.setText("Current Movies");
            myLabel.setText("My Movies");
            setMoviesInHbox(moviesToRender);
            setMyMovies(myMovieList);
        } else {
            currentLabel.setText("Current Shows");
            myLabel.setText("My Shows");
            setShowsInHBox(showsToRender);
            setMyShows(myShowList);
        }
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
        data.setMovieToSend(movieToSend);
        data.setCurrentAppPage("Movies");
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
        data.setShowsToSend(showToSend);
        data.setMovieName(showToSend.getShow_title());
        data.setCurrentAppPage("Shows");
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
     * Genre labels generator string.
     *
     * @param genreO the genre o
     * @param genreT the genre t
     * @return the string
     */
    String genreLabelsGenerator(String genreO, String genreT) {
        if (genreO != null && genreT != null) {
            return genreO + " | " + genreT;
        } else if (genreO != null) {
            return genreO;
        } else return Objects.requireNonNullElse(genreT, "");
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

            mainParent.getStyleClass().add("anchor-pane");
            moviePane.getStyleClass().add("anchor-pane");
            movieBox.getStyleClass().add("anchor-pane");
            search.getStyleClass().add("success");
            movieDetailBox[i].getChildren().add(genres);
            movieBox.getChildren().add(movieDetailBox[i]);
//            setMyMovies(myMovieList);
        }
    }

    /**
     * Sets my movies.
     *
     * @param movies the movies
     */
    void setMyMovies(List<MovieDefinition> movies) {
        System.out.println(movies.size());
        if (movies.isEmpty()) {
            Label noMovies = new Label("No Movies Added Yet");
            noMovies.setFont(new Font("Impact", 77));
            noMovies.setPrefHeight(230);
//            noMovies.setStyle("-fx-background-color: transparent;");
            myMovieBox.getChildren().add(noMovies);
        } else {
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
                myMovieDetailBox[i] = new VBox();
                //  setting alignment
                myMovieDetailBox[i].setAlignment(javafx.geometry.Pos.CENTER);
                myMovieBox.setAlignment(javafx.geometry.Pos.CENTER);
                // giving id to the movie box
                myMovieBox.setId("myMovieBoxD");
                //  adding elements to the movie box
                myMovieDetailBox[i].getChildren().add(posterImageView);
                myMovieDetailBox[i].getChildren().add(movieTitle);
                //  render the genres which are available
                // System.out.println(movieToPass.getGenre_1() + " " + movieToPass.getGenre_2());
                Label genres = new Label(movies.get(i).getGenre_1() + " " + (movies.get(i).getGenre_2() == null ? "" : "| " + movies.get(i).getGenre_2()));
                // creating the remove button
//                ImageView cross = new ImageView(new Image(new File("src/main/java/client/movieapp/img.png").toURI().toString()));
                Button remove = new Button("❌");
                remove.setStyle("-fx-background-color: #121212;" + "-fx-border-color: #121212;");
                remove.setCursor(Cursor.HAND);
//                remove.setGraphic(cross);
                final int f = i;

                remove.setOnAction(event -> {
                    myMovieList.remove(movies.get(f));
                    myMovieBox.getChildren().removeAll(myMovieBox.getChildren());
                    removeMovies(movieToPass);
                    setMyMovies(myMovieList);
                });

                // adding genre and their style class
                genres.getStyleClass().add("genres");
                // Setting id to vbox to apply css to it
                myMovieDetailBox[i].setId("myMovieDetailsV");
                // Add the movie vbox to horizontal scroll pane
                myMovieDetailBox[i].getStyleClass().add("anchor-pane");

                mainParent.getStyleClass().add("anchor-pane");
                myMoviePane.getStyleClass().add("anchor-pane");
                myMovieBox.getStyleClass().add("anchor-pane");
                search.getStyleClass().add("success");
                remove.getStyleClass().add("button-remove");
                myMovieDetailBox[i].getChildren().add(genres);
                myMovieDetailBox[i].getChildren().add(remove);
                myMovieBox.getChildren().add(myMovieDetailBox[i]);

            }

        }
        switchCount++;
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
            Label genres = new Label(genreLabelsGenerator(showToPass.getGenre_1(), showToPass.getGenre_2()));
            // adding genre and their style class
            genres.getStyleClass().add("genres");
            // Setting id to vbox to apply css to it
            movieDetailBox[i].setId("movieDetailsV");
            // Add the movie vbox to horizontal scroll pane
            movieDetailBox[i].getStyleClass().add("anchor-pane");

            mainParent.getStyleClass().add("anchor-pane");
            moviePane.getStyleClass().add("anchor-pane");
            movieBox.getStyleClass().add("anchor-pane");
            search.getStyleClass().add("success");
            movieDetailBox[i].getChildren().add(genres);
            movieBox.getChildren().add(movieDetailBox[i]);
//            setMyShows(myShowList);
        }
        switchCount++;
    }

    /**
     * Sets my shows.
     *
     * @param shows the shows
     */
    void setMyShows(List<ShowDefinition> shows) {
        if (shows.isEmpty()) {
            Label noMovies = new Label("No Shows Added Yet");
            noMovies.setFont(new Font("Impact", 77));
            noMovies.setPrefHeight(230);
//            noMovies.setStyle("-fx-background-color: transparent;");
            myMovieBox.getChildren().add(noMovies);
        } else {
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
                myMovieDetailBox[i] = new VBox();
                //  setting alignment
                myMovieDetailBox[i].setAlignment(javafx.geometry.Pos.CENTER);
                myMovieBox.setAlignment(javafx.geometry.Pos.CENTER);
                // giving id to the movie box
                myMovieBox.setId("myMovieBoxD");
                //  adding elements to the movie box
                myMovieDetailBox[i].getChildren().add(posterImageView);
                myMovieDetailBox[i].getChildren().add(showTitle);
                //  render the genres which are available
                // System.out.println(movieToPass.getGenre_1() + " " + movieToPass.getGenre_2());
                Label genres = new Label(shows.get(i).getGenre_1() + " " + (shows.get(i).getGenre_2() == null ? "" : "| " + shows.get(i).getGenre_2()));
                // creating the remove button
                Button remove = new Button("❌");
                remove.setStyle("-fx-background-color: #121212;" + "-fx-border-color: #121212;");
                remove.setCursor(Cursor.HAND);
                final int f = i;
                remove.setOnAction(event -> {
                    myShowList.remove(shows.get(f));
                    myMovieBox.getChildren().removeAll(myMovieBox.getChildren());
                    removeShows(showToPass);
                    setMyShows(myShowList);
                });
                // adding genre and their style class
                genres.getStyleClass().add("genres");
                // Setting id to vbox to apply css to it
                myMovieDetailBox[i].setId("myMovieDetailsV");
                // Add the movie vbox to horizontal scroll pane
                myMovieDetailBox[i].getStyleClass().add("anchor-pane");

                mainParent.getStyleClass().add("anchor-pane");
                myMoviePane.getStyleClass().add("anchor-pane");
                myMovieBox.getStyleClass().add("anchor-pane");
                search.getStyleClass().add("success");
                remove.getStyleClass().add("button-remove");
                myMovieDetailBox[i].getChildren().add(genres);
                myMovieDetailBox[i].getChildren().add(remove);
                myMovieBox.getChildren().add(myMovieDetailBox[i]);

            }
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
        movieBox.getChildren().removeAll(movieBox.getChildren());
        if (!searchQuery.isEmpty()) {
            //    Empty the hbox to re-render the search results
            List<ShowDefinition> searchedShows = SearchBarAlgorithm.searchAlgorithmShows(searchQuery, showsToRender);
            currentSearchedFilterShows = searchedShows;
            //   fetching the movies that fit the search by using a search function
            List<MovieDefinition> searchedMovies = SearchBarAlgorithm.searchAlgorithmMovies(searchQuery, moviesToRender);
            currentSearchedFilterMovies = searchedMovies;
            if (currentPage.equals("Shows")) {
                // setting the searched movies in the hbox
                setShowsInHBox(searchedShows);
            } else {
                // setting the searched movies in the hbox
                setMoviesInHbox(searchedMovies);
            }
        } else {
            //    Empty the hbox to re-render the search results
            currentSearchedFilterMovies = moviesToRender;
            currentSearchedFilterShows = showsToRender;
            if (currentPage.equals("Movies")) {
                setMoviesInHbox(moviesToRender);
            } else {
                setShowsInHBox(showsToRender);
            }
        }
    }


    /**
     * Show shows.
     *
     * @param event the event
     */
    public void showShows(ActionEvent event) {
        movieBox.getChildren().removeAll(movieBox.getChildren());
        myMovieBox.getChildren().removeAll(myMovieBox.getChildren());
        currentPage = "Shows";
        currentLabel.setText("Current Shows");
        myLabel.setText("My Shows");
        // getting all the genres and setting up the genre sidebar
        // never show vertical scroll bar for movie pane
        moviePane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        // getting all the movies and assigning them to movies to render
        movieButton.getStyleClass().remove("primary");
        movieButton.getStyleClass().add("light");
        showButton.getStyleClass().add("primary");
        mainParent.getStyleClass().add("anchor-pane");
        moviePane.getStyleClass().add("anchor-pane");
        movieBox.getStyleClass().add("anchor-pane");
        search.getStyleClass().add("success");
        setShowsInHBox(currentSearchedFilterShows);
        setMyShows(showPersonalShows());
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
        myMovieBox.getChildren().removeAll(myMovieBox.getChildren());
        currentPage = "Movies";
        currentLabel.setText("Current Movies");
        myLabel.setText("My Movies");
        // getting all the genres and setting up the genre sidebar
        // never show vertical scroll bar for movie pane
        moviePane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        // getting all the movies and assigning them to movies to render
        movieButton.getStyleClass().add("primary");
        showButton.getStyleClass().remove("primary");
        showButton.getStyleClass().add("light");
        mainParent.getStyleClass().add("anchor-pane");
        moviePane.getStyleClass().add("anchor-pane");
        movieBox.getStyleClass().add("anchor-pane");
        search.getStyleClass().add("success");
        setMoviesInHbox(currentSearchedFilterMovies);
        setMyMovies(myMovieList);
        // increasing the switch count so that api is not called again and again;
        switchCount++;
    }

    /**
     * Filter by genre.
     *
     * @param genres the genres
     */
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
                        currentSearchedFilterMovies) {
                    if (!filteredMovie.contains(movie)) {
                        if (movie.getGenre_1().equals(genre)) {

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
                        currentSearchedFilterShows) {
                    if (!filteredShow.contains(show)) {

                        if ((show.getGenre_1() != null) && show.getGenre_1().equals(genre)) {
                            filteredShow.add(show);
                        } else if ((show.getGenre_2() != null) && show.getGenre_2().equals(genre)) {
                            filteredShow.add(show);
                        }
                    }
                }
            }
            //  pass to the render shows function

            setShowsInHBox(filteredShow);
        }


    }

    /**
     * Logout user.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void logoutUser(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterLoginPage.fxml"));
        root = loader.load();
        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        // Adding the appHome.css file from the resources directory
        String css = this.getClass().getResource("appHome.css").toExternalForm();
        // add the stylesheet to the scene
        scene.getStylesheets().add(css);
        // setting invalid password label
        stage.setScene(scene);
    }
}






