import java.util.ArrayList;

public class MovieCollection {

    // Attribute
    private ArrayList<Movie> movieList;

    // Constructor
    public MovieCollection() {
        movieList = new ArrayList<Movie>();
    }

    // Methods
    public void addMovie(String title, String director, int yearCreated, boolean isInColor, double lengthInMinutes, String genre) {
        movieList.add(new Movie(title, director, yearCreated, isInColor, lengthInMinutes, genre));
    }
    public MovieCollection searchMovie(String searchTitle) {
        MovieCollection mc = new MovieCollection();
        for(Movie movie : this.movieList) {
            if(movie.getTitle().toLowerCase().contains(searchTitle.toLowerCase())) {
                mc.movieList.add(movie);
            }
        }
        if(mc.movieList.isEmpty()) {
            return null;
        }
        else {
            return mc;
        }
    }

    // Object method
    @Override
    public String toString() {
        StringBuilder returnString = new StringBuilder();
        for(Movie movie : movieList) {
            returnString.append("\n").append(movie).append("\n");
        }
        return returnString.toString();
    }
}