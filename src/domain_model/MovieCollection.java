package domain_model;

import data_source.FileHandler;
import java.util.ArrayList;
import java.util.Comparator;

public class MovieCollection {

    // Attribute
    private final ArrayList<Movie> movieCollectionList;
    private boolean hasChanged;

    // Constructor
    public MovieCollection() {
        movieCollectionList = new ArrayList<>();
        hasChanged = false;
    }

    // Methods
    public void addMovie(String title, String director, int yearCreated, boolean isInColor, int lengthInMinutes, String genre) {
        movieCollectionList.add(new Movie(title, director, yearCreated, isInColor, lengthInMinutes, genre));
        hasChanged = true;
    }

    public Movie[] searchMovie(String searchTitle) {

        return movieCollectionList.stream()
                .filter(movie -> movie.getTitle().toLowerCase().contains(searchTitle.toLowerCase()))
                .sorted(Movie.TITLE_ORDER)
                .toArray(Movie[]::new);
    }

    public void editMovie(Movie movie, Object oldValue, Object newValue) {

        if (oldValue.getClass() == newValue.getClass() && !oldValue.equals(newValue)) {
            boolean edited = true;
            switch (oldValue) {
                case String s when s.equals(movie.getTitle()) -> movie.setTitle((String) newValue);
                case String s when s.equals(movie.getDirector()) -> movie.setDirector((String) newValue);
                case Integer i when i.equals(movie.getYearCreated()) -> movie.setYearCreated((int) newValue);
                case Boolean b when b.equals(movie.getIsInColor()) -> movie.setIsInColor((boolean) newValue);
                case Integer i when i.equals(movie.getLengthInMinutes()) -> movie.setLengthInMinutes((int) newValue);
                case String s when s.equals(movie.getGenre()) -> movie.setGenre((String) newValue);
                default -> edited = false;
            }
            if (edited) {
                hasChanged = true;
            }
        }
    }

    public void deleteMovie(Movie movie) {
        movieCollectionList.remove(movie);
        hasChanged = true;
    }

    public Movie[] showMovieCollection(Comparator<Movie> movieComparator) {

        return movieCollectionList.stream()
                .sorted(movieComparator)
                .toArray(Movie[]::new);
    }

    public void loadFromFile() {
        movieCollectionList.addAll(FileHandler.loadMovies());
    }

    public void saveToFile() {
        if (hasChanged) {
            FileHandler.saveMovies(movieCollectionList);
        }
    }
}