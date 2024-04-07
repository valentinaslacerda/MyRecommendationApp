import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

public class DataBase {
  private No raiz;
  private int rotacoes = 0;

  public int getRotacoes() {
    return rotacoes;
  }

  public void setRotacoes(int rotacoes) {
    this.rotacoes = rotacoes;
  }

  public void insert(int chave, User user) {
    raiz = insert(raiz, chave, user);
  }

  private No insert(No arv, int chave, User user) {
    if (arv == null) {
      return new No(chave, user);
    } else if (chave < arv.getChave()) {
      arv.setEsquerda(insert(arv.getEsquerda(), chave, user));
    } else if (chave > arv.getChave()) {
      arv.setDireita(insert(arv.getDireita(), chave, user));
    } else {
      return arv; // Caso chave já exista na árvore
    }

    arv.setAltura(1 + maior(altura(arv.getEsquerda()), altura(arv.getDireita())));

    int fb = obterFB(arv);

    if (fb > 1 && chave < arv.getEsquerda().getChave()) {
      rotacoes++;
      return rotacaoDireitaSimples(arv);
    }
    if (fb < -1 && chave > arv.getDireita().getChave()) {
      rotacoes++;
      return rotacaoEsquerdaSimples(arv);
    }
    if (fb > 1 && chave > arv.getEsquerda().getChave()) {
      rotacoes++;
      arv.setEsquerda(rotacaoEsquerdaSimples(arv.getEsquerda()));
      return rotacaoDireitaSimples(arv);
    }
    if (fb < -1 && chave < arv.getDireita().getChave()) {
      rotacoes++;
      arv.setDireita(rotacaoDireitaSimples(arv.getDireita()));
      return rotacaoEsquerdaSimples(arv);
    }

    return arv;
  }

  private No rotacaoDireitaSimples(No y) {
    No x = y.getEsquerda();
    No T2 = x.getDireita();

    x.setDireita(y);
    y.setEsquerda(T2);

    y.setAltura(maior(altura(y.getEsquerda()), altura(y.getDireita())) + 1);
    x.setAltura(maior(altura(x.getEsquerda()), altura(x.getDireita())) + 1);

    return x;
  }

  private No rotacaoEsquerdaSimples(No x) {
    No y = x.getDireita();
    No T2 = y.getEsquerda();

    y.setEsquerda(x);
    x.setDireita(T2);

    x.setAltura(maior(altura(x.getEsquerda()), altura(x.getDireita())) + 1);
    y.setAltura(maior(altura(y.getEsquerda()), altura(y.getDireita())) + 1);

    return y;
  }

  private int obterFB(No no) {
    if (no == null) {
      return 0;
    }
    return altura(no.getEsquerda()) - altura(no.getDireita());
  }

  public int altura(No no) {
    if (no == null) {
      return -1;
    }
    return no.getAltura();
  }

  private int maior(int a, int b) {
    return (a > b) ? a : b;
  }

  public No buscar(No arv, int chavebusca) {
    if (arv == null) {
      return null;
    } else if (arv.getChave() > chavebusca) {
      return buscar(arv.getEsquerda(), chavebusca);
    } else if (arv.getChave() < chavebusca) {
      return buscar(arv.getDireita(), chavebusca);
    } else {
      return arv;
    }
  }

  public void remove(int chave, User user) {
    raiz = remove(raiz, chave, user);
  }

  No remove(No arv, int chave, User user) {
    if (arv == null) {
      return arv;
    } else if (chave < arv.getChave()) {
      arv.setEsquerda(remove(arv.getEsquerda(), chave, user));
    } else if (chave > arv.getChave()) {
      arv.setDireita(remove(arv.getDireita(), chave, user));
    } else {
      if (arv.getEsquerda() == null && arv.getDireita() == null) {
        arv = null;
      } else if (arv.getEsquerda() == null) {
        No temp = arv;
        arv = temp.getDireita();
        temp = null;
      } else if (arv.getDireita() == null) {
        No temp = arv;
        arv = temp.getEsquerda();
        temp = null;
      } else {
        No temp = minimo(arv.getDireita());
        arv.setChave(temp.getChave());
        arv.setDireita(remove(arv.getDireita(), temp.getChave(), user));
      }
    }

    if (arv == null) {
      return arv;
    }

    arv.setAltura(1 + maior(altura(arv.getEsquerda()), altura(arv.getDireita())));
    int fb = obterFB(arv);
    int fbSubArvEsq = obterFB(arv.getEsquerda());
    int fbSubArvDir = obterFB(arv.getDireita());

    if (fb > 1 && fbSubArvEsq >= 0) {
      return rotacaoDireitaSimples(arv);
    } else if (fb < -1 && fbSubArvDir <= 0) {
      return rotacaoEsquerdaSimples(arv);
    } else if (fb > 1 && fbSubArvEsq < 0) {
      arv.setEsquerda(rotacaoEsquerdaSimples(arv.getEsquerda()));
      return rotacaoDireitaSimples(arv);
    } else if (fb < -1 && fbSubArvDir > 0) {
      arv.setDireita(rotacaoDireitaSimples(arv.getDireita()));
      return rotacaoEsquerdaSimples(arv);
    }

    return arv;
  }

