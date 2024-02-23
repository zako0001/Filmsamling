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
        return movieCollection.searchMovie(searchTitle);
    }
    public String showMovieCollection() {
        return movieCollection.toString();
    }
}
