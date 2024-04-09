package data_source;

import domain_model.Movie;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
    private static final File file = new File("src/data_source/FilmData.csv");

    public static ArrayList<Movie> loadMovies(){
        Scanner sc;

        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        ArrayList<Movie> movies = new ArrayList<>();

        while (sc.hasNext()) {
            String line = sc.nextLine();
            String[] attributes = line.split(";");

            Movie movie = new Movie(
                    attributes[0],
                    attributes[1],
                    Integer.parseInt(attributes[2]),
                    Boolean.parseBoolean(attributes[3]),
                    Integer.parseInt(attributes[4]),
                    attributes[5]
            );

            movies.add(movie);
        }
        sc.close();
        return movies;
    }

    public static void saveMovies(ArrayList<Movie> movies){
        PrintStream output;
        try {
            output = new PrintStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (Movie movie : movies){
            String[] attributes = {
                movie.getTitle(),
                movie.getDirector(),
                movie.getYearCreated() + "",
                movie.getIsInColor() + "",
                movie.getLengthInMinutes() + "",
            };
            for (String attribute : attributes){
                output.print(attribute + ";");
            }
            output.println(movie.getGenre());
        }
        output.close();
    }
}
