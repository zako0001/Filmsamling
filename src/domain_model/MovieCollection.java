package domain_model;

import domain_model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieCollection {

    // Attribute
    private final List<Movie> movieCollectionList;

    // Constructor
    public MovieCollection() {
        movieCollectionList = new ArrayList<>();
    }

    // Methods
    public void addMovie(String title, String director, int yearCreated, boolean isInColor, int lengthInMinutes, String genre) {
        movieCollectionList.add(new Movie(title, director, yearCreated, isInColor, lengthInMinutes, genre));
    }

    public List<Movie> searchMovie(String searchTitle) {
        List<Movie> movies = new ArrayList<>();
        for (Movie movie : movieCollectionList) {
            if (movie.getTitle().toLowerCase().contains(searchTitle.toLowerCase())) {
                movies.add(movie);
            }
        }
        return movies;
    }

    public void deleteMovie(Movie movie) {
        movieCollectionList.remove(movie);
    }
}