import java.util.ArrayList;
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

        // Hardcodede film
        controller.addMovie("Avatar", "James Cameron", 2009, true, 162, "Action");
        controller.addMovie("Avatar: The Way of Water", "James Cameron", 2022, true, 192, "Action");
        controller.addMovie("Love Actually", "Richard Curtis", 2003, true, 135, "Comedy");
        controller.addMovie("Harry Potter and the Prisoner of Azkaban", "Alfonso Cuarón", 2004, true, 142, "Adventure");
    }

    // Main menu method
    public void startProgram() {
        running = true;
        System.out.println("\nVelkommen til min filmsamling!");
        while (running) {
            System.out.println( // Præsenterer menuen
                "\n---Hovedmenu---\n" +
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

    // Main menu option methods
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
        String searchTitle = input.next();
        ArrayList<Integer> indexes = controller.searchMovie(searchTitle);
        if(indexes.isEmpty()) {
            System.out.println("No match for \"" + searchTitle + "\".");
        }
        else {
            while(true) {
                System.out.println("\n---Movies containing \"" + searchTitle + "\" in title---");
                int option = 0;
                for(int index : indexes) {
                    System.out.println(++option + ". " + controller.getMovieTitle(index));
                }
                System.out.println("0. Tilbage til hovedmenu\n\nIndtast valg af film:");
                int chosenOption = input.nextInt();
                if(chosenOption > 0 && chosenOption <= option) {
                    int chosenFilm = indexes.get(chosenOption - 1);
                    System.out.println("\n---Valgte film---\n" + controller.getMovie(chosenFilm) + "\n0. Tilbage til søgeresultater\n\nVil du ændre filmen? Indtast valgmulighed:");
                    int editOption = input.nextInt();
                    if(editOption > 0 && editOption < 6) {
                        System.out.println("Indtast ny information:");
                        controller.editMovie(chosenFilm, editOption, input.next());
                        System.out.println("\n---Film ændret til---\n" + controller.getMovie(chosenFilm));
                    }
                }
                else {
                    break;
                }
            }
        }
    }
    private void showMovieCollection() {
        System.out.println("\n" + controller.showMovieCollection());
    }
    private void stopProgram() {
        System.out.println("\nAfslutter...");
        running = false;
    }
}
