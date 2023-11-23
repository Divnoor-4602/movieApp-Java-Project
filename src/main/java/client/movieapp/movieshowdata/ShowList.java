package client.movieapp.movieshowdata;

import java.util.List;

/**
 * The type Show list.
 */
public class ShowList {

    /**
     * Gets shows.
     *
     * @return the shows
     */
    public List<ShowDefinition> getShows() {
        return shows;
    }

    /**
     * Sets shows.
     *
     * @param shows the shows
     */
    public void setShows(List<ShowDefinition> shows) {
        this.shows = shows;
    }

    private List<ShowDefinition> shows;

}
