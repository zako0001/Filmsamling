import java.util.ArrayList;
import java.util.InputMismatchException;
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
            System.out.println( // Præsenterer menuen
                "\n---Hovedmenu---\n" +
                "1. Opret en film\n" +
                "2. Søg efter film\n" +
                "3. Udskriv filmsamling\n" +
                "4. Afslut\n" +
                "\nIndtast menuvalg:"
            );
            switch (scanIntSafely()) { // menuvalg indtastes her
                case 1 -> addMovie();
                case 2 -> searchMovie(); // Her kan fundne film ændres
                case 3 -> showMovieCollection();
                case 4 -> stopProgram();
                default -> System.out.println("Menuvalg skal være \"1\", \"2\", \"3\" eller \"4\"."); // Hvis der tastes ugyldigt tal
            }
        }
    }

    // Main menu option methods (private da de bare er hjælpemetoder)
    private void addMovie() {
        System.out.println("\nIndtast venligst informationer om filmen.\nTitle:");
        String title = input.next();
        System.out.println("Director:");
        String director = input.next(); // scannerens delimiter er ændret i constructoren, så director kan godt blive et fuldt navn med mellemrum
        System.out.println("Year created:");
        int yearCreated = scanIntSafely();
        System.out.println("Is in color (yes/no):");
        boolean isInColor = input.next().equalsIgnoreCase("yes");
        System.out.println("Length in minutes:");
        double lengthInMinutes = scanDoubleSafely();
        System.out.println("Genre:");
        String genre = input.next();
        controller.addMovie(title, director, yearCreated, isInColor, lengthInMinutes, genre);
        System.out.println("\nFilm tilføjet.");
    }
    private void searchMovie() {
        System.out.println("\nIndtast søgeord til filmtitlerne:");
        String searchTitle = input.next(); // Her er søgeordet
        ArrayList<Integer> indexes = controller.searchMovie(searchTitle); // Her er listen over indekser på de film, der har søgeordet i titlen
        if (indexes.isEmpty()) { // Tjekker om listen er tom, for så var der ingen matches på søgeordet
            System.out.println("No match for \"" + searchTitle + "\".");
        }
        else { // Hvis der er matches, skal de vises i en resultatmenu, hvor man kan vælge mellem dem
            while (true) { // While gør at vi kan komme tilbage til denne resultatmenu
                System.out.println("\n---Movies containing \"" + searchTitle + "\" in title---"); // Resultatmenuens overskrift
                int option = 0;
                for (int index : indexes) { // Vi skal bygge resultatmenuen ud fra, hvor mange matches der er, så her går vi gennem dem
                    System.out.println(++option + ". " + controller.getMovieTitle(index)); // Her bliver udskrevet et tal (valgmulighed) og en filmtitel
                    // ++ før variabelnavnet betyder at den bliver 1 højere inden den læses
                }
                System.out.println("0. Tilbage til hovedmenu\n\nIndtast valg af film:");
                int chosenOption = scanIntSafely();
                if (chosenOption > 0 && chosenOption <= option) { // Hvis en af filmene blev valgt (vi kan komme tilbage til resultatmenuen)
                    int chosenFilm = indexes.get(chosenOption - 1); // -1 skyldes at valgmulighederne starter fra 1 og lister starter fra 0, så filmene vil være 1 lavere i listen
                    // Der skal nu laves en filmændringsmenu ud fra den valgte film (se nederst i Movie-klassen)
                    System.out.println("\n---Valgte film---\n" + controller.getMovie(chosenFilm) + "\n7. Slet filmen\n0. Tilbage til søgeresultater\n\nVil du ændre filmen? Indtast valgmulighed:");
                    int editOption = scanIntSafely();
                    if (editOption > 0 && editOption <= 6) { // Hvis en attribut er valgt (6 er højeste nummer blandt Movies attributes)
                        System.out.println("Indtast ny information:");
                        try {
                            controller.editMovie(chosenFilm, editOption, input.next()); // Filmattributten ændres vba. filmens indeks, filmattributtens nummer og den nye værdi som String
                            System.out.println("\n---Film ændret til---\n" + controller.getMovie(chosenFilm));
                        }
                        catch (NumberFormatException nfe) {
                            input.nextLine();
                            System.out.println("Ændring fejlede: ikke et tal.");
                        }
                    }
                    else if (editOption == 7) {
                        System.out.println("Er du sikker på, at du vil slette \"" + controller.getMovieTitle(chosenFilm) + "\" permanent (ja/nej)?");
                        if (input.next().equalsIgnoreCase("ja")) {
                            System.out.println("\"" + controller.getMovieTitle(chosenFilm) + "\" er slettet fra filmsamlingen.");
                            controller.deleteMovie(chosenFilm); // skal være efter printet fordi indexer kan være flyttet
                            break; // bryder ud, fordi resultatmenuen indeholder en nu slettet film og fordi indekser kan være flyttet
                        }
                        else {
                            System.out.println("\"" + controller.getMovieTitle(chosenFilm) + "\" blev ikke slettet fra filmsamlingen.");
                        }
                    }
                    // Filmændringsmenuen slutter og vi går tilbage til resultatmenuen pga. while-loopet
                }
                else { // Hvis ingen af filmene blev valgt
                    break; // Bryder ud af while-loopet og dermed ud af resultatmenuen
                }
            }
        }
    }
    private void showMovieCollection() {
        System.out.println("\n" + controller.showMovieCollection());
    }
    private void stopProgram() {
        System.out.println("\nAfslutter...");
        running = false; // Stopper while-loopet i startProgram-metoden og grunden til at running er attribute i stedet for lokal variabel i startProgram (pga. scope)
    }
    private int scanIntSafely() {
        try {
            return input.nextInt();
        }
        catch (InputMismatchException ime) {
            input.nextLine(); // Scanneren skal lige forstå, at den nu skal være klar til at læse på en ny linje
            System.out.println("Indtastede var ikke heltal. Prøv igen.");
            return scanIntSafely(); // Rekursion: Metoden kalder sig selv, og starter dermed forfra med et nyt try!
        }
    }
    private Double scanDoubleSafely() { // For kommentarer se int-metoden ovenfor
        try {
            return input.nextDouble();
        }
        catch (InputMismatchException ime) {
            input.nextLine();
            System.out.println("Indtastede var ikke et heltal eller kommasepareret decimaltal. Prøv igen.");
            return scanDoubleSafely();
        }
    }
}
