module client.movieapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires java.net.http;

    requires javafx.web;
    requires java.desktop;
    requires com.google.gson;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;
    requires org.mongodb.driver.sync.client;


    exports client.movieapp.movieshowdata;
    opens client.movieapp.movieshowdata to com.google.gson, javafx.fxml, javafx.web;
    exports client.movieapp;
    opens client.movieapp to  com.google.gson, javafx.fxml, javafx.web;

}