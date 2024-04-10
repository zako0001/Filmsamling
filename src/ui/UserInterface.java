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
        movieAttributes = new String[] {
                "Filmtitel",
                "Filminstruktør",
                "Årstal",
                "Farvefilm",
                "Længde i minutter",
                "Genre"
        };
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
            case 0 -> System.out.println("\nMin filmsamling er gemt. Afslutter...");
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
        searchResults = new ArrayList<>(List.of(controller.searchMovie(searchWord)));
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

        System.out.println("0. Tilbage til hovedmenu\n");
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
            editMenu(chosenMovie, true);
        }
    }

    private void editMenu(Movie movie, boolean fromSearch) {

        showMovie(movie, false);
        System.out.println(STR."\n---Redigér eller slet '\{movie.getTitle()}'---");

        for (int i = 1; i < 7; i++) {
            System.out.println(STR."\{i}. Redigér \{movieAttributes[i - 1].toLowerCase()}");
        }

        System.out.println("7. Slet filmen");
        System.out.println(STR."0. Tilbage til \{fromSearch ? "søgeresultater" : "hovedmenu"}\n");
        int choice = scanInt("Indtast menuvalg", 0, 7);

        if (choice == 0) {
            if (fromSearch) {
                searchMenu();
            } else {
                mainMenu();
            }
        } else if (choice == 7) {
            deleteMovie(movie);
        } else {
            editMovie(choice - 1, movie, fromSearch);
        }
    }

    private void editMovie(int attributeIndex, Movie movie, boolean fromSearch) {

        String message = STR."Indtast ny \{movieAttributes[attributeIndex].toLowerCase()}";

        switch (attributeIndex) {
            case 0 -> controller.editMovie(movie, movie.getTitle(), scanString(message));
            case 1 -> controller.editMovie(movie, movie.getDirector(), scanString(message));
            case 2 -> controller.editMovie(movie, movie.getYearCreated(), scanInt(message, 1800, 2100));
            case 3 -> controller.editMovie(movie, movie.getIsInColor(), scanBoolean(message));
            case 4 -> controller.editMovie(movie, movie.getLengthInMinutes(), scanInt(message, 0, Integer.MAX_VALUE));
            case 5 -> controller.editMovie(movie, movie.getGenre(), scanString(message));
        }

        System.out.println("\n---Film ændret til---");
        editMenu(movie, fromSearch);
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
            editMenu(movie, false);
        }
    }

    private void showMovie(Movie movie, boolean indent) {

        String[] specificMovieAttributes = {
                movie.getTitle(),
                movie.getDirector(),
                movie.getYearCreated() + "",
                movie.getIsInColor() ? "Ja" : "Nej",
                movie.getLengthInMinutes() + "",
                movie.getGenre()
        };

        for (int i = 0; i < specificMovieAttributes.length; i++) {
            System.out.println(STR."\{indent ? "   " : ""}\{movieAttributes[i]}: \{specificMovieAttributes[i]}");
        }
    }

    private void showMovieCollection() {

        System.out.println("\nVælg hvilken attribut du ønsker at sortere din filmsamling efter:\n");
        int menuChoice = 0;
        for (String att : movieAttributes){
            System.out.print(++menuChoice + ": " + att);
            if (att.equals("Årstal")){
                System.out.print(" (nyeste først)");
            } else if (att.equals("Længde i minutter")) {
                System.out.print(" (korteste først)");
            } else if (att.equals("Farvefilm")) {
                System.out.print(" (farvefilm først)");
            }
            System.out.println();
        }
        System.out.println("0: Tilbage til hovedmenu");

        int menuChoiceForScanInt = scanInt("\nIndtast tal til valg af attribut", 0, 6);
        if (menuChoiceForScanInt == 0){
            mainMenu();
            return;
        }
        Movie[] movies = switch (menuChoiceForScanInt){
            case 1 -> controller.showMovieCollection("title");
            case 2 -> controller.showMovieCollection("director");
            case 3 -> controller.showMovieCollection("yearCreated");
            case 4 -> controller.showMovieCollection("isInColor");
            case 5 -> controller.showMovieCollection("lengthInMinutes");
            case 6 -> controller.showMovieCollection("genre");
            default -> new Movie[]{};
        };
        System.out.println();

        if (movies.length == 0) {
            System.out.println("Der er ingen film i filmsamlingen.");
            mainMenu();
        } else if (movies.length == 1) {
            System.out.println("---Eneste film i filmsamlingen---");
            editMenu(movies[0], false);
        } else {
            System.out.println("------Liste af film------");
            int number = 0;

            for (Movie movie : movies) {
                System.out.println(STR."\n   ---Film nr. \{++number}---");
                showMovie(movie, true);
            }

            System.out.println(STR."\n1. - \{number}. Vælg film nr.");
            System.out.println("0. Tilbage til hovedmenu\n");
            int choice = scanInt("Indtast menuvalg", 0, number);

            if (choice == 0) {
                mainMenu();
            } else {
                System.out.println("\n---Valgt film---");
                editMenu(movies[choice - 1], false);
            }
        }
    }

    private int scanInt(String message, int lowerBound, int upperBound) {

        System.out.print(STR."\{message}: ");

        try {
            int number = scanner.nextInt();

            if (number >= lowerBound && number <= upperBound) {
                return number;
            } else {
                System.out.print("Indtastede var ikke ");

                if (lowerBound == Integer.MIN_VALUE) {
                    System.out.print(STR."under \{upperBound}");
                } else if (upperBound == Integer.MAX_VALUE) {
                    System.out.print(STR."over \{lowerBound}");
                } else {
                    System.out.print(STR."mellem \{lowerBound} og \{upperBound}");
                }
            }
        } catch (InputMismatchException ime) {
            scanner.nextLine();
            System.out.print("Indtastede var ikke heltal");
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