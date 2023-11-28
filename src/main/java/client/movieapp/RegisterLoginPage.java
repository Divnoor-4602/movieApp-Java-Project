package client.movieapp;

import client.movieapp.PasswordSecurity.PasswordOptions;
import client.movieapp.movieshowdata.BridgeControllerInstance;
import client.movieapp.movieshowdata.MovieDefinition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

/**
 * The type Register login page.
 */
// Controller for the login page
public class RegisterLoginPage implements Initializable {
    /**
     * The Main login box.
     */
    @FXML
    public BorderPane mainLoginBox;
    /**
     * The Image box.
     */
    @FXML
    public AnchorPane ImageBox;
    /**
     * The Company image box.
     */
    @FXML
    public ImageView companyImageBox;
    /**
     * The Company title.
     */
    @FXML
    public Label companyTitle;
    /**
     * The Email label.
     */
    @FXML
    public Label emailLabel;
    /**
     * The Invalid password label.
     */
    @FXML
    public Label invalidPasswordLabel;
    /**
     * The Invalid email label.
     */
    @FXML
    public Label invalidEmailLabel;

    @FXML
    private AnchorPane outerPane;
    /**
     * The Email text box.
     */
    @FXML
    public TextField emailTextBox;
    /**
     * The Password label.
     */
    @FXML
    public Label passwordLabel;

    @FXML
    private AnchorPane rightPane;
    /**
     * The Password text box.
     */
    @FXML
    public TextField passwordTextBox;
    /**
     * The Login in button.
     */
    @FXML
    public Button loginInButton;
    /**
     * The Register account label.
     */
    @FXML
    public Label registerAccountLabel;
    /**
     * The Create new account page.
     */
    @FXML
    public Hyperlink createNewAccountPage;

    /**
     * The Invalid password.
     */
    static Boolean invalidPassword = false;

    /**
     * Load scene.
     *
     * @param nameFXML the name fxml
     * @param event    the event
     * @throws IOException the io exception
     */
    public void loadScene(String nameFXML, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nameFXML));
        Parent root = loader.load();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        // Adding the appHome.css file from the resources directory
        String css = this.getClass().getResource("appHome.css").toExternalForm();
        // add the stylesheet to the scene
        scene.getStylesheets().add(css);
        // setting invalid password label
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // companyImageBox = new ImageView(new Image("/Users/divnoor/IdeaProjects/javafx-learning/movieApp/src/main/java/client/movieapp/—Pngtree—cartoon movie popcorn film glasses_6125864.png"));
        // companyImageBox = new ImageView(new Image("—Pngtree—cartoon movie popcorn film glasses_6125864.png"));
        if (invalidPassword) {
            invalidEmailLabel.setText("Invalid email");
            invalidPasswordLabel.setText("Invalid password");
            invalidPassword = false;
        } else {
            invalidPasswordLabel.setText("");
            invalidEmailLabel.setText("");
        }

        outerPane.getStyleClass().add("outerPane");
        mainLoginBox.getStyleClass().add("innerPane");
        rightPane.getStyleClass().add("rightPane");
        loginInButton.getStyleClass().add("outerPane");
        emailTextBox.getStyleClass().add("inputBox");
        passwordTextBox.getStyleClass().add("inputBox");

    }


    /**
     * Register new account.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    public void registerNewAccount(ActionEvent event) throws IOException {
        loadScene("RegisterPage.fxml", event);

    }

    /**
     * Login account.
     *
     * @param event the event
     * @throws NoSuchAlgorithmException the no such algorithm exception
     * @throws IOException              the io exception
     */
// The function is called when log in button is pressed, the information is saved
    @FXML
    public void loginAccount(ActionEvent event) throws NoSuchAlgorithmException, IOException {
        // get the email address of the user
        String emailAddress = emailTextBox.getText();
        // create a password hash by getting the password from the user and save it in variable
        String passwordUser = PasswordOptions.passwordEncryptor(passwordTextBox.getText());
        // login the user and match the password using this function
        boolean passwordMatched = MongoDatabaseControl.loginRegisteredUser(emailAddress, passwordUser);
        System.out.println(passwordMatched);
        if (passwordMatched) {
            loadScene("movieSceneHome.fxml", event);
        } else {
            invalidPassword = true;
            loadScene("RegisterLoginPage.fxml", event);
        }


        System.out.println(passwordUser);
    }

}

