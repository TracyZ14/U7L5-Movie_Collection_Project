import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a tital search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.print("Enter a cast member: ");
        String searchTerm = scanner.nextLine();
        searchTerm = searchTerm.toLowerCase();
        ArrayList<Movie> results = new ArrayList<Movie>();
        for(int i = 0; i < movies.size(); i++)
        {
            String movieCast = movies.get(i).getCast();
            movieCast = movieCast.toLowerCase();
            if(movieCast.contains(searchTerm))
            {
                results.add(movies.get(i));
            }
        }
        ArrayList<String> cast = new ArrayList<String>();
        for(int i = 0; i < results.size(); i++)
        {
            String[] castMembers = results.get(i).getCast().split("\\|");
            for(int j = 0; j < castMembers.length; j++)
            {
                String name = castMembers[j];
                name = name.toLowerCase();
                if(name.contains(searchTerm))
                {
                    cast.add(castMembers[j]);
                }
            }
        }
        for(int i = (cast.size() - 1); i >= 0; i--)
        {
            for(int j = 0; j < i; j++)
            {
                if(cast.get(i).equals(cast.get(j)))
                {
                    cast.remove(i);
                    j = i;
                }
            }
        }
        for(int i = 1; i < cast.size(); i++)
        {
            String tempCast = cast.get(i);
            while((i > 0) && (tempCast.compareTo(cast.get(i - 1)) < 0))
            {
                cast.set(i, cast.get(i - 1));
                i--;
            }
            cast.set(i, tempCast);
        }
        for(int i = 0; i < cast.size(); i++)
        {
            String castName = cast.get(i);
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + castName);
        }
        System.out.println("Which cast member would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        ArrayList<Movie> castMovie = new ArrayList<Movie>();
        String selectedCast = cast.get(choice - 1);
        for(int i = 0; i < movies.size(); i++)
        {
            if(movies.get(i).getCast().contains(selectedCast))
            {
                castMovie.add(movies.get(i));
            }
        }
        sortResults(castMovie);
        for(int i = 0; i < castMovie.size(); i++)
        {
            String title = castMovie.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + title);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice2 = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = castMovie.get(choice2 - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine();
        searchTerm = searchTerm.toLowerCase();
        ArrayList<Movie> results = new ArrayList<Movie>();
        for(int i = 0; i < movies.size(); i++)
        {
            String movieKeyword = movies.get(i).getKeywords();
            movieKeyword = movieKeyword.toLowerCase();
            if(movieKeyword.indexOf(searchTerm) != -1)
            {
                results.add(movies.get(i));
            }
        }
        sortResults(results);
        for(int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + title);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = results.get(choice - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres()
    {
        ArrayList<String> genresList = new ArrayList<String>();
        for(int i = 0; i < movies.size(); i++)
        {
            String movieGenres = movies.get(i).getGenres();
            genresList.add(movieGenres);
        }
        ArrayList<String> genre = new ArrayList<String>();
        for(int i = 0; i < genresList.size(); i++)
        {
            String[] genres = genresList.get(i).split("\\|");
            for(int j = 0; j < genres.length; j++)
            {
                if(!genre.contains(genres[j]))
                {
                    genre.add(genres[j]);
                }
            }
        }
        for(int i = 1; i < genre.size(); i++)
        {
            String tempGenre = genre.get(i);
            while((i > 0) && (tempGenre.compareTo(genre.get(i - 1)) < 0))
            {
                genre.set(i, genre.get(i - 1));
                i--;
            }
            genre.set(i, tempGenre);
        }
        System.out.println("List of genres:");
        for(int i = 0; i < genre.size(); i++)
        {
            String movieGenre = genre.get(i);
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + movieGenre);
        }
        System.out.println("Which genre would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        ArrayList<Movie> genreMovie = new ArrayList<Movie>();
        String selectedGenre = genre.get(choice - 1);
        for(int i = 0; i < movies.size(); i++)
        {
            if(movies.get(i).getGenres().contains(selectedGenre))
            {
                genreMovie.add(movies.get(i));
            }
        }
        sortResults(genreMovie);
        for(int i = 0; i < genreMovie.size(); i++)
        {
            String title = genreMovie.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + title);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice2 = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = genreMovie.get(choice2 - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated()
    {
        ArrayList<Movie> highestRated = new ArrayList<Movie>();
        ArrayList<Movie> moviesList = movies;
        for(int i = 0; i < 50; i++)
        {
            boolean addedMovie = false;
            double highestRating = 0.0;
            int indexOfMovie = 0;
            for(int j = 0; (j < moviesList.size()) && (!addedMovie); j++)
            {
                double rating = moviesList.get(j).getUserRating();
                if(rating > highestRating)
                {
                    highestRating = rating;
                    indexOfMovie = j;
                }
            }
            highestRated.add(moviesList.get(indexOfMovie));
            moviesList.remove(indexOfMovie);
        }
        System.out.println("List of top 50 rated movies:");
        for(int i = 0; i < highestRated.size(); i++)
        {
            String title = highestRated.get(i).getTitle();
            double rating = highestRated.get(i).getUserRating();
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + title + ": " + rating);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = highestRated.get(choice - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRevenue()
    {
        ArrayList<Movie> highestRevenue = new ArrayList<Movie>();
        ArrayList<Movie> moviesList = movies;
        for(int i = 0; i < 50; i++)
        {
            boolean addedMovie = false;
            int highestRevenueAmount = 0;
            int indexOfMovie = 0;
            for(int j = 0; (j < moviesList.size()) && (!addedMovie); j++)
            {
                int revenue = moviesList.get(j).getRevenue();
                if(revenue > highestRevenueAmount)
                {
                    highestRevenueAmount = revenue;
                    indexOfMovie = j;
                }
            }
            highestRevenue.add(moviesList.get(indexOfMovie));
            moviesList.remove(indexOfMovie);
        }
        System.out.println("List of top 50 highest revenue movies:");
        for(int i = 0; i < highestRevenue.size(); i++)
        {
            String title = highestRevenue.get(i).getTitle();
            int revenue = highestRevenue.get(i).getRevenue();
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + title + ": " + revenue);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = highestRevenue.get(choice - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}