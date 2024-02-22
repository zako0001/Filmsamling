import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Controller controller = new Controller();
        Scanner input = new Scanner(System.in);
        input.useDelimiter("\n"); // Forhindrer bøvl med mellemrum

        while (true) {

            // Præsenterer menuen
            System.out.println("\nVelkommen til min filmsamling!\n1. Opret en film\n2. Afslut\n\nIndtast menuvalg:");
            int menuValg = input.nextInt();

            // Opretter og tilføjer film
            if (menuValg == 1) {
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

            // Afslutter programmet
            else if (menuValg == 2) {
                System.out.println("\nAfslutter.");
                break;
            }

            // Hvis brugeren skriver ugyldigt menuvalg.
            else {
                System.out.println("Valg skal være 1 eller 2.");
            }
        }
    }
}