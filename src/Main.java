import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Controller controller = new Controller();
        Scanner input = new Scanner(System.in);
        input.useDelimiter("\n"); // Forhindrer bøvl med mellemrum

        boolean run = true;
        while (run) {

            // Præsenterer menuen
            System.out.println(
                "\nVelkommen til min filmsamling!\n" +
                "1. Opret en film\n" +
                "2. Søg efter film\n" +
                "3. Udskriv filmsamling\n" +
                "4. Afslut\n" +
                "\nIndtast menuvalg:"
            );
            switch (input.nextInt()) { // Henter menuvalg

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

                case 2: // Søger efter en film ud fra titlen
                    System.out.println("\nIndtast søgeord til filmtitlerne:");
                    System.out.println("\n" + controller.searchMovie(input.next()));
                    break;

                case 3: // Udskriver filmsamlingen som tekst i terminalen
                    System.out.println("\n" + controller.getMovieCollectionAsString());
                    break;

                case 4: // Afslutter programmet
                    System.out.println("\nAfslutter...");
                    run = false;
                    break;

                default: // Hvis brugeren skriver ugyldigt menuvalg
                    System.out.println("Menuvalg skal være \"1\", \"2\", \"3\" eller \"4\".");
            }
        }
    }
}