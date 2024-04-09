package domain_model;

public class Movie {

    // Attributes
    private String title;
    private String director;
    private int yearCreated;
    private boolean isInColor;
    private int lengthInMinutes;
    private String genre;

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