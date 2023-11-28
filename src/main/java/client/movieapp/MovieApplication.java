// todo: make an add button on every movie which on click should add it to the users movie and show list
// todo: make a remove button which removes the movie from the users list

// todo: connect the app to a database which saves login info and movelist and retrieves it

// todo: make a logout button to logout the user and reset all information and retrieves the login and register page


package client.movieapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * The type Movie application.
 */
public class MovieApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MovieApplication.class.getResource("RegisterLoginPage.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        // Adding the appHome.css file from the resources directory
        String css = this.getClass().getResource("appHome.css").toExternalForm();
        // add the stylesheet to the scene
        scene.getStylesheets().add(css);
        stage.setTitle("Movie Application");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws URISyntaxException   the uri syntax exception
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        launch();
    }
}


// ,----,          ,----, ,-.----.             ,----,          ,----,
//         ,---,.        .'   .`|        .'   .`| \    /  \          .'   .`|        .'   .`|
//         ,'  .' |     .'   .'   ;     .'   .'   ; |   :    \      .'   .'   ;     .'   .'   ;
//         ,---.'   |   ,---, '    .'   ,---, '    .' |   |  .\ :   ,---, '    .'   ,---, '    .'
//         |   |   .'   |   :     ./    |   :     ./  .   :  |: |   |   :     ./    |   :     ./
//         :   :  |-,   ;   | .'  /     ;   | .'  /   |   |   \ :   ;   | .'  /     ;   | .'  /
//         :   |  ;/|   `---' /  ;      `---' /  ;    |   : .   /   `---' /  ;      `---' /  ;
//         |   :   .'     /  ;  /         /  ;  /     ;   | |`-'      /  ;  /         /  ;  /
//         |   |  |-,    ;  /  /--,      ;  /  /--,   |   | ;        ;  /  /--,      ;  /  /--,
//         '   :  ;/|   /  /  / .`|     /  /  / .`|   :   ' |       /  /  / .`|     /  /  / .`|
//         |   |    \ ./__;       :   ./__;       :   :   : :     ./__;       :   ./__;       :
//         |   :   .' |   :     .'    |   :     .'    |   | :     |   :     .'    |   :     .'
//         |   | ,'   ;   |  .'       ;   |  .'       `---'.|     ;   |  .'       ;   |  .'
//         `----'     `---'           `---'             `---`     `---'           `---'
//
//         ,--,
//         ,---.'|              ____            ,--.                                        ,----,
//         |   | :            ,'  , `.        ,--.'|   .--.--.        ,----..             .'   .`|
//         :   : |         ,-+-,.' _ |    ,--,:  : |  /  /    '.     /   /   \         .'   .'   ;
//         |   ' :      ,-+-. ;   , || ,`--.'`|  ' : |  :  /`. /    /   .     :      ,---, '    .'
//         ;   ; '     ,--.'|'   |  ;| |   :  :  | | ;  |  |--`    .   /   ;.  \     |   :     ./
//         '   | |__  |   |  ,', |  ': :   |   \ | : |  :  ;_     .   ;   /  ` ;     ;   | .'  /
//         |   | :.'| |   | /  | |  || |   : '  '; |  \  \    `.  ;   |  ; \ ; |     `---' /  ;
//         '   :    ; '   | :  | :  |, '   ' ;.    ;   `----.   \ |   :  | ; | '       /  ;  /
//         |   |  ./  ;   . |  ; |--'  |   | | \   |   __ \  \  | .   |  ' ' ' :      ;  /  /--,
//         ;   : ;    |   : |  | ,     '   : |  ; .'  /  /`--'  / '   ;  \; /  |     /  /  / .`|
//         |   ,/     |   : '  |/      |   | '`--'   '--'.     /   \   \  ',  . \  ./__;       :
//         '---'      ;   | |`-'       '   : |         `--'---'     ;   :      ; | |   :     .'
//         |   ;/           ;   |.'                       \   \ .'`--"  ;   |  .'
//         '---'            '---'                          `---`        `---'
//