  No minimo(No arv) {
    No temp = arv;
    if (temp == null) {
      return null;
    }
    while (temp.getEsquerda() != null) {
      temp = temp.getEsquerda();
    }
    return temp;
  }

  public List<User> listarUsers() {
    List<User> users = new ArrayList<>();
    ordem(getRaiz(), users);
    return users;
  }

  private void ordem(No a, List<User> users) {
    if (a != null) {
      ordem(a.getEsquerda(), users);
      users.add(a.getUser());
      ordem(a.getDireita(), users);
    }
  }

  public No getRaiz() {
    return raiz;
  }

  public void alterarUser(User userAlterar, int chave) {
    No no = buscar(raiz, chave);
    if (no != null) {
      no.setUser(userAlterar);
    }
  }

  public int quantidadeUsers(No arv) {
    if (arv == null) {
      return 0;
    } else {
      return 1 + quantidadeUsers(arv.getEsquerda()) + quantidadeUsers(arv.getDireita());
    }
  }

  // CRUD Operations

  // Create - Inserir um novo usuário
  public void criarUsuario(User user) {
    String username = user.getUsername();
    int chave = username.hashCode();
    user.setUsername(username);
    insert(chave, user);
    System.out.println("Usuário criado com sucesso!");
  }

  // Método para criar uma nova conta de usuário
  public void criarNovaConta() {
    User novoUsuario = new User(null, null, null, null, null, null);
    novoUsuario.setUsername(JOptionPane.showInputDialog("Digite um nome de usuário:"));
    novoUsuario.setPassword(JOptionPane.showInputDialog("Digite uma senha:"));
    // Outras informações de perfil podem ser solicitadas aqui, se necessário

    // Inserir o novo usuário na árvore
    int chave = novoUsuario.getUsername().hashCode();
    insert(chave, novoUsuario);
    JOptionPane.showMessageDialog(null, "Conta criada com sucesso! Faça login para continuar.");
  }

  // Read - Buscar um usuário pelo nome de usuário
  public User buscarUsuario(String username) {
    No no = buscar(raiz, username.hashCode());
    if (no != null) {
      return no.getUser();
    } else {
      JOptionPane.showMessageDialog(null, "Usuário não encontrado.");
      return null;
    }
  }

  // Update - Atualizar informações de livros, filmes e jogos de um usuário
  public void atualizarUsuario(String username) {
    No no = buscar(raiz, username.hashCode());
    if (no != null) {
      User user = no.getUser();

      // Solicitar ao usuário as novas informações de livros, filmes e jogos
      ArrayList<String> newBooks = new ArrayList<>();
      String bookInput = JOptionPane.showInputDialog("Digite os novos livros separados por vírgula:");
      if (bookInput != null && !bookInput.isEmpty()) {
        String[] bookArray = bookInput.split(",");
        for (String book : bookArray) {
          newBooks.add(book.trim());
        }
      }

      ArrayList<String> newMovies = new ArrayList<>();
      String movieInput = JOptionPane.showInputDialog("Digite os novos filmes separados por vírgula:");
      if (movieInput != null && !movieInput.isEmpty()) {
        String[] movieArray = movieInput.split(",");
        for (String movie : movieArray) {
          newMovies.add(movie.trim());
        }
      }

      ArrayList<String> newGames = new ArrayList<>();
      String gameInput = JOptionPane.showInputDialog("Digite os novos jogos separados por vírgula:");
      if (gameInput != null && !gameInput.isEmpty()) {
        String[] gameArray = gameInput.split(",");
        for (String game : gameArray) {
          newGames.add(game.trim());
        }
      }

      // Atualizar as informações do usuário com as novas informações de livros,
      // filmes e jogos
      user.setBooks(newBooks);
      user.setMovies(newMovies);
      user.setGames(newGames);

      JOptionPane.showMessageDialog(null, "Informações de livros, filmes e jogos atualizadas com sucesso!");
    } else {
      JOptionPane.showMessageDialog(null, "Usuário não encontrado.");
    }
  }

  // Delete - Remover um usuário pelo nome de usuário
  public void deletarUsuario(String username) {
    No no = buscar(raiz, username.hashCode());
    if (no != null) {
      remove(username.hashCode(), no.getUser());
      JOptionPane.showMessageDialog(null, "Usuário removido com sucesso!");
    } else {
      JOptionPane.showMessageDialog(null, "Usuário não encontrado.");
    }
  }

