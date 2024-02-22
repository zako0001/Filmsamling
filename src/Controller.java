import java.util.Objects;

public class Controller {

    // Attribute
    private MovieCollection movieCollection;

    // Constructor
    public Controller() {
        movieCollection = new MovieCollection();
    }

    // Methods
    public void addMovie(String title, String director, int yearCreated, boolean isInColor, double lengthInMinutes, String genre) {
        movieCollection.addMovie(title, director, yearCreated, isInColor, lengthInMinutes, genre);
    }
    public String searchMovie(String searchTitle) {
        MovieCollection movies = movieCollection.searchMovie(searchTitle);
        if(Objects.isNull(movies)) {
            return "No match for \"" + searchTitle + "\".";
        } else {
            return "---Movies containing \"" + searchTitle + "\" in title---\n" + movies + "\n---Search result ends---";
        }
    }
    public String getMovieCollectionAsString() {
        return "---MovieCollection begins---\n" + movieCollection + "\n---MovieCollection ends---";
    }
}
