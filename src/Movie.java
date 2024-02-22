public class Movie {

    // Attributes
    private String title;
    private String director;
    private int yearCreated;
    private boolean isInColor;
    private double lengthInMinutes;
    private String genre;

    // Constructor
    public Movie(String title, String director, int yearCreated, boolean isInColor, double lengthInMinutes, String genre) {
        this.title = title;
        this.director = director;
        this.yearCreated = yearCreated;
        this.isInColor = isInColor;
        this.lengthInMinutes = lengthInMinutes;
        this.genre = genre;
    }

    // Getters
    public String getTitle() {return title;}
    public String getDirector() {return director;}
    public int getYearCreated() {return yearCreated;}
    public boolean getIsInColor() {return isInColor;}
    public double getLengthInMinutes() {return lengthInMinutes;}
    public String getGenre() {return genre;}
}