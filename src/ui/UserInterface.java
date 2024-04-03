package ui;

import domain_model.*;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UserInterface {

    // Attributes
    private final Controller controller;
    private final Scanner scanner;
    private boolean running;

    // Constructor
    public UserInterface() {
        controller = new Controller();
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n"); // Forhindrer bøvl med mellemrum.
        running = false;

        // Hardcodede film til hurtig brugertest af søgefunktion
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
            System.out.println("\n---Hovedmenu---");
            System.out.println("1. Opret en film");
            System.out.println("2. Søg efter film");
            System.out.println("3. Udskriv filmsamling");
            System.out.println("4. Afslut");
            System.out.print("\nIndtast menuvalg: ");
            switch (scanIntSafely()) { // Menuvalg indtastes her.
                case 1 -> addMovie();
                case 2 -> searchMovie(); // Her kan fundne film ændres.
                case 3 -> showMovieCollection();
                case 4 -> stopProgram();
                default -> System.out.println("Menuvalg skal være '1', '2', '3' eller '4'."); // Hvis der tastes ugyldigt tal.
            }
        }
    }

    // Main menu option methods (private da de bare er hjælpemetoder)
    private void addMovie() {
        System.out.println("\nIndtast venligst informationer om filmen.");
        System.out.println("Title:");
        String title = scanner.next();
        System.out.println("Director:");
        String director = scanner.next();
        System.out.println("Year created:");
        int yearCreated = scanIntSafely();
        System.out.println("Is in color (yes/no):");
        boolean isInColor = scanner.next().equalsIgnoreCase("yes");
        System.out.println("Length in minutes:");
        int lengthInMinutes = scanIntSafely();
        System.out.println("Genre:");
        String genre = scanner.next();
        controller.addMovie(title, director, yearCreated, isInColor, lengthInMinutes, genre);
        System.out.println("\nFilm tilføjet.");
    }

    private void searchMovie() {
        System.out.println("\nIndtast søgeord til filmtitlerne:");
        String searchTitle = scanner.next(); // Her er søgeordet.
        List<Movie> movies = controller.searchMovie(searchTitle); // Her er listen over de film, der har søgeordet i titlen.
        if (movies.isEmpty()) { // Tjekker om listen er tom, for så var der ingen matches på søgeordet.
            System.out.println("No match for '" + searchTitle + "'.");
        } else { // Hvis der er matches, skal de vises i en resultatmenu, hvor man kan vælge mellem dem.
            while (true) { // While gør at vi kan komme tilbage til denne resultatmenu.
                System.out.println("\n---Movies containing '" + searchTitle + "' in title---"); // Resultatmenuens overskrift.
                int option = 0;
                for (Movie movie : movies) { // Vi skal bygge resultatmenuen ud fra, hvor mange matches der er, så her går vi gennem dem.
                    System.out.println(++option + ". " + movie.getTitle()); // Her bliver udskrevet et tal (valgmulighed) og en filmtitel.
                    // ++ før variabelnavnet betyder, at variablen bliver 1 højere inden den læses.
                }
                System.out.println("0. Tilbage til hovedmenu");
                System.out.println("\nIndtast valg af film:");
                int chosenOption = scanIntSafely();
                if (chosenOption > 0 && chosenOption <= option) { // Hvis en af filmene blev valgt (vi kan komme tilbage til resultatmenuen).
                    Movie chosenFilm = movies.get(chosenOption - 1); // -1 skyldes at valgmulighederne starter fra 1 og lister starter fra 0, så filmene vil være 1 lavere i listen.
                    // Der skal nu laves en filmændringsmenu ud fra den valgte film (se nederst i domain_model.Movie-klassen).
                    System.out.println("\n---Valgte film---");
                    System.out.println("1. Title: " + chosenFilm.getTitle());
                    System.out.println("2. Director: " + chosenFilm.getDirector());
                    System.out.println("3. Year created: " + chosenFilm.getYearCreated());
                    System.out.println("4. Is in color: " + (chosenFilm.getIsInColor() ? "yes" : "no"));
                    System.out.println("5. Length in minutes: " + chosenFilm.getLengthInMinutes());
                    System.out.println("6. Genre: " + chosenFilm.getGenre());
                    System.out.println("7. Slet filmen");
                    System.out.println("0. Tilbage til søgeresultater");
                    System.out.println("\nVil du ændre filmen?");
                    System.out.print("Indtast valgmulighed: ");
                    int editOption = scanIntSafely();
                    if (editOption > 0 && editOption < 7) {
                        System.out.print("Indtast ny information: ");
                        switch (editOption) {
                            case 1 -> chosenFilm.setTitle(scanner.next());
                            case 2 -> chosenFilm.setDirector(scanner.next());
                            case 3 -> chosenFilm.setYearCreated(scanIntSafely());
                            case 4 -> chosenFilm.setIsInColor(scanner.next().equals("yes"));
                            case 5 -> chosenFilm.setLengthInMinutes(scanIntSafely());
                            case 6 -> chosenFilm.setGenre(scanner.next());
                        }
                        System.out.println("\n---Film ændret til---");
                        showMovie(chosenFilm);
                    } else if (editOption == 7) {
                        System.out.println("Er du sikker på, at du vil slette '" + chosenFilm.getTitle() + "' permanent (ja/nej)?");
                        if (scanner.next().equalsIgnoreCase("ja")) {
                            System.out.println("'" + chosenFilm.getTitle() + "' er slettet fra filmsamlingen.");
                            controller.deleteMovie(chosenFilm);
                            break; // Bryder ud, fordi resultatmenuen indeholder en nu slettet film og fordi indekser kan være flyttet.
                        } else {
                            System.out.println("'" + chosenFilm.getTitle() + "' blev ikke slettet fra filmsamlingen.");
                        }
                    }
                }
                else { // Hvis ingen af filmene blev valgt
                    break; // Bryder ud af while-loopet og dermed ud af resultatmenuen.
                }
            }
        }
    }

    private void showMovie(Movie movie) {
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Director: " + movie.getDirector());
        System.out.println("Year created: " + movie.getYearCreated());
        System.out.println("Is in color: " + (movie.getIsInColor() ? "yes" : "no"));
        System.out.println("Length in minutes: " + movie.getLengthInMinutes());
        System.out.println("Genre: " + movie.getGenre());
    }

    private void showMovieCollection() {
        System.out.println("\n---domain_model.MovieCollection begins---");
        for (Movie movie : controller.searchMovie("")) {
            System.out.println();
            showMovie(movie);
        }
        System.out.println("\n---domain_model.MovieCollection ends---");
    }

    private void stopProgram() {
        System.out.println("\nAfslutter...");
        running = false; // Stopper while-loopet i startProgram-metoden og grunden til at running er attribute i stedet for lokal variabel i startProgram (pga. scope).
    }

    private int scanIntSafely() {
        try {
            return scanner.nextInt();
        }
        catch (InputMismatchException ime) {
            scanner.nextLine(); // Scanneren skal lige forstå, at den nu skal være klar til at læse på en ny linje
            System.out.println("Indtastede var ikke heltal. Prøv igen.");
            return scanIntSafely(); // Rekursion: Metoden kalder sig selv, og starter dermed forfra med et nyt try!
        }
    }
}
