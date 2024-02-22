import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Controller controller = new Controller();
        Scanner input = new Scanner(System.in);
        input.useDelimiter("\n"); // Forhindrer bøvl med mellemrum

        boolean run = true;
        while (run) {

            // Præsenterer menuen
            System.out.println("\nVelkommen til min filmsamling!\n1. Opret en film\n2. Udskriv filmsamling\n3. Afslut\n\nIndtast menuvalg:");
            switch (input.nextInt()) {

                case 1: // Opretter og tilføjer film
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
                    break;

                case 2: // Udskriver filmsamlingen som tekst i terminalen
                    System.out.println(controller.getMovieCollectionAsString());
                    break;

                case 3: // Afslutter programmet
                    System.out.println("\nAfslutter...");
                    run = false;
                    break;

                default: // Hvis brugeren skriver ugyldigt menuvalg
                    System.out.println("Valg skal være 1 eller 2.");
            }
        }
    }
}