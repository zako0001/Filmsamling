package domain_model;

import java.util.Comparator;

public class Movie {

    // Attributes
    private String title;
    private String director;
    private int yearCreated;
    private boolean isInColor;
    private int lengthInMinutes;
    private String genre;

    // Comparators
    public static final Comparator<Movie> TITLE_ORDER = Comparator.comparing(Movie::getTitle, String.CASE_INSENSITIVE_ORDER);
    public static final Comparator<Movie> DIRECTOR_ORDER = Comparator.comparing(Movie::getDirector, String.CASE_INSENSITIVE_ORDER);
    public static final Comparator<Movie> YEAR_CREATED_ORDER = Comparator.comparing(Movie::getYearCreated);
    public static final Comparator<Movie> IS_IN_COLOR_ORDER = Comparator.comparing(Movie::getIsInColor);
    public static final Comparator<Movie> LENGTH_IN_MINUTES_ORDER = Comparator.comparing(Movie::getLengthInMinutes);
    public static final Comparator<Movie> GENRE_ORDER = Comparator.comparing(Movie::getGenre, String.CASE_INSENSITIVE_ORDER);

    // Constructor
    public Movie(String title, String director, int yearCreated, boolean isInColor, int lengthInMinutes, String genre) {

        this.title = title;
        this.director = director;
        this.yearCreated = yearCreated;
        this.isInColor = isInColor;
        this.lengthInMinutes = lengthInMinutes;
        this.genre = genre;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public int getYearCreated() {
        return yearCreated;
    }

    public boolean getIsInColor() {
        return isInColor;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public String getGenre() {
        return genre;
    }

    // Setters
    protected void setTitle(String title) {
        this.title = title;
    }

    protected void setDirector(String director) {
        this.director = director;
    }

    protected void setYearCreated(int yearCreated) {
        this.yearCreated = yearCreated;
    }

    protected void setIsInColor(boolean isInColor) {
        this.isInColor = isInColor;
    }

    protected void setLengthInMinutes(int lengthInMinutes) {
        this.lengthInMinutes = lengthInMinutes;
    }

    protected void setGenre(String genre) {
        this.genre = genre;
    }
}