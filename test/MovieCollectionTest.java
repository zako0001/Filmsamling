import domain_model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MovieCollectionTest {

    @DisplayName("0 added")
    @Test
    void addMovie0() {
        // Arrange
        MovieCollection mc = new MovieCollection();
        // Act
        int actualSize = mc.searchMovie("").length;
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
        int actualSize = mc.searchMovie("").length;
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
        int actualSize = mc.searchMovie("").length;
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
        int actualSize = mc.searchMovie("q").length;
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
        int actualSize = mc.searchMovie("z").length;
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
        int actualSize = mc.searchMovie("ava").length;
        // Assert
        int expectedSize = 2;
        assertEquals(expectedSize, actualSize);
    }

    @DisplayName("1 deleted")
    @Test
    void deleteMovieOne() {
        // Arrange
        MovieCollection mc = new MovieCollection();
        mc.addMovie("Avatar", "James Cameron", 2009, true, 162, "Action");
        mc.addMovie("Avatar: The Way of Water", "James Cameron", 2022, true, 192, "Action");
        mc.addMovie("Love Actually", "Richard Curtis", 2003, true, 135, "Comedy");
        mc.addMovie("Harry Potter and the Prisoner of Azkaban", "Alfonso Cuarón", 2004, true, 142, "Adventure");
        // Act
        mc.deleteMovie(mc.searchMovie("")[0]);
        // Assert
        int actualSize = mc.searchMovie("").length;
        int expectedSize = 3;
        assertEquals(expectedSize, actualSize);
    }

    @DisplayName("2 deleted")
    @Test
    void deleteMovieTwo() {
        // Arrange
        MovieCollection mc = new MovieCollection();
        mc.addMovie("Avatar", "James Cameron", 2009, true, 162, "Action");
        mc.addMovie("Avatar: The Way of Water", "James Cameron", 2022, true, 192, "Action");
        mc.addMovie("Love Actually", "Richard Curtis", 2003, true, 135, "Comedy");
        mc.addMovie("Harry Potter and the Prisoner of Azkaban", "Alfonso Cuarón", 2004, true, 142, "Adventure");
        // Act
        mc.deleteMovie(mc.searchMovie("")[0]);
        mc.deleteMovie(mc.searchMovie("")[0]);
        // Assert
        int actualSize = mc.searchMovie("").length;
        int expectedSize = 2;
        assertEquals(expectedSize, actualSize);
    }

    @DisplayName("0 edited")
    @Test
    void editMovieFail(){
        // Arrange
        MovieCollection mc = new MovieCollection();
        mc.addMovie("Avatar", "James Cameron", 2009, true, 162, "Action");
        mc.addMovie("Avatar: The Way of Water", "James Cameron", 2022, true, 192, "Action");
        mc.addMovie("Love Actually", "Richard Curtis", 2003, true, 135, "Comedy");
        mc.addMovie("Harry Potter and the Prisoner of Azkaban", "Alfonso Cuarón", 2004, true, 142, "Adventure");
        // Act
        mc.editMovie(mc.searchMovie("")[0], true, 2009);
        // Assert
    }

    @DisplayName("all attributes edited")
    @Test
    void editMovieAllAttributes(){
        // Arrange
        MovieCollection mc = new MovieCollection();
        mc.addMovie("Avatar", "James Cameron", 2009, true, 162, "Action");
        Movie movie = mc.searchMovie("")[0];
        // Act
        mc.editMovie(movie, movie.getTitle(), "TestTitle");
        mc.editMovie(movie, movie.getDirector(), "TestDirector" );
        mc.editMovie(movie, movie.getYearCreated(), 1234);
        mc.editMovie(movie, movie.getIsInColor(), false);
        mc.editMovie(movie, movie.getLengthInMinutes(), 4321);
        mc.editMovie(movie, movie.getGenre(), "TestGenre");
        // Assert
        assertEquals("TestTitle", movie.getTitle());
        assertEquals("TestDirector", movie.getDirector());
        assertEquals(1234, movie.getYearCreated());
        assertFalse(movie.getIsInColor());
        assertEquals(4321, movie.getLengthInMinutes());
        assertEquals("TestGenre", movie.getGenre());

    }
}