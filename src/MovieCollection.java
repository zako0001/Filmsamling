import java.util.ArrayList;

public class MovieCollection {

    // Attribute
    private ArrayList<Movie> movieCollectionList;

    // Constructor
    public MovieCollection() {
        movieCollectionList = new ArrayList<>();
    }

    // Methods
    public void addMovie(String title, String director, int yearCreated, boolean isInColor, double lengthInMinutes, String genre) {
        movieCollectionList.add(new Movie(title, director, yearCreated, isInColor, lengthInMinutes, genre));
    }
    public ArrayList<Integer> searchMovie(String searchTitle) {
        ArrayList<Integer> indexes = new ArrayList<>(); // Laver en liste, der kan indeholde tal, til indekser
        for (Movie movie : movieCollectionList) { // Gennemgår movieCollectionList
            if (movie.getTitle().toLowerCase().contains(searchTitle.toLowerCase())) { // Ser om en films titel indeholder søgeordet
                indexes.add(movieCollectionList.indexOf(movie)); // Tilføjer filmens indeks i movieList til listen over indekser
            }
        }
        return indexes; // Returnerer listen over indekser i movieCollectionList, der indeholder søgeordet i titlen, som kan bruges i metoderne under
    }
    public String getMovieTitle(int index) {
        // Her kan et indeks fra en søgning bruges til at finde en bestemt film
        return movieCollectionList.get(index).getTitle();
    }
    public String getMovie(int index) {
        // Her kan et indeks fra en søgning bruges til at finde en bestemt film
        return movieCollectionList.get(index).toString();
    }
    public void editMovie(int index, int property, String input) {
        // Her kan et indeks fra en søgning bruges til at finde en bestemt film
        // property er nummeret (1-6) på den attribut, der skal ændres (se nederst i Movie-klassen)
        // input er hvad attributten skal ændres til
        switch (property) {
            case 1 -> movieCollectionList.get(index).setTitle(input);
            case 2 -> movieCollectionList.get(index).setDirector(input);
            case 3 -> movieCollectionList.get(index).setYearCreated(Integer.parseInt(input)); // husker at konvertere til int
            case 4 -> movieCollectionList.get(index).setIsInColor(input.equalsIgnoreCase("yes")); // husker at konvertere til boolean
            case 5 -> movieCollectionList.get(index).setLengthInMinutes(Double.parseDouble(input)); // husker at konvertere til double
            case 6 -> movieCollectionList.get(index).setGenre(input);
        }
    }

    public void deleteMovie(int index) {
        // Her kan et indeks fra en søgning bruges til at finde en bestemt film
        movieCollectionList.remove(index);
    }

    // Object method
    @Override
    public String toString() {
        String returnString = "---MovieCollection begins---\n";
        for (Movie movie : movieCollectionList) {
            returnString += "\n" + movie + "\n";
        }
        return returnString + "\n---MovieCollection ends---";
    }

    // Test method (default access)
    ArrayList<Movie> getMovieCollectionList() {
        return movieCollectionList;
    }
}