module client.movieapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires java.net.http;

    requires java.desktop;
    requires com.google.gson;
    requires javafx.web;


    exports client.movieapp.movieshowdata;
    opens client.movieapp.movieshowdata to javafx.web, com.google.gson, javafx.fxml;
    exports client.movieapp;
    opens client.movieapp to  com.google.gson, javafx.fxml, javafx.web;

}