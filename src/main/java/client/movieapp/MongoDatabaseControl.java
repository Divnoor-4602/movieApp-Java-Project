package client.movieapp;

import client.movieapp.movieshowdata.BridgeControllerInstance;
import client.movieapp.movieshowdata.MovieDefinition;
import client.movieapp.movieshowdata.ShowDefinition;
import com.mongodb.ConnectionString;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;

public class MongoDatabaseControl {

    // create a connection uri and connect it to a mongo client
    static MongoClient mongoClient = MongoClients.create(new ConnectionString("mongodb+srv://divnoorsingh4602:divnoor@movie-db.tqysio1.mongodb.net/?retryWrites=true&w=majority"));
    // fetching the database
    static MongoDatabase mongoDB = mongoClient.getDatabase("movielistDB");
    // fetching collections in the database
    static MongoCollection<Document> mongoCollection = mongoDB.getCollection("movieDB");

    public static boolean registerNewPerson(String nameFirst, String nameLast, String emailAddress, String password) {
        List<MovieDefinition> moviesTosSave = new ArrayList<>();
        List<ShowDefinition> showsToSave = new ArrayList<>();
        // create a document to add to the database
        Document newUser = new Document("first name", nameFirst)
                .append("last name", nameLast)
                .append("email", emailAddress)
                .append("password", password)
                .append("movie list", moviesTosSave)
                .append("show list", showsToSave);

        //  Adding the document to the collection
        try {
            mongoCollection.insertOne(newUser);
            System.out.println("Successfully added document");
            BridgeControllerInstance data = BridgeControllerInstance.getInstance();
            data.setCurrentUser("Welcome, " + Objects.requireNonNull(nameFirst));
            return true;
        } catch (MongoWriteException e) {
            System.out.println("Error writing document");
            System.out.println(e);
            return false;
        }
    }

    public static boolean loginRegisteredUser(String emailToFind, String passwordToFind) {
        // finds the user by their email and gets the first result and casts iterable to a document
        try {
            BridgeControllerInstance data = BridgeControllerInstance.getInstance();
            Document foundUser = (Document) mongoCollection.find(eq("email", emailToFind)).first();
            // retrieve the password from the object document if the user if found
            Object passwordSaved = Objects.requireNonNull(foundUser).get("password");
            data.setCurrentUser("Welcome, " + Objects.requireNonNull(foundUser).get("first name"));
            // return true or false if the password matches
            return passwordSaved.equals(passwordToFind);
        } catch (NullPointerException e) {
            System.out.println("user not found or password missing");
            System.out.println(e);
            return false;
        }
    }
}



