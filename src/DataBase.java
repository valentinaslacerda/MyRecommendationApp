import java.util.ArrayList;
import java.util.List;

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
}
