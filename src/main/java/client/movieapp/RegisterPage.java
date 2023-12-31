package client.movieapp;

import client.movieapp.PasswordSecurity.PasswordOptions;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;


/**
 * The type Register page.
 */
//  controller for the register page
public class RegisterPage implements Initializable {
    /**
     * The Root.
     */
    Parent root;
    /**
     * The Stage.
     */
    Stage stage;
    /**
     * The Scene.
     */
    Scene scene;
    /**
     * The Register box.
     */
    @FXML
    public BorderPane registerBox;
    /**
     * The First name.
     */
    @FXML
    public TextField firstName;
    /**
     * The Last name.
     */
    @FXML
    public TextField lastName;
    /**
     * The Email field.
     */
    @FXML
    public TextField emailField;
    @FXML
    private AnchorPane outerPane;
    @FXML
    private AnchorPane innerPane;
    @FXML
    private AnchorPane lowerPane;
    /**
     * The Password field.
     */
    @FXML
    public TextField passwordField;
    /**
     * The Sign up button.
     */
    @FXML
    public Button signUpButton;
    /**
     * The Login redirect label.
     */
    @FXML
    public Hyperlink loginRedirectLabel;
    /**
     * The Invalid password label.
     */
    @FXML
    public Label invalidPasswordLabel;

    /**
     * The Invalid pass.
     */
    static boolean invalidPass = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (invalidPass) {
            invalidPasswordLabel.setText("Invalid password or email");
        } else {
            invalidPasswordLabel.setText("");
        }
        outerPane.getStyleClass().add("outerPane");
        innerPane.getStyleClass().add("registerInnerPane");
        lowerPane.getStyleClass().add("lowerPane");
        signUpButton.getStyleClass().add("success");
    }

    /**
     * Load scene.
     *
     * @param nameFXML the name fxml
     * @param event    the event
     * @throws IOException the io exception
     */
    public void loadScene(String nameFXML, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(nameFXML));
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


    /**
     * Sign up.
     *
     * @param event the event
     * @throws NoSuchAlgorithmException the no such algorithm exception
     * @throws IOException              the io exception
     */
    @FXML
    public void signUp(ActionEvent event) throws NoSuchAlgorithmException, IOException {
        // getting user details from the register form
        String nameInitial = firstName.getText();
        String nameEnd = lastName.getText();
        // getting email from the email field and validating it
        String email = emailField.getText();
        //  getting password from the password field and then validating the password
        String passwordUser = "";
        if (PasswordOptions.passwordValidator(passwordField.getText()) && emailValidator(email)) {
            passwordUser = PasswordOptions.passwordEncryptor(passwordField.getText().toLowerCase());
            invalidPass = false;
            // calls mongo db class and register method to save details in mongo db database
            boolean registrationStatus = MongoDatabaseControl.registerNewPerson(nameInitial, nameEnd, email, passwordUser);
            if (registrationStatus) {
                loadScene("movieSceneHome.fxml", event);
            } else {
                loadScene("registerPage.fxml", event);
            }
            // on successful register render the user to the movie application page
        } else {
            // if the password is not correct, make the user re-enter the password
            invalidPass = true;
            loadScene("registerPage.fxml", event);
        }
        System.out.println("Passwords is " + passwordUser);
    }

    /**
     * Switch to login page.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    @FXML
    public void switchToLoginPage(ActionEvent event) throws IOException {
        loadScene("RegisterLoginPage.fxml", event);
    }

    /**
     * Email validator boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public boolean emailValidator(String email) {
        // using regex symbols to check email validation
        String regexPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        // checks numerics from 0-9, uppercase and lowercase, dot not allowed at start
        // return a boolean if the email entered matches the requirements
        return Pattern.compile(regexPattern).matcher(email).matches();

    }


}
