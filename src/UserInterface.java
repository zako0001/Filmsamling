import java.util.Scanner;

public class UserInterface {

    // Attributes
    private Controller controller;
    private Scanner input;
    private boolean running;

    // Constructor
    public UserInterface() {
        controller = new Controller();
        input = new Scanner(System.in);
        input.useDelimiter("\n"); // Forhindrer bøvl med mellemrum
        running = false;
    }

    // Main menu method
    public void startProgram() {
        running = true;
        while (running) {
            System.out.println( // Præsenterer menuen
                "\nVelkommen til min filmsamling!\n" +
                "1. Opret en film\n" +
                "2. Søg efter film\n" +
                "3. Udskriv filmsamling\n" +
                "4. Afslut\n" +
                "\nIndtast menuvalg:"
            );
            switch (input.nextInt()) { // Henter menuvalg
                case 1 -> addMovie();
                case 2 -> searchMovie();
                case 3 -> showMovieCollection();
                case 4 -> stopProgram();
                default -> System.out.println("Menuvalg skal være \"1\", \"2\", \"3\" eller \"4\"."); // Hvis der tastes ugyldigt tal
            }
        }
    }

    // Menu option methods
    private void addMovie() {
        System.out.println("\nIndtast venligst informationer om filmen.\nTitle:");
        String title = input.next();
        System.out.println("Director:");
        String director = input.next();
        System.out.println("Year created:");
        int yearCreated = input.nextInt();
        System.out.println("Is in color (yes/no):");
        boolean isInColor = input.next().equalsIgnoreCase("yes");
        System.out.println("Length in minutes:");
        double lengthInMinutes = input.nextDouble();
        System.out.println("Genre:");
        String genre = input.next();
        controller.addMovie(title, director, yearCreated, isInColor, lengthInMinutes, genre);
        System.out.println("\nFilm tilføjet.");
    }
    private void searchMovie() {
        System.out.println("\nIndtast søgeord til filmtitlerne:");
        System.out.println("\n" + controller.searchMovie(input.next()));
    }
    private void showMovieCollection() {
        System.out.println("\n" + controller.showMovieCollection());
    }
    private void stopProgram() {
        System.out.println("\nAfslutter...");
        running = false;
    }
}
