
public class No {
  private User user;
  private int chave;
  private No esquerda;
  private No direita;
  private int altura;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public int getChave() {
    return chave;
  }

  public void setChave(int chave) {
    try {
      this.chave = user.getUsername().hashCode();
    } catch (Exception e) {
      System.out.println("Erro ao setar chave");
    }
  }

  public No getEsquerda() {
    return esquerda;
  }

  public void setEsquerda(No esquerda) {
    this.esquerda = esquerda;
  }

  public No getDireita() {
    return direita;
  }

  public void setDireita(No direita) {
    this.direita = direita;
  }

  public int getAltura() {
    return altura;
  }

  public void setAltura(int altura) {
    this.altura = altura;
  }

  public No(int chave, User User) {
    this.chave = chave;
    this.user = User;
    this.esquerda = null;
    this.direita = null;
    this.altura = 0;
    this.chave = user.getUsername().hashCode();
  }

  public String[] getFriends() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getFriends'");
  }

  public String[] getBooks() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getBooks'");
  }
}
