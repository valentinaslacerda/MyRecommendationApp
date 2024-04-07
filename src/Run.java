import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Run {
  public static void main(String[] args) {
    // Criar listas para armazenar informações dos usuários
    ArrayList<String> livroList1 = new ArrayList<>();
    ArrayList<String> filmesList1 = new ArrayList<>();
    ArrayList<String> jogosList1 = new ArrayList<>();
    ArrayList<String> amigosList1 = new ArrayList<>();

    // Adicionar informações para o usuário 1
    List<String> livros1 = Arrays.asList("Ana Karenina", "O Hobbit");
    List<String> jogos1 = Arrays.asList("Celeste", "Stardew Valley");
    List<String> amigos1 = Arrays.asList("Mateus", "Ricardo");

    livroList1.addAll(livros1);
    jogosList1.addAll(jogos1);
    amigosList1.addAll(amigos1);

    // Criar o usuário 1
    User user1 = new User("Val", "1234", livroList1, jogosList1, filmesList1, amigosList1);

    // Criar listas para armazenar informações dos usuários
    ArrayList<String> livroList2 = new ArrayList<>();
    ArrayList<String> filmesList2 = new ArrayList<>();
    ArrayList<String> jogosList2 = new ArrayList<>();
    ArrayList<String> amigosList2 = new ArrayList<>();

    // Adicionar informações para o usuário 2
    List<String> livros2 = Arrays.asList("Dom casmurro", "Uma Vida Pequena");
    List<String> jogos2 = Arrays.asList("COD", "LOL");
    List<String> amigos2 = Arrays.asList("Val", "Ricardo");

    livroList2.addAll(livros2);
    jogosList2.addAll(jogos2);
    amigosList2.addAll(amigos2);

    // Criar os outros usuários
    User user2 = new User("Mateus", "5678", livroList2, jogosList2, filmesList2, amigosList2);

    // Criar listas para armazenar informações dos usuários
    ArrayList<String> livroList3 = new ArrayList<>();
    ArrayList<String> filmesList3 = new ArrayList<>();
    ArrayList<String> jogosList3 = new ArrayList<>();
    ArrayList<String> amigosList3 = new ArrayList<>();

    // Adicionar informações para o usuário 3
    List<String> livros3 = Arrays.asList("Divina Comédia");
    List<String> jogos3 = Arrays.asList("Stardew Valley");
    List<String> amigos3 = Arrays.asList("Mateus", "Val");

    livroList3.addAll(livros3);
    jogosList3.addAll(jogos3);
    amigosList3.addAll(amigos3);
    User user3 = new User("Ricardo", "91011", livroList3, jogosList3, filmesList3, amigosList3);

    // Adicionar os usuários ao banco de dados
    DataBase database = new DataBase();
    database.criarUsuario(user1);
    database.criarUsuario(user2);
    database.criarUsuario(user3);

    // Exibir o menu do banco de dados
    database.exibirMenu();
  }
}
