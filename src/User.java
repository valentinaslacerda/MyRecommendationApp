import java.util.ArrayList;

public class User {
  private String username;
  private String password;
  private ArrayList<String> books;
  private ArrayList<String> games;
  private ArrayList<String> movies;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public ArrayList<String> getBooks() {
    return books;
  }

  public void setBooks(ArrayList<String> books) {
    this.books = books;
  }

  public ArrayList<String> getGames() {
    return games;
  }

  public void setGames(ArrayList<String> games) {
    this.games = games;
  }

  public ArrayList<String> getMovies() {
    return movies;
  }

  public void setMovies(ArrayList<String> movies) {
    this.movies = movies;
  }

  public User(String username, String password, ArrayList<String> books, ArrayList<String> games,
      ArrayList<String> movies) {
    setUsername(username);
    setPassword(password);
    setBooks(books);
    setGames(games);
    setMovies(movies);
  }
}
