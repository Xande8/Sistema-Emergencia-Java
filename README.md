# Sistema de Emergencia - Etapa 2 (POO 2026.2)

IFPB Joao Pessoa - Prof. Fausto Ayres

## Requisitos
- `src/modelo`: Paciente, Atendimento e Medicamento (modelagem da etapa 1).
- `src/appconsole`: Cadastrar, Listar, Alterar e Apagar.
- `src/util`: Util e GeradorID **adaptados do exemplo AgendaCompromisso-db4o fornecido pelo professor**.
- Banco principal `emergencia.db4o`; banco auxiliar automatico `sequencia.db4o` (contador de IDs).
- Datas representadas por `String`. IDs numericos de Atendimento e Medicamento atribuidos pelo trigger do professor.
- `toString()` mostra os vinculos sem recursao e a listagem usa `forEach` sem laco `for` explicito.

## Executar pelo VS Code
1. Abrir esta pasta com Extension Pack for Java instalado.
2. Confirmar JDK instalado (testado em Java 21) e bibliotecas `lib/` carregadas automaticamente por `.vscode/settings.json`.
3. Executar, na ordem: `appconsole.Cadastrar`, `appconsole.Listar`, `appconsole.Alterar`, `appconsole.Listar`, `appconsole.Apagar`, `appconsole.Listar`.
4. Para reiniciar o demonstrativo, com o programa fechado, apagar **ambos** `emergencia.db4o` e `sequencia.db4o` (e possiveis arquivos de backup relacionados). Nunca apagar so o banco de sequencias se ainda houver dados cadastrados.

## Observacoes
- `Cadastrar` e uma demonstracao com dados fixos, nao um menu interativo; nao duplica registros caso o banco tenha objetos.
- `Alterar` remove um vinculo Atendimento-Dipirona; `Apagar` remove Joao e os atendimentos associados, preservando os medicamentos.
- O gerador de IDs fornecido pelo professor foi preservado em sua logica; apenas o `for` de procura de campo foi convertido a Stream e `getFirst` a `get(0)` para portabilidade.
- `Util` foi adaptada nas configuracoes das classes e nome do arquivo principal; restante segue o exemplo da aula.
- `GeradorID` do exemplo requer tambem a biblioteca db4o client-server para compilacao, mesmo usando o banco local.
- O arquivo UML e o material da primeira etapa estao em `UML/` como referencia.

## Divisao da apresentacao (Pedro, Igor e Alexandre)

A apresentacao segue a sequencia `Cadastrar -> Listar -> Alterar -> Listar -> Apagar -> Listar`.

| Integrante | Nivel | Responsabilidade |
|---|---|---|
| Alexandre | Facil | Introducao ao sistema, classes de negocio, `Cadastrar.java`, `Listar.java` e exibicao via `toString()`. |
| Igor | Intermediario | `Alterar.java` e relacionamento entre Atendimento e Medicamento: remover o vinculo sem excluir objetos. |
| Pedro | Avancado | `Apagar.java`, persistencia Db4o, `Util.java` e `GeradorID.java`; exclusao sem deixar referencias inconsistentes. |

**Roteiro de demonstracao:** Alexandre executa cadastro e listagem inicial; Igor demonstra a alteracao seguida de nova listagem; Pedro executa a exclusao e confirma o estado final com outra listagem. Reiniciar `emergencia.db4o` e `sequencia.db4o` antes da apresentacao, com os programas fechados, caso queiram repetir o cadastro desde o inicio.


## IDs automaticos e Db4o

O Db4o armazena os objetos e dispara o evento `creating()` quando um objeto novo e persistido. A classe `util.GeradorID`, fornecida no exemplo de aula, escuta esse evento e preenche automaticamente o atributo `int id` das classes `Atendimento` e `Medicamento`. O controle da numeracao e armazenado em `sequencia.db4o`. Nao atribuimos IDs manualmente nos construtores nem em `Cadastrar.java`.

A classe `Paciente` mantem o CPF como identificacao, de acordo com a modelagem da primeira etapa. Os IDs internos usados pelo proprio Db4o sao diferentes dos IDs numericos da aplicacao. Os metodos `toString()` apenas mostram o ID ja gerado, sem criar nem alterar identificadores.

**Verificacao:** no teste com Java 21, um `Atendimento` e um `Medicamento` novos apresentavam `getId() == 0` antes do salvamento e passaram a receber IDs numericos positivos apos `manager.store()` e `manager.commit()`.

## Executar no GitHub Codespaces

O arquivo `.devcontainer/devcontainer.json` seleciona Java 21 e instala a extensao **Extension Pack for Java** (`vscjava.vscode-java-pack`). A pasta `lib/` e reconhecida pelo `.vscode/settings.json`.

1. No GitHub: **Code > Codespaces > Create codespace on main**. Se voce ja tinha um Codespace aberto antes desta configuracao, use **Codespaces: Rebuild Container** para aplicar o Java 21.
2. No terminal Linux do Codespaces, execute os comandos a seguir, um de cada vez:

```bash
java -version
mkdir -p bin
javac -encoding UTF-8 -cp "lib/*" -d bin $(find src -name "*.java")
java -cp "bin:lib/*" appconsole.Cadastrar
java -cp "bin:lib/*" appconsole.Listar
java -cp "bin:lib/*" appconsole.Alterar
java -cp "bin:lib/*" appconsole.Listar
java -cp "bin:lib/*" appconsole.Apagar
java -cp "bin:lib/*" appconsole.Listar
```

O primeiro cadastro cria `emergencia.db4o` e `sequencia.db4o` no diretorio de execucao. Os bancos estao no `.gitignore` e nao sao enviados ao GitHub. Para reiniciar a demonstracao, feche todos os programas Java e apague **ambos** os arquivos de banco antes de executar `Cadastrar` novamente.
