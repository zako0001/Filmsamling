import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MovieCollectionTest {

    @DisplayName("0 added")
    @Test
    void getMovieCollectionList() {
        // Arrange
        MovieCollection mc = new MovieCollection();
        // Act
        int actualSize = mc.getMovieCollectionList().size();
        // Assert
        int expectedSize = 0;
        assertEquals(expectedSize, actualSize);
    }

    @DisplayName("1 added")
    @Test
    void addMovieOne() {
        // Arrange
        MovieCollection mc = new MovieCollection();
        // Act
        mc.addMovie("Avatar", "James Cameron", 2009, true, 162, "Action");
        // Assert
        int expectedSize = 1;
        int actualSize = mc.getMovieCollectionList().size();
        assertEquals(expectedSize, actualSize);
    }

    @DisplayName("2 added")
    @Test
    void addMovieTwo() {
        // Arrange
        MovieCollection mc = new MovieCollection();
        // Act
        mc.addMovie("Avatar", "James Cameron", 2009, true, 162, "Action");
        mc.addMovie("Avatar: The Way of Water", "James Cameron", 2022, true, 192, "Action");
        // Assert
        int expectedSize = 2;
        int actualSize = mc.getMovieCollectionList().size();
        assertEquals(expectedSize, actualSize);
    }

    @DisplayName("0 found")
    @Test
    void searchMovieZero() {
        // Arrange
        MovieCollection mc = new MovieCollection();
        mc.addMovie("Avatar", "James Cameron", 2009, true, 162, "Action");
        mc.addMovie("Avatar: The Way of Water", "James Cameron", 2022, true, 192, "Action");
        mc.addMovie("Love Actually", "Richard Curtis", 2003, true, 135, "Comedy");
        mc.addMovie("Harry Potter and the Prisoner of Azkaban", "Alfonso Cuarón", 2004, true, 142, "Adventure");
        // Act
        int actualSize = mc.searchMovie("q").size();
        // Assert
        int expectedSize = 0;
        assertEquals(expectedSize, actualSize);
    }

    @DisplayName("1 found")
    @Test
    void searchMovieOne() {
        // Arrange
        MovieCollection mc = new MovieCollection();
        mc.addMovie("Avatar", "James Cameron", 2009, true, 162, "Action");
        mc.addMovie("Avatar: The Way of Water", "James Cameron", 2022, true, 192, "Action");
        mc.addMovie("Love Actually", "Richard Curtis", 2003, true, 135, "Comedy");
        mc.addMovie("Harry Potter and the Prisoner of Azkaban", "Alfonso Cuarón", 2004, true, 142, "Adventure");
        // Act
        int actualSize = mc.searchMovie("z").size();
        // Assert
        int expectedSize = 1;
        assertEquals(expectedSize, actualSize);
    }

    @DisplayName("2 found")
    @Test
    void searchMovieTwo() {
        // Arrange
        MovieCollection mc = new MovieCollection();
        mc.addMovie("Avatar", "James Cameron", 2009, true, 162, "Action");
        mc.addMovie("Avatar: The Way of Water", "James Cameron", 2022, true, 192, "Action");
        mc.addMovie("Love Actually", "Richard Curtis", 2003, true, 135, "Comedy");
        mc.addMovie("Harry Potter and the Prisoner of Azkaban", "Alfonso Cuarón", 2004, true, 142, "Adventure");
        // Act
        int actualSize = mc.searchMovie("ava").size();
        // Assert
        int expectedSize = 2;
        assertEquals(expectedSize, actualSize);
    }
}