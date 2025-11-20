# AgendaEletronica

Projeto de Agenda Eletrônica — versão para entrega.

## Estrutura do projeto
```
AgendaEletronica/
 ├── src/
 │    ├── Contato.java
 │    ├── GerenciadorContatos.java
 │    ├── ContatoExistenteException.java
 │    ├── ContatoNaoEncontradoException.java
 │    ├── AgendaManager.java
 │    └── AgendaApplication.java
 ├── README.md
 └── .gitignore
```

## Como compilar e executar (linha de comando)

1. Abra um terminal na pasta `AgendaEletronica`.
2. Compile os arquivos Java:
   ```
   javac src/*.java
   ```
3. Execute a aplicação:
   ```
   java -cp src AgendaApplication
   ```
4. Ou use a extenção do Java

## O que o programa faz
- Permite adicionar, buscar e remover contatos.
- Lista contatos ordenados por nome.
- Salva e carrega contatos em/desde arquivos CSV (formato: `nome;telefone;email`).
- Busca por domínio de email (ex.: `gmail.com`).

## Instruções para entrega no GitHub (ordem de commits)

Para mostrar claramente a contribuição de cada integrante no histórico:

1. **João Lucas de Souza Paz** — Commit inicial: criar repositório e adicionar estrutura base (README.md, .gitignore, pasta `src/`).
2. **Alan Mendes Rocha** — Commit: adicionar `Contato.java` e exceções (`ContatoExistenteException.java`, `ContatoNaoEncontradoException.java`).
3. **Wedson Gabriel da Silva** — Commit: adicionar `GerenciadorContatos.java` e `AgendaManager.java`.
4. **Paulo Marcelo Cordeiro de Jesus** — Commit: adicionar `AgendaApplication.java` (menu e interação).
5. **João Lucas de Souza Paz** — Commit final: revisão, ajustes e mensagens finais no README indicando testes e instruções.

**Observação:** cada integrante deve configurar seu `user.name` e `user.email` no Git antes de commitar:
```
git config user.name "Seu Nome"
git config user.email "seu-email@example.com"
```

## Onde inserir os nomes dos integrantes
- Os arquivos `.java` já contém um comentário no topo com `Integrantes: <INSERIR_NOMES_AQUI>`.
- Substituam esse marcador pelos nomes completos quando for fazer o commit final.

## .gitignore sugerido
```
*.class
/bin/
.idea/
.vscode/
```