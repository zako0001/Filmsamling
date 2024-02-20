import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        MovieCollection mc = new MovieCollection(5);
        Scanner input = new Scanner(System.in);

        System.out.println("Type in title:");
        String title = input.nextLine();

        System.out.println("Type in director:");
        String director = input.nextLine();

        mc.addMovie(title, director);
    }
}