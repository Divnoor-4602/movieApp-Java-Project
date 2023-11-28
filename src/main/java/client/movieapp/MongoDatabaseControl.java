package client.movieapp;

import client.movieapp.movieshowdata.BridgeControllerInstance;
import client.movieapp.movieshowdata.MovieDefinition;
import client.movieapp.movieshowdata.ShowDefinition;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.List;
import java.util.Objects;

import static com.mongodb.client.model.Filters.eq;

/**
 * The type Mongo database control.
 */
public class MongoDatabaseControl {
    /**
     * The Pojo codec provider.
     */
    static PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
    /**
     * The Codec registries.
     */
    static CodecRegistry codecRegistries = CodecRegistries.fromProviders(pojoCodecProvider);
    /**
     * The Mongo client.
     */
    static MongoClient mongoClient = MongoClients.create(MongoClientSettings.builder().applyConnectionString(new ConnectionString("mongodb+srv://divnoorsingh4602:divnoor@movie-db.tqysio1.mongodb.net/?retryWrites=true&w=majority")).codecRegistry(CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), codecRegistries)).build());

    /**
     * The constant mongoDB.
     */
// fetching the database
    static MongoDatabase mongoDB = mongoClient.getDatabase("movielistDB");
    /**
     * The Mongo collection.
     */
