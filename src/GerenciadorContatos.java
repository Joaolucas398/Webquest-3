// Integrantes
// João Lucas de Souza Paz
// Alan Mendes Rocha
// Wedson Gabriel da Silva
// Paulo Marcelo Cordeiro de Jesus 

import java.util.List;

public interface GerenciadorContatos {
    void adicionarContato(Contato contato) throws ContatoExistenteException;
    Contato buscarContato(String nome) throws ContatoNaoEncontradoException;
    void removerContato(String nome) throws ContatoNaoEncontradoException;
    List<Contato> listarTodosContatos();
}
