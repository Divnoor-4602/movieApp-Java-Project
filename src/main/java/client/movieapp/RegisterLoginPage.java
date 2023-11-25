package client.movieapp;

import client.movieapp.PasswordSecurity.PasswordOptions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

// Controller for the login page
public class RegisterLoginPage implements Initializable {
    @FXML
    public BorderPane mainLoginBox;
    @FXML
    public AnchorPane ImageBox;
    @FXML
    public ImageView companyImageBox;
    @FXML
    public Label companyTitle;
    @FXML
    public Label emailLabel;

    @FXML
    private AnchorPane outerPane;
    @FXML
    public TextField emailTextBox;
    @FXML
    public Label passwordLabel;

    @FXML
    private AnchorPane rightPane;
    @FXML
    public TextField passwordTextBox;
    @FXML
    public Button loginInButton;
    @FXML
    public Label registerAccountLabel;
    @FXML
    public Hyperlink createNewAccountPage;

    @FXML
    public void registerNewAccount(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterPage.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        // Adding the appHome.css file from the resources directory
        String css = this.getClass().getResource("appHome.css").toExternalForm();
        // add the stylesheet to the scene
        scene.getStylesheets().add(css);
        stage.setScene(scene);

    }

    // The function is called when log in button is pressed, the information is saved
    @FXML
    public void loginAccount(ActionEvent event) throws NoSuchAlgorithmException, IOException {
        // get the email address of the user
        String emailAddress = emailTextBox.getText();
        // create a password hash by getting the password from the user and save it in variable
        String passwordUser = PasswordOptions.passwordEncryptor(passwordTextBox.getText());
        // todo: create a function to check if the password entered matches with the email address


        System.out.println(passwordUser);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // companyImageBox = new ImageView(new Image("/Users/divnoor/IdeaProjects/javafx-learning/movieApp/src/main/java/client/movieapp/—Pngtree—cartoon movie popcorn film glasses_6125864.png"));
        // companyImageBox = new ImageView(new Image("—Pngtree—cartoon movie popcorn film glasses_6125864.png"));

        outerPane.getStyleClass().add("outerPane");
        mainLoginBox.getStyleClass().add("innerPane");
        rightPane.getStyleClass().add("rightPane");
        loginInButton.getStyleClass().add("outerPane");
        emailTextBox.getStyleClass().add("inputBox");
        passwordTextBox.getStyleClass().add("inputBox");

    }
}

