package ui;

import domain_model.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

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
        controller = new Controller();
        scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        movieAttributes = new String[]{"Filmtitel", "Filminstruktør", "Årstal", "Farvefilm", "Længde i minutter", "Genre"};

        // Hardcodede film til hurtig brugertest af søgefunktion
        controller.addMovie("Avatar", "James Cameron", 2009, true, 162, "Action");
        controller.addMovie("Avatar: The Way of Water", "James Cameron", 2022, true, 192, "Action");
        controller.addMovie("Love Actually", "Richard Curtis", 2003, true, 135, "Comedy");
        controller.addMovie("Harry Potter and the Prisoner of Azkaban", "Alfonso Cuarón", 2004, true, 142, "Adventure");
    }

    // Method
    public void startProgram() {
        System.out.println("\nVelkommen til min filmsamling!");
        mainMenu();
    }

    // Auxiliary methods
    private void mainMenu() {
        System.out.println("\n---Hovedmenu---");
        System.out.println("1. Opret en film");
        System.out.println("2. Søg efter film");
        System.out.println("3. Udskriv filmsamling");
        System.out.println("0. Afslut");
        int option = scanInt("Indtast menuvalg", 0, 3);
        switch (option) {
            case 1 -> addMovie();
            case 2 -> searchMovie();
            case 3 -> showMovieCollection();
            case 0 -> System.out.println("\nAfslutter...");
        }
    }

    private void addMovie() {
        System.out.println("\nIndtast informationer om filmen.");
        String title = scanString(movieAttributes[0]);
        String director = scanString(movieAttributes[1]);
        int yearCreated = scanInt(movieAttributes[2], 1800, 2100);
        boolean isInColor = scanBoolean(movieAttributes[3]);
        int lengthInMinutes = scanInt(movieAttributes[4], 0, Integer.MAX_VALUE);
        String genre = scanString(movieAttributes[5]);
        controller.addMovie(title, director, yearCreated, isInColor, lengthInMinutes, genre);
        System.out.println("Film tilføjet.");
        mainMenu();
    }

    private void searchMovie() {
        deletedMovies = new ArrayList<>();
        System.out.print("Indtast søgeord til filmtitlerne: ");
        searchWord = scanner.next();
        searchResults = controller.searchMovie(searchWord);
        if (searchResults.isEmpty()) {
            System.out.println("Ingen resultater med søgeordet '" + searchWord + "' i titlen.");
            mainMenu();
        } else {
            searchMenu();
        }
    }

    private void searchMenu() {
        System.out.println("\n---Resultater med søgeordet '" + searchWord + "' i titlen---");
        int option = 0;
        List<Integer> invalidOptions = new ArrayList<>();
        for (Movie movie : searchResults) {
            if (deletedMovies.contains(movie)) {
                System.out.println("\u001B[9m" + ++option + ". " + movie.getTitle() + "\u001B[0m");
                invalidOptions.add(option);
            } else {
                System.out.println(++option + ". " + movie.getTitle());
            }
        }
        System.out.println("0. Tilbage til hovedmenu");
        int chosenOption;
        while (true) {
            chosenOption = scanInt("Indtast menuvalg", 0, option);
            if (invalidOptions.contains(chosenOption)) {
                System.out.println("Den film er blevet slettet.");
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
        System.out.println("\n---Redigér eller slet '" + movie.getTitle() + "'---");
        for (int i = 1; i < 7; i++) {
            System.out.println(i + ". Redigér " + movieAttributes[i - 1].toLowerCase());
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
        String message = "Indtast ny " + movieAttributes[attributeIndex].toLowerCase();
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
        boolean delete = scanBoolean("Er du sikker på, du vil slette '" + movie.getTitle() + "' permanent?");
        if (delete) {
            System.out.println("'" + movie.getTitle() + "' blev slettet fra filmsamlingen.");
            controller.deleteMovie(movie);
            deletedMovies.add(movie);
            searchMenu();
        } else {
            System.out.println("'" + movie.getTitle() + "' blev ikke slettet fra filmsamlingen.");
            System.out.println("\n---Valgt film---");
            editMenu(movie);
        }
    }

    private void showMovie(Movie movie) {
        String[] specificMovieAttributes = {movie.getTitle(), movie.getDirector(), "" + movie.getYearCreated(), movie.getIsInColor() ? "Ja" : "Nej", "" + movie.getLengthInMinutes(), movie.getGenre()};
        for (int i = 0; i < 6; i++) {
            System.out.println(movieAttributes[i] + ": " + specificMovieAttributes[i]);
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
        System.out.print(message + ": ");
        try {
            int number = scanner.nextInt();
            if (number >= lowerBound && number <= upperBound) {
                return number;
            } else {
                if (lowerBound == Integer.MIN_VALUE) {
                    System.out.println("Indtastede var ikke under " + upperBound + ". Prøv igen.");
                } else if (upperBound == Integer.MAX_VALUE) {
                    System.out.println("Indtastede var ikke over " + lowerBound + ". Prøv igen.");
                } else {
                    System.out.println("Indtastede var ikke mellem " + lowerBound + " og " + upperBound + ". Prøv igen.");
                }
            }
        } catch (InputMismatchException ime) {
            scanner.nextLine();
            System.out.println("Indtastede var ikke heltal. Prøv igen.");
        }
        return scanInt(message, lowerBound, upperBound);
    }

    private boolean scanBoolean(String message) {
        System.out.print(message + " (ja/nej): ");
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
        System.out.print(message + ": ");
        return scanner.next();
    }
}