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


//  controller for the register page
public class RegisterPage implements Initializable {
    Parent root;
    Stage stage;
    Scene scene;
    @FXML
    public BorderPane registerBox;
    @FXML
    public TextField firstName;
    @FXML
    public TextField lastName;
    @FXML
    public TextField emailField;
    @FXML
    private AnchorPane outerPane;
    @FXML
    public TextField passwordField;
    @FXML
    public Button signUpButton;
    @FXML
    public Hyperlink loginRedirectLabel;
    @FXML
    public Label invalidPasswordLabel;

    static boolean invalidPass = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (invalidPass) {
            invalidPasswordLabel.setText("Invalid password or email");
        } else {
            invalidPasswordLabel.setText("");
        }
        outerPane.getStyleClass().add("outerPane");
    }


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
        } else {
            // if the password is not correct, make the user re-enter the password
            invalidPass = true;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterPage.fxml"));
            root = loader.load();
            stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            // setting invalid password label
            stage.setScene(scene);
        }
        System.out.println("Passwords is" + passwordUser);
    }

    // todo: save all this data to a mongodb database and link the register page to the login page
    // todo: if the user is successfully logged in go to the movie app otherwise re render the login page or
    // todo: go to register page if the email address does not exist in the database

    @FXML
    public void switchToLoginPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterloginPage.fxml"));
        root = loader.load();
        //    todo: complete this
    }

    public boolean emailValidator(String email) {
        // using regex symbols to check email validation
        String regexPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        //  checks numerics from 0-9, uppercase and lowercase, dot not allowed at start
        // return a boolean if the email entered matches the requirements
        return Pattern.compile(regexPattern).matcher(email).matches();

    }


}