  // Método para gerar recomendações com base nos gostos dos amigos
  public void gerarRecomendacoes(User usuario) {
    List<String> recomendacoes = new ArrayList<>();

    // Percorrer os amigos do usuário
    for (String amigoUsername : usuario.getFriends()) {
      // Buscar o amigo na árvore
      User amigo = buscarUsuario(amigoUsername);

      // Se o amigo for encontrado e tiver preferências de livros, filmes e séries
      if (amigo != null && amigo.getBooks() != null && amigo.getMovies() != null && amigo.getGames() != null) {
        // Adicionar as preferências do amigo às recomendações
        recomendacoes.addAll(amigo.getBooks());
        recomendacoes.addAll(amigo.getMovies());
        recomendacoes.addAll(amigo.getGames());
      }
    }

    // Remover duplicatas das recomendações
    Set<String> recomendacoesSemDuplicatas = new HashSet<>(recomendacoes);
    recomendacoes.clear();
    recomendacoes.addAll(recomendacoesSemDuplicatas);

    // Exibir as recomendações ao usuário
    JOptionPane.showMessageDialog(null, "Recomendações baseadas nos gostos dos amigos:\n" + recomendacoes);
  }

  // Método para realizar o login do usuário
  public User fazerLogin() {
    String username = JOptionPane.showInputDialog("Digite seu nome de usuário:");
    String senha = JOptionPane.showInputDialog("Digite sua senha:");

    // Buscar o usuário na árvore
    No noUsuario = buscar(raiz, username.hashCode());

    // Verificar se o usuário existe e se a senha está correta
    if (noUsuario != null && noUsuario.getUser().getPassword().equals(senha)) {
      JOptionPane.showMessageDialog(null, "Login bem-sucedido!");
      return noUsuario.getUser(); // Retornar o usuário logado
    } else {
      JOptionPane.showMessageDialog(null, "Nome de usuário ou senha incorretos.");
      return null; // Retornar null se o login falhar
    }
  }

  // Método para exibir o menu principal e lidar com as opções
  public void exibirMenu() {
    int opcao;
    do {
      String escolha = JOptionPane.showInputDialog(
          "Selecione uma opção:\n" +
              "1 - Login\n" +
              "2 - Criar nova conta\n" +
              "0 - Sair");
      opcao = Integer.parseInt(escolha);

      switch (opcao) {
        case 1:
          // Opção para fazer login
          User usuarioLogado = fazerLogin();
          if (usuarioLogado != null) {
            // Se o login for bem-sucedido, exibir o menu principal
            exibirMenuPrincipal(usuarioLogado);
          }
          break;
        case 2:
          // Opção para criar nova conta
          criarNovaConta();
          break;
        case 0:
          // Opção para sair do programa
          JOptionPane.showMessageDialog(null, "Saindo do programa...");
          break;
        default:
          JOptionPane.showMessageDialog(null, "Opção inválida!");
          break;
      }
    } while (opcao != 0);
  }

  // Interface com JOptionPane
  public void exibirMenuPrincipal(User usuarioLogado) {
    int opcao;
    do {
      String escolha = JOptionPane.showInputDialog(
          "Selecione uma opção:\n" +
              "1 - Buscar amigo\n" +
              "2 - Atualizar Usuário\n" +
              "3 - Deletar Usuário\n" +
              "4 - Listar amigos\n" +
              "5 - Gerar recomendações\n" +
              "0 - Sair");
      opcao = Integer.parseInt(escolha);

      switch (opcao) {
        case 1:
          String nomeBuscar = JOptionPane.showInputDialog("Digite o nome de usuário a ser buscado:");
          User amigo = buscarUsuario(nomeBuscar);
          JOptionPane.showMessageDialog(null,
              amigo.getUsername() + "\n" + "Livros: " + amigo.getBooks() + "\n Jogos: " + amigo.getGames()
                  + "\n Filmes: " + amigo.getMovies() + "\n");
          break;
        case 2:
          try {
            atualizarUsuario(usuarioLogado.getUsername());
            JOptionPane.showMessageDialog(null, "Usuário atualizado!");
          } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar usuário!");
          }

          break;
        case 3:
          try {
            deletarUsuario(usuarioLogado.getUsername());
            JOptionPane.showMessageDialog(null, "Usuário deletado!");

          } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao deletar usuário!");
          }

          break;
        case 4:
          List<User> users = listarUsers();
          List<String> nomes = new ArrayList<String>();
          for (User user : users) {
            nomes.add(user.getUsername());
          }
          JOptionPane.showMessageDialog(null, nomes);
          break;
        case 5:
          gerarRecomendacoes(usuarioLogado);
          break;

        case 0:
          JOptionPane.showMessageDialog(null, "Saindo do programa...");
          break;
        default:
          JOptionPane.showMessageDialog(null, "Opção inválida!");
          break;
      }
    } while (opcao != 0);
    exibirMenu();
  }
}
