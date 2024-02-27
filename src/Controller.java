import java.util.ArrayList;

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
    public ArrayList<Integer> searchMovie(String searchTitle) {
        return movieCollection.searchMovie(searchTitle);
    }
    public String getMovieTitle(int index) {
        return movieCollection.getMovieTitle(index);
    }
    public String getMovie(int index) {
        return movieCollection.getMovie(index);
    }
    public void editMovie(int index, int property, String input) {
        movieCollection.editMovie(index, property, input);
    }
    public String showMovieCollection() {
        return movieCollection.toString();
    }
}
