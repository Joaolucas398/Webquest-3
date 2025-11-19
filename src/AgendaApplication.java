// AgendaApplication.java
// Classe principal com menu interativo
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class AgendaApplication {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AgendaManager agenda = new AgendaManager();

    public static void main(String[] args) {
        System.out.println("=== Agenda Eletrônica ===");
        boolean sair = false;
        while (!sair) {
            exibirMenu();
            String opcao = lerLinha("Escolha uma opção:");
            switch (opcao) {
                case "1":
                    opcaoAdicionarContato();
                    break;
                case "2":
                    opcaoBuscarContato();
                    break;
                case "3":
                    opcaoRemoverContato();
                    break;
                case "4":
                    opcaoListarTodosContatos();
                    break;
                case "5":
                    opcaoSalvarCSV();
                    break;
                case "6":
                    opcaoCarregarCSV();
                    break;
                case "7":
                    System.out.println("Saindo. Até mais!");
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("1. Adicionar Contato");
        System.out.println("2. Buscar Contato");
        System.out.println("3. Remover Contato");
        System.out.println("4. Listar Todos os Contatos");
        System.out.println("5. Salvar em CSV");
        System.out.println("6. Carregar de CSV");
        System.out.println("7. Sair");
    }

    private static String lerLinha(String prompt) {
        System.out.print(prompt + " ");
        return scanner.nextLine().trim();
    }

    private static void opcaoAdicionarContato() {
        try {
            String nome = lerLinha("Nome:");
            if (nome.isEmpty()) {
                System.out.println("Nome não pode ser vazio.");
                return;
            }
            String telefone = lerLinha("Telefone:");
            String email = lerLinha("Email:");
            Contato novo = new Contato(nome, telefone, email);
            agenda.adicionarContato(novo);
            System.out.println("Contato adicionado com sucesso.");
        } catch (ContatoExistenteException ex) {
            System.out.println("Erro: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.out.println("Dados inválidos: " + ex.getMessage());
        }
    }

    private static void opcaoBuscarContato() {
        String nome = lerLinha("Nome para buscar:");
        try {
            Contato c = agenda.buscarContato(nome);
            System.out.println("Contato encontrado:");
            System.out.println(c);
        } catch (ContatoNaoEncontradoException ex) {
            System.out.println("Erro: " + ex.getMessage());
            // Sugestão: procurar por similaridade / listarContatosOrdenados poderia ser aplicada aqui.
        }
    }

    private static void opcaoRemoverContato() {
        String nome = lerLinha("Nome para remover:");
        try {
            agenda.removerContato(nome);
            System.out.println("Contato removido com sucesso.");
        } catch (ContatoNaoEncontradoException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }

    private static void opcaoListarTodosContatos() {
        List<Contato> lista = agenda.listarContatosOrdenados();
        if (lista.isEmpty()) {
            System.out.println("Agenda vazia.");
        } else {
            System.out.println("Contatos:");
            for (Contato c : lista) {
                System.out.println(c);
            }
        }
        // Além disso, demonstramos busca por domínio como exemplo (opcional)
        String resposta = lerLinha("Deseja buscar por domínio de email? (s/n)");
        if (resposta.equalsIgnoreCase("s")) {
            String dominio = lerLinha("Informe o domínio (ex: gmail.com):");
            List<Contato> encontrados = agenda.buscarPorDominioEmail(dominio);
            if (encontrados.isEmpty()) {
                System.out.println("Nenhum contato com domínio fornecido.");
            } else {
                System.out.println("Contatos com domínio '" + dominio + "':");
                for (Contato c : encontrados) System.out.println(c);
            }
        }
    }

    private static void opcaoSalvarCSV() {
        String arquivo = lerLinha("Nome do arquivo para salvar (ex: contatos.csv):");
        if (arquivo.isEmpty()) {
            System.out.println("Nome de arquivo inválido.");
            return;
        }
        try {
            agenda.salvarContatosCSV(arquivo);
            System.out.println("Contatos salvos em " + arquivo);
        } catch (IOException ex) {
            System.out.println("Erro ao salvar arquivo: " + ex.getMessage());
        }
    }

    private static void opcaoCarregarCSV() {
        String arquivo = lerLinha("Nome do arquivo para carregar (ex: contatos.csv):");
        if (arquivo.isEmpty()) {
            System.out.println("Nome de arquivo inválido.");
            return;
        }
        try {
            agenda.carregarContatosCSV(arquivo);
            System.out.println("Contatos carregados de " + arquivo);
        } catch (IOException ex) {
            System.out.println("Erro ao carregar arquivo: " + ex.getMessage());
        }
    }
}