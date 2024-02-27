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
    public ArrayList<Integer> searchMovie(String searchTitle) {
        ArrayList<Integer> indexes = new ArrayList<>();
        for(Movie movie : movieList) {
            if(movie.getTitle().toLowerCase().contains(searchTitle.toLowerCase())) {
                indexes.add(movieList.indexOf(movie));
            }
        }
        return indexes;
    }
    public String getMovieTitle(int index) {
        return movieList.get(index).getTitle();
    }
    public String getMovie(int index) {
        return movieList.get(index).toString();
    }
    public void editMovie(int index, int property, String input) {
        switch (property) {
            case 1 -> movieList.get(index).setTitle(input);
            case 2 -> movieList.get(index).setDirector(input);
            case 3 -> movieList.get(index).setYearCreated(Integer.parseInt(input));
            case 4 -> movieList.get(index).setIsInColor(input.equalsIgnoreCase("yes"));
            case 5 -> movieList.get(index).setLengthInMinutes(Double.parseDouble(input));
            case 6 -> movieList.get(index).setGenre(input);
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