package client.movieapp;

import client.movieapp.movieshowdata.BridgeControllerInstance;
import client.movieapp.movieshowdata.MovieDefinition;

import client.movieapp.movieshowdata.MovieList;
import client.movieapp.movieshowdata.ShowDefinition;
import javafx.event.ActionEvent;

import java.net.URI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static client.movieapp.MongoDatabaseControl.saveMoviePersonalList;
import static client.movieapp.MongoDatabaseControl.saveShowsPersonalList;
import static client.movieapp.MovieController.*;

/**
 * The type Individual movie controller.
 */
public class IndividualMovieController implements Initializable {

    @FXML
    private AnchorPane deepDesc;
    /**
     * The Details pane.
     */
    public AnchorPane detailsPane;
    /**
     * The Data.
     */
    BridgeControllerInstance data = BridgeControllerInstance.getInstance();
    @FXML
    private Button movieAdder;
    @FXML
    private WebView trailerWebView;
    @FXML
    private ImageView moviePosterImage;
    @FXML
    private Label movieTitleLabel = new Label("");
    @FXML
    private Label genreOne;
    @FXML
    private Label genreTwo;
    @FXML
    private Label movieDescription;
    @FXML
    private Label movieRating;
    @FXML
    private Label releaseDate;
    @FXML
    private Button homeButton;
    @FXML
    private Button trailerButton;

    /**
     * Add movie.
     *
     * @param event the event
     */
    @FXML
    void addMovie(ActionEvent event) {
        // adding movie to the movie list
        if (currentPage.equals("Movies")) {
            movieAdder.setText("Added");
            movieAdder.getStyleClass().remove("primary");
            movieAdder.getStyleClass().add("success");
            movieAdder.setCursor(Cursor.HAND);
            // myMovieList.add(data.getMovieToSend());
            saveMoviePersonalList(data.getMovieToSend());
            movieAdder.setDisable(true);
        } else {
            movieAdder.setText("Added");
            movieAdder.getStyleClass().remove("primary");
            movieAdder.getStyleClass().add("success");
            movieAdder.setCursor(Cursor.HAND);
            movieAdder.setDisable(true);
            // myShowList.add(data.getShowsToSend());
            saveShowsPersonalList(data.getShowsToSend());
        }
    }

    /**
     * Openurl.
     *
     * @param event the event
     * @throws IOException        the io exception
     * @throws URISyntaxException the uri syntax exception
     */
    @FXML
    void openurl(ActionEvent event) throws IOException, URISyntaxException {
        URI uri = URI.create(data.getMovieTrailerPath());
        java.awt.Desktop.getDesktop().browse(uri);

    }

    /**
     * Switch to home.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    public void switchToHome(ActionEvent event) throws IOException {
        currentPage = data.getCurrentAppPage();
        // method to switch back to the home page
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("movieSceneHome.fxml"));
        Parent root = loader.load();
        String css = this.getClass().getResource("appHome.css").toExternalForm();
        currentPage = "Movies";
        // add the stylesheet to the scene
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
    }

    /**
     * Description formatter string.
     *
     * @param desc the desc
     * @return the string
     */
    String descriptionFormatter(String desc) {
        String[] words = desc.split(" ");
        StringBuilder sentence = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (i % 17 == 0) {
                sentence.append("\n");
            } else {
                sentence.append(words[i]).append(" ");
            }
        }
        return sentence.toString();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movieTitleLabel.setText(data.getMovieName());
        String posterPathUrl = "https://image.tmdb.org/t/p/original";
        // getting the poster path to render on the page
        Image posterMovie = new Image(posterPathUrl + data.getMoviePoster(), 1920, 1080, true, false);
        moviePosterImage.setImage(posterMovie);
        genreOne.setText(data.getGenre1());
        genreTwo.setText(data.getGenre2());
        movieDescription.setText(descriptionFormatter(data.getMovieDescriptions()));
        movieRating.setText(data.getMovieRating().substring(0, 3) + "/10");
        releaseDate.setText(data.getMovieReleaseDate());

        // css
        deepDesc.getStyleClass().addAll(new String[]{"padding"});
        detailsPane.getStyleClass().addAll(new String[]{"anchor-pane", "padding"});
        movieDescription.getStyleClass().add("anchor-pane");
        moviePosterImage.getStyleClass().add("anchor-pane");
        // trailerWebView.getStyleClass().add("anchor-pane");
        homeButton.getStyleClass().addAll(new String[]{"drop-shadow", "scale", "transition"});
        movieAdder.getStyleClass().addAll(new String[]{"drop-shadow", "scale", "transition"});
        movieTitleLabel.getStyleClass().add("genres");
        movieRating.getStyleClass().add("ratings");
        releaseDate.getStyleClass().add("genres");
        genreOne.getStyleClass().add("genres");
        genreTwo.getStyleClass().add("genres");
        movieDescription.getStyleClass().add("genres");
        if (data.getCurrentAppPage().equals("Movies")) {
            System.out.println(myMovieList);
            for (MovieDefinition movie :
                    myMovieList) {
                if (movie.getMovie_title().equals(data.getMovieToSend().getMovie_title())) {
                    movieAdder.setText(("Added"));
                    movieAdder.getStyleClass().remove("primary");
                    movieAdder.getStyleClass().add("success");
                    movieAdder.setDisable(true);
                    break;
                }
            }
        } else if (data.getCurrentAppPage().equals("Shows")) {
            // System.out.println(data.getShowsToSend().getShow_title());
            // System.out.println(myShowList.get(0).getShow_title());
            System.out.println(myShowList);
            for (ShowDefinition show :
                    myShowList) {
                if (show.getShow_title().equals(data.getShowsToSend().getShow_title())) {
                    movieAdder.setText(("Added"));
                    movieAdder.getStyleClass().remove("primary");
                    movieAdder.getStyleClass().add("success");
                    movieAdder.setDisable(true);
                    break;
                }
            }

        }
    }
}

