import java.util.ArrayList;

public class MovieCollection {

    // Attributes
    private final ArrayList<Movie> collection;

    // Constructor
    public MovieCollection() {
        collection = new ArrayList<Movie>();
    }

    // Method
    public void addMovie(Movie m) {
        collection.add(m);
    }
}