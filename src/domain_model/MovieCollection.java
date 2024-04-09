package domain_model;

import data_source.FileHandler;
import java.util.ArrayList;

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

    public Movie[] searchMovie(String searchTitle) {

        return movieCollectionList.stream()
                .filter(movie -> movie.getTitle().toLowerCase().contains(searchTitle.toLowerCase()))
                .toArray(Movie[]::new);
    }

    public void editMovie(Movie movie, String oldValue, String newValue) {
        edit(movie, oldValue, newValue);
    }

    public void editMovie(Movie movie, int oldValue, int newValue) {
        edit(movie, oldValue, newValue);
    }

    public void editMovie(Movie movie, boolean oldValue, boolean newValue) {
        edit(movie, oldValue, newValue);
    }

    public void deleteMovie(Movie movie) {
        movieCollectionList.remove(movie);
    }

    public Movie[] showMovieCollection() {

        return movieCollectionList.toArray(Movie[]::new);
    }

    public void loadFromFile(){
        movieCollectionList.addAll(FileHandler.loadMovies());
    }

    public void saveToFile(){
        FileHandler.saveMovies(movieCollectionList);
    }

    // Auxiliary method
    private void edit(Movie movie, Object oldValue, Object newValue) {

        if (!oldValue.equals(newValue)) {

            switch (oldValue) {
                case String s when s.equals(movie.getTitle()) -> movie.setTitle((String) newValue);
                case String s when s.equals(movie.getDirector()) -> movie.setDirector((String) newValue);
                case Integer i when i.equals(movie.getYearCreated()) -> movie.setYearCreated((int) newValue);
                case Boolean b when b.equals(movie.getIsInColor()) -> movie.setIsInColor((boolean) newValue);
                case Integer i when i == movie.getLengthInMinutes() -> movie.setLengthInMinutes((int) newValue);
                case String s when s.equals(movie.getGenre()) -> movie.setGenre((String) newValue);
                default -> {}
            }
        }
    }
}