package ui;

import domain_model.*;

import java.util.*;

import static java.lang.StringTemplate.STR;

public class UserInterface {

    // Attributes
    private final Controller controller;
    private final Scanner scanner;
    private final String[] movieAttributes;
    private Movie[] shownMovies;
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
            case 3 -> showMenu();
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
                showMovieCollection();
            }
        } else if (choice == 7) {
            deleteMovie(movie, fromSearch);
        } else {
            editMovie(choice - 1, movie, fromSearch);
        }
    }

    private void editMovie(int attributeIndex, Movie movie, boolean fromSearch) {

        String name = movieAttributes[attributeIndex].toLowerCase();
        String descriptor = attributeIndex == 3 ? "om" : attributeIndex == 2 ? "nyt" : "ny";
        String message = STR."Indtast \{descriptor} \{name}";

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

    private void deleteMovie(Movie movie, boolean fromSearch) {

        boolean delete = scanBoolean(STR."Er du sikker på, du vil slette '\{movie.getTitle()}' permanent?");

        if (delete) {
            System.out.println(STR."'\{movie.getTitle()}' blev slettet fra filmsamlingen.");
            controller.deleteMovie(movie);
            deletedMovies.add(movie);
            if (fromSearch) {
                searchMenu();
            } else {
                showMovieCollection();
            }
        } else {
            System.out.println(STR."'\{movie.getTitle()}' blev ikke slettet fra filmsamlingen.");
            System.out.println("\n---Valgt film---");
            editMenu(movie, fromSearch);
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

        boolean b = deletedMovies.contains(movie);

        for (int i = 0; i < specificMovieAttributes.length; i++) {

            System.out.println(STR."\{indent ? "   " : ""}\{b ? "\u001B[9m" : ""}\{movieAttributes[i]}: \{specificMovieAttributes[i]}\{b ? "\u001B[0m" : ""}");
        }
    }

    private void showMovieCollection() {

        System.out.println("\n------Liste af film------");
        List<Integer> invalidOptions = new ArrayList<>();
        int option = 0;

        for (Movie movie : shownMovies) {

            if (deletedMovies.contains(movie)) {
                System.out.println(STR."\n   \u001B[9m---Film nr. \{++option}---\u001B[0m");
                invalidOptions.add(option);
            } else {
                System.out.println(STR."\n   ---Film nr. \{++option}---");
            }

            showMovie(movie, true);
        }

        System.out.println(STR."\n1. - \{option}. Vælg film nr.");
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
            System.out.println("\n---Valgt film---");
            editMenu(shownMovies[chosenOption - 1], false);
        }
    }

    private void showMenu() {

        deletedMovies = new ArrayList<>();
        shownMovies = controller.showMovieCollection(Movie.TITLE_ORDER);

        if (shownMovies.length == 0) {
            System.out.println("\nDer er ingen film i filmsamlingen.");
            mainMenu();
        } else if (shownMovies.length == 1) {
            System.out.println("\n---Eneste film i filmsamlingen---");
            editMenu(shownMovies[0], false);
        } else {
            if (scanBoolean("Skal filmsamlingen udskrives i en sorteret rækkefølge?")) {
                sortMenu();
            } else {
                showMovieCollection();
            }
        }
    }

    private void sortMenu() {

        String sortingOptions = STR."""

            ---Sortér efter---
            1. \{movieAttributes[0]}, alfabetisk rækkefølge
            2. \{movieAttributes[0]}, omvendt alfabetisk rækkefølge
            3. \{movieAttributes[1]}, alfabetisk rækkefølge
            4. \{movieAttributes[1]}, omvendt alfabetisk rækkefølge
            5. \{movieAttributes[2]}, ældste øverst
            6. \{movieAttributes[2]}, nyeste øverst
            7. \{movieAttributes[3]}, sort-hvid øverst
            8. \{movieAttributes[3]}, farvet øverst
            9. \{movieAttributes[4]}, korteste øverst
            10. \{movieAttributes[4]}, længste øverst
            11. \{movieAttributes[5]}, alfabetisk rækkefølge
            12. \{movieAttributes[5]}, omvendt alfabetisk rækkefølge
            0. Tilbage til hovedmenu
            """;

        System.out.println(sortingOptions);

        int choice = scanInt("Indtast menuvalg", 0, 12);

        if (choice == 0) {
            mainMenu();
            return;
        }

        Comparator<Movie> comparator = switch (choice) {
            case 2 -> Movie.TITLE_ORDER.reversed();
            case 3 -> Movie.DIRECTOR_ORDER;
            case 4 -> Movie.DIRECTOR_ORDER.reversed();
            case 5 -> Movie.YEAR_CREATED_ORDER;
            case 6 -> Movie.YEAR_CREATED_ORDER.reversed();
            case 7 -> Movie.IS_IN_COLOR_ORDER;
            case 8 -> Movie.IS_IN_COLOR_ORDER.reversed();
            case 9 -> Movie.LENGTH_IN_MINUTES_ORDER;
            case 10 -> Movie.LENGTH_IN_MINUTES_ORDER.reversed();
            case 11 -> Movie.GENRE_ORDER;
            case 12 -> Movie.GENRE_ORDER.reversed();
            default -> Movie.TITLE_ORDER;
        };

        if (scanBoolean("Skal rækkefølgen have en sekundær sortering?")) {
            System.out.println(sortingOptions);

            int choice2 = scanInt("Indtast menuvalg", 0, 12);

            if (choice2 == 0) {
                mainMenu();
                return;
            }

            Comparator<Movie> comparator2 = switch (choice2) {
                case 2 -> Movie.TITLE_ORDER.reversed();
                case 3 -> Movie.DIRECTOR_ORDER;
                case 4 -> Movie.DIRECTOR_ORDER.reversed();
                case 5 -> Movie.YEAR_CREATED_ORDER;
                case 6 -> Movie.YEAR_CREATED_ORDER.reversed();
                case 7 -> Movie.IS_IN_COLOR_ORDER;
                case 8 -> Movie.IS_IN_COLOR_ORDER.reversed();
                case 9 -> Movie.LENGTH_IN_MINUTES_ORDER;
                case 10 -> Movie.LENGTH_IN_MINUTES_ORDER.reversed();
                case 11 -> Movie.GENRE_ORDER;
                case 12 -> Movie.GENRE_ORDER.reversed();
                default -> Movie.TITLE_ORDER;
            };

            shownMovies = controller.showMovieCollection(comparator.thenComparing(comparator2));
        } else {
            shownMovies = controller.showMovieCollection(comparator);
        }
        showMovieCollection();
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