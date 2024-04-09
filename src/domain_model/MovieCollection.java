package domain_model;

import data_source.FileHandler;

import java.util.ArrayList;
import java.util.List;

public class MovieCollection {

    // Attribute
    private final ArrayList<Movie> movieCollectionList;

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

    public void loadFromFile(){
        movieCollectionList.addAll(FileHandler.loadMovies());
    }

    public void saveToFile(){
        FileHandler.saveMovies(movieCollectionList);
    }
}