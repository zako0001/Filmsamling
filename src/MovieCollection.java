public class MovieCollection {

    // Attributes
    private final Movie[] collection;
    private int cursor; // Lowest index with null element

    // Constructor
    public MovieCollection(int size) {
        collection = new Movie[size];
        cursor = 0;
    }

    // Methods
    public void addMovie(String title, String director) {
        if (cursor < collection.length) {
            collection[cursor++] = new Movie(title, director);
            System.out.println("Movie added.");
        } else {
            System.out.println("Movie adding failed: Collection full.");
        }
    }
}