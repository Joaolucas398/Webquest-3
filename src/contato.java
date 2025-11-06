public class Contato {
    private String nome;
    private String telefone;
    private String email;

    public Contato(String nome, String telefone, String email) {
        setNome(nome);
        setTelefone(telefone);
        setEmail(email);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        if (telefone == null) telefone = "";
        this.telefone = telefone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null) email = "";
        this.email = email.trim();
    }

    @Override
    public String toString() {
        return String.format("Nome: %s | Telefone: %s | Email: %s", nome, telefone, email);
    }
}