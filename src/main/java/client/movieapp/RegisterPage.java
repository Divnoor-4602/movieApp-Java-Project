package client.movieapp;

import client.movieapp.PasswordSecurity.PasswordOptions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;


//  controller for the register page
public class RegisterPage {
    @FXML
    public BorderPane registerBox;
    @FXML
    public TextField firstName;
    @FXML
    public TextField lastName;
    @FXML
    public TextField emailField;
    @FXML
    public TextField passwordField;
    @FXML
    public Button signUpButton;
    @FXML
    public Hyperlink loginRedirectLabel;
    public Label invalidPasswordlLabel;

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
            passwordUser = PasswordOptions.passwordEncryptor(passwordField.getText());
        } else {
            // if the password is not correct, make the user re-enter the password
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterPage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            // setting invalid password label
            invalidPasswordlLabel.setText("Invalid password");
            stage.setScene(scene);
        }
        System.out.println("Passwords is" + passwordUser);
    }

    // todo: save all this data to a mongodb database and link the register page to the login page
    // todo: if the user is successfully logged in go to the movie app otherwise re render the login page or
    // todo: go to register page if the email address does not exist in the database
    // todo: make an email validator

    @FXML
    public void switchToLoginPage(ActionEvent event) {
    }

    public boolean emailValidator(String email) {
        return email.contains("@");
    }

}
