package client.movieapp.movieshowdata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


/**
 * The type Application data.
 */
public class ApplicationData {

    /**
     * The Movies api personal url.
     */
// API made on JS hosted on Render to get real time shows and movies data
    public String moviesAPIPersonalURL = "https://movie-api-tmdb.onrender.com/";

    /**
     * The Gson builder.
     */
// Setting up GSON to serialize and deserilaize content into MovieDefinition and MovieList
    Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Gets real time movies.
     *
     * @return the real time movies of type Movie definition
     * @throws URISyntaxException   the uri syntax exception
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public List<MovieDefinition> getRealTimeMovies() throws URISyntaxException, IOException, InterruptedException {
        String movieEndpoint = "currentMovies";
        // creating a https request to the API endpoint to get the data
        HttpRequest movieRequest = HttpRequest.newBuilder().uri(new URI(moviesAPIPersonalURL + movieEndpoint)).GET().build();
        // creating a client to send the http request
        HttpClient movieClient = HttpClient.newHttpClient();
        // getting the movie response and converting it into a string (JSON)
        HttpResponse<String> movieResponse = movieClient.send(movieRequest, HttpResponse.BodyHandlers.ofString());
        //  saving movie JSON into a movie real time list using gson which uses the wrapper classes defined to do this
        MovieList movieRealTimeList;
        movieRealTimeList = gsonBuilder.fromJson(movieResponse.body(), MovieList.class);
        return movieRealTimeList.getMovies();
    }

    /**
     * Gets real time shows.
     *
     * @return the real time shows list of type Show definition
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     * @throws URISyntaxException   the uri syntax exception
     */
    public List<ShowDefinition> getRealTimeShows() throws IOException, InterruptedException, URISyntaxException {
        String showEndpoint = "currentShows";
        // creating a https request to the API endpoint to get the data
        HttpRequest showRequest = HttpRequest.newBuilder().uri(new URI(moviesAPIPersonalURL + showEndpoint)).GET().build();
        // creating a client to send the http request
        HttpClient showClient = HttpClient.newHttpClient();
        // getting the show response and converting it into a string (JSON)
        HttpResponse<String> showResponse = showClient.send(showRequest, HttpResponse.BodyHandlers.ofString());
        //  saving movie JSON into a show real time list using gson which uses the wrapper classes defined to do this
        ShowList showRealTimeList;
        showRealTimeList = gsonBuilder.fromJson(showResponse.body(), ShowList.class);
        return showRealTimeList.getShows();
    }


}