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
    /* Ikke brugt
    public String getDirector() {return director;}
    public int getYearCreated() {return yearCreated;}
    public boolean getIsInColor() {return isInColor;}
    public double getLengthInMinutes() {return lengthInMinutes;}
    public String getGenre() {return genre;}
    */

    // Setters
    public void setTitle(String in) {title = in;}
    public void setDirector(String in) {director = in;}
    public void setYearCreated(int in) {yearCreated = in;}
    public void setIsInColor(boolean in) {isInColor = in;}
    public void setLengthInMinutes(double in) {lengthInMinutes = in;}
    public void setGenre(String in) {genre = in;}

    // Object method
    @Override
    public String toString() {
        return "1. Title: " + title +
            "\n2. Director: " + director +
            "\n3. Year created: " + yearCreated +
            "\n4. Is in color: " + (isInColor ? "yes" : "no") +
            "\n5. Length in minutes: " + lengthInMinutes +
            "\n6. Genre: " + genre;
        // Tallene giver numre til de forskellige attributes
    }
}