package domain_model;

import java.util.List;

public class Controller {

    // Attribute
    private final MovieCollection movieCollection;

    // Constructor
    public Controller() {
        movieCollection = new MovieCollection();
    }

    // Methods
    public void addMovie(String title, String director, int yearCreated, boolean isInColor, int lengthInMinutes, String genre) {
        movieCollection.addMovie(title, director, yearCreated, isInColor, lengthInMinutes, genre);
    }

    public List<Movie> searchMovie(String searchTitle) {
        return movieCollection.searchMovie(searchTitle);
    }

    public void deleteMovie(Movie movie) {
        movieCollection.deleteMovie(movie);
    }

    public void loadFromFile() {
        movieCollection.loadFromFile();
    }

    public void saveToFile() {
        movieCollection.saveToFile();
    }
}