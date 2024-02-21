import java.util.ArrayList;

public class Controller {

    // Attribute
    MovieCollection collection;

    // Constructor
    public Controller() {
        collection = new MovieCollection();
    }

    // Method
    public void addMovie(String title, String director) {
        collection.addMovie(new Movie(title, director));
    }
}
