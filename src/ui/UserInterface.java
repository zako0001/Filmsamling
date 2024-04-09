package ui;

import domain_model.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import static java.lang.StringTemplate.STR;

public class UserInterface {

    // Attributes
    private final Controller controller;
    private final Scanner scanner;
    private final String[] movieAttributes;
    private String searchWord;
    private List<Movie> searchResults;
    private List<Movie> deletedMovies;

    // Constructor
    public UserInterface() {

        // Setting initial attributes
        controller = new Controller();
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        movieAttributes = new String[]{"Filmtitel", "Filminstruktør", "Årstal", "Farvefilm", "Længde i minutter", "Genre"};
    }

    // Method
    public void startProgram() {
        System.out.println("\nVelkommen til min filmsamling!");
        controller.loadFromFile();
        mainMenu();
        controller.saveToFile();
    }

    // Auxiliary methods
    private void mainMenu() {

        System.out.println("""
            
            ---Hovedmenu---
            1. Opret en film
            2. Søg efter film
            3. Udskriv filmsamling
            0. Gem og afslut
            """);

        int option = scanInt("Indtast menuvalg", 0, 3);

        switch (option) {
            case 1 -> addMovie();
            case 2 -> searchMovie();
            case 3 -> showMovieCollection();
            case 0 -> System.out.println("\nDin samling er gemt. Afslutter...");
        }
    }

    private void addMovie() {

        controller.addMovie(
            scanString(movieAttributes[0]),
            scanString(movieAttributes[1]),
            scanInt(movieAttributes[2], 1800, 2100),
            scanBoolean(movieAttributes[3]),
            scanInt(movieAttributes[4], 0, Integer.MAX_VALUE),
            scanString(movieAttributes[5])
        );

        System.out.println("Film tilføjet.");
        mainMenu();
    }

    private void searchMovie() {

        System.out.print("Indtast søgeord til filmtitlerne: ");
        searchWord = scanner.next();
        searchResults = controller.searchMovie(searchWord);
        deletedMovies = new ArrayList<>();

        if (searchResults.isEmpty()) {
            System.out.println(STR."Ingen resultater med søgeordet '\{searchWord}' i titlen.");
            mainMenu();
        } else {
            searchMenu();
        }
    }

    private void searchMenu() {

        System.out.println(STR."\n---Resultater med søgeordet '\{searchWord}' i titlen---");
        int option = 0;
        List<Integer> invalidOptions = new ArrayList<>();

        for (Movie movie : searchResults) {

            if (deletedMovies.contains(movie)) {
                System.out.println(STR."\u001B[9m\{++option}. \{movie.getTitle()}\u001B[0m");
                invalidOptions.add(option);
            } else {
                System.out.println(STR."\{++option}. \{movie.getTitle()}");
            }
        }

        System.out.println("0. Tilbage til hovedmenu");
        int chosenOption;

        while (true) {

            chosenOption = scanInt("Indtast menuvalg", 0, option);
            if (invalidOptions.contains(chosenOption)) {
                System.out.println("Den film er blevet slettet. Prøv igen.");
            } else {
                break;
            }
        }

        if (chosenOption == 0) {
            mainMenu();
        } else {
            Movie chosenMovie = searchResults.get(chosenOption - 1);
            System.out.println("\n---Valgt film---");
            editMenu(chosenMovie);
        }
    }

    private void editMenu(Movie movie) {

        showMovie(movie);
        System.out.println(STR."\n---Redigér eller slet '\{movie.getTitle()}'---");

        for (int i = 1; i < 7; i++) {
            System.out.println(STR."\{i}. Redigér \{movieAttributes[i - 1].toLowerCase()}");
        }

        System.out.println("7. Slet filmen");
        System.out.println("0. Tilbage til søgeresultater");
        int choice = scanInt("Indtast menuvalg", 0, 7);

        if (choice == 0) {
            searchMenu();
        } else if (choice == 7) {
            deleteMovie(movie);
        } else {
            editMovie(choice - 1, movie);
        }
    }

    private void editMovie(int attributeIndex, Movie movie) {

        String message = STR."Indtast ny \{movieAttributes[attributeIndex].toLowerCase()}";

        switch (attributeIndex) {
            case 0 -> movie.setTitle(scanString(message));
            case 1 -> movie.setDirector(scanString(message));
            case 2 -> movie.setYearCreated(scanInt(message, 1800, 2100));
            case 3 -> movie.setIsInColor(scanBoolean(message));
            case 4 -> movie.setLengthInMinutes(scanInt(message, 0, Integer.MAX_VALUE));
            case 5 -> movie.setGenre(scanString(message));
        }

        System.out.println("\n---Film ændret til---");
        editMenu(movie);
    }

    private void deleteMovie(Movie movie) {

        boolean delete = scanBoolean(STR."Er du sikker på, du vil slette '\{movie.getTitle()}' permanent?");

        if (delete) {
            System.out.println(STR."'\{movie.getTitle()}' blev slettet fra filmsamlingen.");
            controller.deleteMovie(movie);
            deletedMovies.add(movie);
            searchMenu();
        } else {
            System.out.println(STR."'\{movie.getTitle()}' blev ikke slettet fra filmsamlingen.");
            System.out.println("\n---Valgt film---");
            editMenu(movie);
        }
    }

    private void showMovie(Movie movie) {

        String[] specificMovieAttributes = {movie.getTitle(), movie.getDirector(), "" + movie.getYearCreated(), movie.getIsInColor() ? "Ja" : "Nej", "" + movie.getLengthInMinutes(), movie.getGenre()};

        for (int i = 0; i < 6; i++) {
            System.out.println(STR."\{movieAttributes[i]}: \{specificMovieAttributes[i]}");
        }
    }

    private void showMovieCollection() {

        System.out.print("\n------Liste af film------");

        for (Movie movie : controller.searchMovie("")) {
            System.out.println();
            showMovie(movie);
        }

        mainMenu();
    }

    private int scanInt(String message, int lowerBound, int upperBound) {

        System.out.print(STR."\{message}: ");

        try {
            int number = scanner.nextInt();

            if (number >= lowerBound && number <= upperBound) {
                return number;
            } else {
                System.out.println("Indtastede var ikke ");

                if (lowerBound == Integer.MIN_VALUE) {
                    System.out.println(STR."under \{upperBound}");
                } else if (upperBound == Integer.MAX_VALUE) {
                    System.out.println(STR."over \{lowerBound}");
                } else {
                    System.out.println(STR."mellem \{lowerBound} og \{upperBound}");
                }
            }
        } catch (InputMismatchException ime) {
            scanner.nextLine();
            System.out.println("Indtastede var ikke heltal");
        }

        System.out.println(". Prøv igen.");
        return scanInt(message, lowerBound, upperBound);
    }

    private boolean scanBoolean(String message) {

        System.out.print(STR."\{message} (ja/nej): ");
        String answer = scanner.next();

        if (answer.equalsIgnoreCase("ja")) {
            return true;
        } else if (answer.equalsIgnoreCase("nej")) {
            return false;
        }

        System.out.println("Indtastede var ikke 'ja' eller 'nej'. Prøv igen.");
        return scanBoolean(message);
    }

    private String scanString(String message) {

        System.out.print(STR."\{message}: ");
        return scanner.next();
    }
}