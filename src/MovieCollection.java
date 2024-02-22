import java.util.ArrayList;

public class MovieCollection {

    // Attribute
    private ArrayList<Movie> movieList;

    // Constructor
    public MovieCollection() {
        movieList = new ArrayList<Movie>();
    }

    // Method
    public void addMovie(String title, String director, int yearCreated, boolean isInColor, double lengthInMinutes, String genre) {
        movieList.add(new Movie(title, director, yearCreated, isInColor, lengthInMinutes, genre));
    }

    // Object method
    @Override
    public String toString() {
        String returnString = "\n---MovieCollection begins---";
        for(Movie movie : movieList) {returnString += "\n\n" + movie;}
        returnString += "\n\n---MovieCollection ends---";
        return returnString;
    }
}