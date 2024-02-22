public class Controller {

    // Attribute
    private MovieCollection movieCollection;

    // Constructor
    public Controller() {
        movieCollection = new MovieCollection();
    }

    // Method
    public void addMovie(String title, String director, int yearCreated, boolean isInColor, double lengthInMinutes, String genre) {
        movieCollection.addMovie(title, director, yearCreated, isInColor, lengthInMinutes, genre);
    }
}
