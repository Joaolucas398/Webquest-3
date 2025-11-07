 import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class AgendaManager implements GerenciadorContatos {

    private final Map<String, Contato> contatos;

    public AgendaManager() {
        this.contatos = new HashMap<>();
    }

    private String chaveParaNome(String nome) {
        return nome == null ? "" : nome.trim().toLowerCase();
    }

    @Override
    public void adicionarContato(Contato contato) throws ContatoExistenteException {
        String chave = chaveParaNome(contato.getNome());
        if (contatos.containsKey(chave)) {
            throw new ContatoExistenteException("Contato com nome '" + contato.getNome() + "' já existe.");
        }
        contatos.put(chave, contato);
    }

    @Override
    public Contato buscarContato(String nome) throws ContatoNaoEncontradoException {
        String chave = chaveParaNome(nome);
        Contato c = contatos.get(chave);
        if (c == null) {
            throw new ContatoNaoEncontradoException("Contato '" + nome + "' não encontrado.");
        }
        return c;
    }

    @Override
    public void removerContato(String nome) throws ContatoNaoEncontradoException {
        String chave = chaveParaNome(nome);
        Contato removed = contatos.remove(chave);
        if (removed == null) {
            throw new ContatoNaoEncontradoException("Contato '" + nome + "' não encontrado para remoção.");
        }
    }

    @Override
    public List<Contato> listarTodosContatos() {
        return new ArrayList<>(contatos.values());
    }

    // Retorna lista ordenada alfabeticamente por nome
    public List<Contato> listarContatosOrdenados() {
        List<Contato> lista = listarTodosContatos();
        lista.sort(Comparator.comparing(c -> c.getNome().toLowerCase()));
        return lista;
    }

    // Busca contatos por domínio de email (ex.: "gmail.com" ou "@gmail.com")
    public List<Contato> buscarPorDominioEmail(String dominio) {
        if (dominio == null) dominio = "";
        String dominioNormalizado = dominio.trim().toLowerCase();
        if (dominioNormalizado.startsWith("@")) {
            dominioNormalizado = dominioNormalizado.substring(1);
        }

        List<Contato> encontrados = new ArrayList<>();
        for (Contato c : contatos.values()) {
            String email = c.getEmail().toLowerCase();
            if (!email.isEmpty() && email.endsWith(dominioNormalizado)) {
                // ensure full match on domain (e.g., endsWith "gmail.com")
                encontrados.add(c);
            }
        }
        return encontrados;
    }

    // Salva contatos em CSV (nome;telefone;email) - sobrescreve arquivo
    public void salvarContatosCSV(String nomeArquivo) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(nomeArquivo))) {
            for (Contato c : listarContatosOrdenados()) {
                // Escapamos ; apenas removendo (pode ser melhorado se necessário)
                String nome = c.getNome().replace(";", " ");
                String telefone = c.getTelefone().replace(";", " ");
                String email = c.getEmail().replace(";", " ");
                String linha = String.join(";", nome, telefone, email);
                writer.write(linha);
                writer.newLine();
            }
        }
    }

    // Carrega contatos de CSV (nome;telefone;email)
    // Se encontrar contato existente, ignora (ou poderia atualizar — escolha feita: ignora).
    public void carregarContatosCSV(String nomeArquivo) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(nomeArquivo))) {
            String linha;
            int linhaNum = 0;
            while ((linha = reader.readLine()) != null) {
                linhaNum++;
                linha = linha.trim();
                if (linha.isEmpty()) continue;
                String[] partes = linha.split(";");
                if (partes.length < 1) {
                    System.err.println("Linha " + linhaNum + " com formato inválido, ignorando.");
                    continue;
                }
                String nome = partes.length > 0 ? partes[0].trim() : "";
                String telefone = partes.length > 1 ? partes[1].trim() : "";
                String email = partes.length > 2 ? partes[2].trim() : "";
                if (nome.isEmpty()) {
                    System.err.println("Linha " + linhaNum + ": nome vazio, ignorando.");
                    continue;
                }
                Contato contato = new Contato(nome, telefone, email);
                String chave = nome.toLowerCase();
                // Se já existir, ignoramos para não lançar exceção. Outra opção: atualizar.
                contatos.putIfAbsent(chave, contato);
            }
        }
    }
}
