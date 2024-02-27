import java.util.ArrayList;

public class MovieCollection {

    // Attribute
    private ArrayList<Movie> movieList;

    // Constructor
    public MovieCollection() {
        movieList = new ArrayList<>();
    }

    // Methods
    public void addMovie(String title, String director, int yearCreated, boolean isInColor, double lengthInMinutes, String genre) {
        movieList.add(new Movie(title, director, yearCreated, isInColor, lengthInMinutes, genre));
    }
    public ArrayList<Integer> searchMovie(String searchTitle) {
        ArrayList<Integer> indexes = new ArrayList<>(); // Laver en liste, der kan indeholde tal, til indekser
        for (Movie movie : movieList) { // Gennemgår movieList
            if (movie.getTitle().toLowerCase().contains(searchTitle.toLowerCase())) { // Ser om en films titel indeholder søgeordet
                indexes.add(movieList.indexOf(movie)); // Tilføjer filmens indeks i movieList til listen over indekser
            }
        }
        return indexes; // Returnerer listen over indekser i movieList, der indeholder søgeordet i titlen, som kan bruges i metoderne under
    }
    public String getMovieTitle(int index) {
        // Her kan et indeks fra en søgning bruges til at finde en bestemt film
        return movieList.get(index).getTitle();
    }
    public String getMovie(int index) {
        // Her kan et indeks fra en søgning bruges til at finde en bestemt film
        return movieList.get(index).toString();
    }
    public void editMovie(int index, int property, String input) {
        // Her kan et indeks fra en søgning bruges til at finde en bestemt film
        // property er nummeret (1-6) på den attribut, der skal ændres (se nederst i Movie-klassen)
        // input er hvad attributten skal ændres til
        switch (property) {
            case 1 -> movieList.get(index).setTitle(input);
            case 2 -> movieList.get(index).setDirector(input);
            case 3 -> movieList.get(index).setYearCreated(Integer.parseInt(input)); // husker at konvertere til int
            case 4 -> movieList.get(index).setIsInColor(input.equalsIgnoreCase("yes")); // husker at konvertere til boolean
            case 5 -> movieList.get(index).setLengthInMinutes(Double.parseDouble(input)); // husker at konvertere til double
            case 6 -> movieList.get(index).setGenre(input);
        }
    }

    // Object method
    @Override
    public String toString() {
        String returnString = "---MovieCollection begins---\n";
        for (Movie movie : movieList) {
            returnString += "\n" + movie + "\n";
        }
        return returnString + "\n---MovieCollection ends---";
    }
}