package domain_model;

import java.util.Comparator;

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

    public Movie[] searchMovie(String searchTitle) {
        return movieCollection.searchMovie(searchTitle);
    }

    public void editMovie(Movie movie, Object oldValue, Object newValue) {
        movieCollection.editMovie(movie, oldValue, newValue);
    }

    public void deleteMovie(Movie movie) {
        movieCollection.deleteMovie(movie);
    }

    public Movie[] showMovieCollection(Comparator<Movie> movieComparator) {
        return movieCollection.showMovieCollection(movieComparator);
    }

    public void loadFromFile() {
        movieCollection.loadFromFile();
    }

    public void saveToFile() {
        movieCollection.saveToFile();
    }
}