// fetching collections in the database
    static MongoCollection<MongoSpecificClass> mongoCollection = mongoDB.getCollection("movieDB", MongoSpecificClass.class);

    /**
     * Register new person boolean.
     *
     * @param nameFirst    the name first
     * @param nameLast     the name last
     * @param emailAddress the email address
     * @param password     the password
     * @return the boolean
     */
    public static Boolean registerNewPerson(String nameFirst, String nameLast, String emailAddress, String password) {
        System.out.println("registering");
        // create instance of the mongo class
        MongoSpecificClass userToAdd = new MongoSpecificClass();
        // setting the properties to add
        userToAdd.setFirstName(nameFirst);
        userToAdd.setLastName(nameLast);
        userToAdd.setEmailAddress(emailAddress);
        userToAdd.setPassword(password);
        // inset a document of class mongo specific class to the collection
        try {
            mongoCollection.insertOne(userToAdd);
            System.out.println("Successfully added document");
            BridgeControllerInstance data = BridgeControllerInstance.getInstance();
            data.setCurrentUser("Welcome, " + Objects.requireNonNull(nameFirst));
            data.setCurrentUserEmail(emailAddress);
            return true;
        } catch (MongoWriteException e) {
            System.out.println("Error writing document");
            System.out.println(e);
            return false;
        }
    }

    /**
     * Login registered user boolean.
     *
     * @param emailToFind    the email to find
     * @param passwordToFind the password to find
     * @return the boolean
     */
    public static boolean loginRegisteredUser(String emailToFind, String passwordToFind) {
        // finds the user by their email and gets the first result and casts iterable to a document
        try {
            BridgeControllerInstance data = BridgeControllerInstance.getInstance();
            MongoSpecificClass foundUser = mongoCollection.find(new Document("emailAddress", emailToFind)).first();
            // retrieve the password from the object document if the user if found
            Object passwordSaved = Objects.requireNonNull(foundUser).getPassword();
            data.setCurrentUser("Welcome, " + Objects.requireNonNull(foundUser).getFirstName());
            // set the current user's email as well
            data.setCurrentUserEmail(emailToFind);
            // return true or false if the password matches
            return passwordSaved.equals(passwordToFind);
        } catch (NullPointerException e) {
            System.out.println("user not found or password missing");
            System.out.println(e);
            return false;
        }
    }

    /**
     * Save movie personal list.
     *
     * @param movie the movie
     */
    static public void saveMoviePersonalList(MovieDefinition movie) {
        BridgeControllerInstance data = BridgeControllerInstance.getInstance();
        String currentLoggedUser = data.getCurrentUserEmail();
        //  search through collection to get the user
        MongoSpecificClass foundUser = mongoCollection.find(eq("emailAddress", currentLoggedUser)).first();
        List<MovieDefinition> currentMovies = Objects.requireNonNull(foundUser).getMoviesToSave();
        currentMovies.add(movie);
        // setting the new movie in database
        foundUser.setMoviesToSave(currentMovies);
        try {
            // replace the object with the updated movie
            mongoCollection.replaceOne(eq("_id", foundUser.getId()), foundUser);
            System.out.println("Successfully added the movie");
            // mongoCollection.updateOne((MongoSpecificClass) foundUser, set("moviesToSave", currentMovies));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * Save shows personal list.
     *
     * @param show the show
     */
    static public void saveShowsPersonalList(ShowDefinition show) {
        BridgeControllerInstance data = BridgeControllerInstance.getInstance();
        String currentLoggedUser = data.getCurrentUserEmail();
        //  search through collection to get the user
        MongoSpecificClass foundUser = mongoCollection.find(eq("emailAddress", currentLoggedUser)).first();

        List<ShowDefinition> currentShows = Objects.requireNonNull(foundUser).getShowsToSave();
        currentShows.add(show);

        foundUser.setShowsToSave(currentShows);
        try {
            // replace the object with the updated movie
            mongoCollection.replaceOne(eq("_id", foundUser.getId()), foundUser);
            System.out.println("Successfully added the show");
            // mongoCollection.updateOne((MongoSpecificClass) foundUser, set("moviesToSave", currentMovies));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Show personal movies list.
     *
     * @return the list
     */
    static public List<MovieDefinition> showPersonalMovies() {
        BridgeControllerInstance data = BridgeControllerInstance.getInstance();
        String currentLoggedUser = data.getCurrentUserEmail();
        //  search through collection to get the user
        MongoSpecificClass foundUser = mongoCollection.find(eq("emailAddress", currentLoggedUser)).first();
        return Objects.requireNonNull(foundUser).getMoviesToSave();


    }

    /**
     * Show personal shows list.
     *
     * @return the list
     */
    static public List<ShowDefinition> showPersonalShows() {
        BridgeControllerInstance data = BridgeControllerInstance.getInstance();
        String currentLoggedUser = data.getCurrentUserEmail();
        //  search through collection to get the user
        MongoSpecificClass foundUser = mongoCollection.find(eq("emailAddress", currentLoggedUser)).first();
        return Objects.requireNonNull(foundUser).getShowsToSave();


    }

    /**
     * Remove movies.
     *
     * @param movie the movie
     */
    static public void removeMovies(MovieDefinition movie) {
        BridgeControllerInstance data = BridgeControllerInstance.getInstance();
        String currentLoggedUser = data.getCurrentUserEmail();
        //  search through collection to get the user
        MongoSpecificClass foundUser = mongoCollection.find(eq("emailAddress", currentLoggedUser)).first();
        List<MovieDefinition> currentMovies = Objects.requireNonNull(foundUser).getMoviesToSave();

        for (int i = 0; i < currentMovies.size(); i++) {
            if (currentMovies.get(i).getMovie_title().equalsIgnoreCase(movie.getMovie_title())) {
                currentMovies.remove(i);
                break;
            }
        }
        System.out.println(currentMovies);
        // setting the new movie in database
        foundUser.setMoviesToSave(currentMovies);
        try {
            // replace the object with the updated movie
            mongoCollection.replaceOne(eq("_id", foundUser.getId()), foundUser);
            System.out.println("Successfully removed the movie");
            // mongoCollection.updateOne((MongoSpecificClass) foundUser, set("moviesToSave", currentMovies));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Remove shows.
     *
     * @param show the show
     */
    static void removeShows(ShowDefinition show) {
        BridgeControllerInstance data = BridgeControllerInstance.getInstance();
        String currentLoggedUser = data.getCurrentUserEmail();
        //  search through collection to get the user
        MongoSpecificClass foundUser = mongoCollection.find(eq("emailAddress", currentLoggedUser)).first();
        List<ShowDefinition> currentShows = Objects.requireNonNull(foundUser).getShowsToSave();
        for (int i = 0; i < currentShows.size(); i++) {
            if (currentShows.get(i).getShow_title().equalsIgnoreCase(show.getShow_title())) {
                currentShows.remove(i);
                break;
            }
        }
        // setting the new movie in database
        foundUser.setShowsToSave(currentShows);
        try {
            // replace the object with the updated movie
            mongoCollection.replaceOne(eq("_id", foundUser.getId()), foundUser);
            System.out.println("Successfully removed the show");
            // mongoCollection.updateOne((MongoSpecificClass) foundUser, set("moviesToSave", currentMovies));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}


