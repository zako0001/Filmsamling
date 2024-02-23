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
    public String searchMovie(String searchTitle) {
        ArrayList<Movie> results= new ArrayList<Movie>();
        for(Movie movie : movieList) {
            if(movie.getTitle().toLowerCase().contains(searchTitle.toLowerCase())) {
                results.add(movie);
            }
        }
        if(results.isEmpty()) {
            return "No match for \"" + searchTitle + "\".";
        }
        else {
            String returnString = "---Movies containing \"" + searchTitle + "\" in title---\n";
            for(Movie movie : results) {
                returnString += "\n" + movie + "\n";
            }
            return returnString + "\n---Search result ends---";
        }
    }

    // Object method
    @Override
    public String toString() {
        String returnString = "---MovieCollection begins---\n";
        for(Movie movie : movieList) {
            returnString += "\n" + movie + "\n";
        }
        return returnString + "\n---MovieCollection ends---";
    }
}