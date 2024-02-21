import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Controller controller = new Controller();

        while (true) {
            System.out.println("\nVelkommen til min filmsamling!\n1. Opret en film\n2. Afslut\n\nIndtast menuvalg:");
            int valg = input.nextInt();
            if (valg == 1) {
                System.out.println("\nIndtast venligst informationer om filmen uden at bruge mellemrum.\nTitle:");
                String title = input.next();
                System.out.println("Director:");
                String director = input.next();
                controller.addMovie(title, director);
                System.out.println("\nFilm tilføjet.");
            } else if (valg == 2) {
                System.out.println("\nAfslutter.");
                break;
            } else {
                System.out.println("Valg skal være 1 eller 2.");
            }
        }
    }
}