# Sistema de Emergencia - Etapa 2 (POO 2026.2)

IFPB Joao Pessoa - Prof. Fausto Ayres

## Requisitos
- `src/modelo`: Paciente, Atendimento, Medicamento e **MedicamentoControlado**, subclasse de Medicamento com atributo `crm`.
- `src/appconsole`: Cadastrar, Listar, Alterar e Apagar.
- `src/util`: Util e GeradorID **adaptados do exemplo AgendaCompromisso-db4o fornecido pelo professor**.
- Banco principal `emergencia.db4o`; banco auxiliar automatico `sequencia.db4o` (contador de IDs).
- Datas representadas por `String`. IDs numericos de Atendimento, Medicamento e MedicamentoControlado atribuidos automaticamente pelo `GeradorID` do professor; os dois tipos de medicamento compartilham a mesma sequencia.
- `toString()` mostra os vinculos sem recursao e a listagem usa `forEach` sem laco `for` explicito.

## Executar pelo VS Code
1. Abrir esta pasta com Extension Pack for Java instalado.
2. Confirmar JDK instalado (testado em Java 21) e bibliotecas `lib/` carregadas automaticamente por `.vscode/settings.json`.
3. Executar `appconsole.Cadastrar`, selecionar **1** para os dados demonstrativos e **0** para sair. Depois executar: `appconsole.Listar`, `appconsole.Alterar`, `appconsole.Listar`, `appconsole.Apagar`, `appconsole.Listar`.
4. Para reiniciar o demonstrativo, com o programa fechado, apagar **ambos** `emergencia.db4o` e `sequencia.db4o` (e possiveis arquivos de backup relacionados). Nunca apagar so o banco de sequencias se ainda houver dados cadastrados.

## Observacoes
- `Cadastrar` agora oferece **menu interativo**: o cadastro demonstrativo (opcao 1) continua disponivel, e as demais opcoes permitem cadastrar novos objetos e relacionamentos sem apagar o banco. Para sair, use a opcao 0.
- `Alterar` remove um vinculo Atendimento-Dipirona; `Apagar` remove Joao e os atendimentos associados, preservando os medicamentos.
- O gerador de IDs fornecido pelo professor foi preservado em sua logica; apenas o `for` de procura de campo foi convertido a Stream e `getFirst` a `get(0)` para portabilidade.
- `Util` foi adaptada nas configuracoes das classes e nome do arquivo principal; restante segue o exemplo da aula.
- `GeradorID` do exemplo requer tambem a biblioteca db4o client-server para compilacao, mesmo usando o banco local.
- O diagrama textual `UML/Emergencia.puml` inclui a heranca do Projeto 2. Os arquivos `UML/Emergencia.svg` e `UML/Emergencia.pdf` ainda sao da etapa anterior: nao foram regenerados porque o PlantUML nao estava disponivel no ambiente de edicao.

## Divisao da apresentacao (Pedro, Igor e Alexandre)

A apresentacao segue a sequencia `Cadastrar (opcao 1, depois 0) -> Listar -> Alterar -> Listar -> Apagar -> Listar`.

| Integrante | Nivel | Responsabilidade |
|---|---|---|
| Alexandre | Facil | Introducao ao sistema, classes de negocio, `Cadastrar.java`, `Listar.java` e exibicao via `toString()`. |
| Igor | Intermediario | `Alterar.java` e relacionamento entre Atendimento e Medicamento: remover o vinculo sem excluir objetos. |
| Pedro | Avancado | `Apagar.java`, persistencia Db4o, `Util.java` e `GeradorID.java`; exclusao sem deixar referencias inconsistentes. |

**Roteiro de demonstracao:** Alexandre executa cadastro e listagem inicial; Igor demonstra a alteracao seguida de nova listagem; Pedro executa a exclusao e confirma o estado final com outra listagem. Reiniciar `emergencia.db4o` e `sequencia.db4o` antes da apresentacao, com os programas fechados, caso queiram repetir o cadastro desde o inicio.


## IDs automaticos e Db4o

O Db4o armazena os objetos e dispara o evento `creating()` quando um objeto novo e persistido. A classe `util.GeradorID`, fornecida no exemplo de aula, escuta esse evento e preenche automaticamente o atributo `int id` das classes `Atendimento` e `Medicamento`. O controle da numeracao e armazenado em `sequencia.db4o`. Nao atribuimos IDs manualmente nos construtores nem em `Cadastrar.java`.

A classe `Paciente` mantem o CPF como identificacao, de acordo com a modelagem da primeira etapa. MedicamentoControlado herda o atributo `id` de Medicamento e usa o mesmo contador via `GeradorID`, sem nenhuma alteracao em `src/util/GeradorID.java`. Os IDs internos usados pelo proprio Db4o sao diferentes dos IDs numericos da aplicacao. Os metodos `toString()` apenas mostram o ID ja gerado, sem criar nem alterar identificadores.

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
# No menu: 1 cadastra o exemplo, 2 a 5 e 7 criam ou relacionam objetos,
# 6 consulta os dados; 0 encerra e libera o banco.
java -cp "bin:lib/*" appconsole.Listar
java -cp "bin:lib/*" appconsole.Alterar
java -cp "bin:lib/*" appconsole.Listar
java -cp "bin:lib/*" appconsole.Apagar
java -cp "bin:lib/*" appconsole.Listar
```

O primeiro cadastro cria `emergencia.db4o` e `sequencia.db4o` no diretorio de execucao. Os bancos estao no `.gitignore` e nao sao enviados ao GitHub. Para reiniciar a demonstracao, feche todos os programas Java e apague **ambos** os arquivos de banco antes de executar `Cadastrar` novamente.


## Cadastro ao vivo para a apresentacao

Executar **`java -cp "bin:lib/*" appconsole.Cadastrar`** no terminal do Codespaces. O menu de cadastro tem estas opcoes:

| Opcao | Operacao |
|---|---|
| 1 | Cadastrar 3 pacientes, 4 atendimentos e 4 medicamentos, incluindo 1 controlado (somente se o banco estiver vazio) |
| 2 | Cadastrar novo paciente (CPF com 11 digitos e nome) |
| 3 | Cadastrar novo medicamento (nome; ID numerico automatico no Db4o) |
| 4 | Cadastrar novo atendimento (CPF de paciente ja cadastrado e data como `String` no formato dd/MM/aaaa; ID automatico) |
| 5 | Vincular um medicamento a um atendimento, informando os IDs exibidos na listagem |
| 6 | Listar os objetos gravados |
| 7 | Cadastrar um medicamento controlado (nome + CRM; ID gerado automaticamente) |
| 0 | Encerrar o cadastro, fechando o banco |

**Exemplo caso o professor peca um objeto novo:**
1. Execute o programa e escolha `2` para cadastrar o paciente Carlos, informando um CPF de 11 digitos ainda nao utilizado.
2. Escolha `3` e cadastre o medicamento Amoxicilina. Anote o ID gerado automaticamente.
3. Escolha `4`, informe o CPF de Carlos e uma data real (por exemplo, 29/10/2026). Anote o ID do atendimento.
4. Escolha `5` e informe os IDs do atendimento e do medicamento para associa-los.
5. Escolha `6` para consultar os objetos. Digite `0` para encerrar e liberar o banco.
6. Execute `java -cp "bin:lib/*" appconsole.Listar` **em outra execucao** para comprovar que os objetos e os vinculos foram persistidos no `emergencia.db4o`.

As operacoes usam `manager.store(...)` e `manager.commit()`. O usuario nunca digita um ID novo: os IDs numericos sao gerados pelo `GeradorID` ativado pela conexao Db4o. CPF deve ter 11 digitos; isso e uma verificacao de formato, nao uma validacao matematica de CPF. Datas permanecem armazenadas como texto. A listagem usa `toString()` e `forEach`, sem `for` explicito.

**Atenção:** se tiver usado o Codespaces antes dessa atualizacao, execute `git pull origin main` no terminal do Codespace ou recrie-o. Nao execute `Listar` enquanto o menu `Cadastrar` estiver aberto, pois ambos podem tentar acessar o mesmo arquivo Db4o.

## Projeto 2 — Subclasse MedicamentoControlado

- `src/modelo/MedicamentoControlado.java` estende `Medicamento`, acrescenta `private String crm`, `getCrm()` e `toString()` que reaproveita `super.toString()`.
- A subclasse utiliza os relacionamentos herdados com `Atendimento`. A consulta `manager.query(Medicamento.class)` inclui medicamentos comuns e controlados, sem modificar `Listar`, `Alterar`, `Apagar` ou `Entrada`.
- `Util` configura para `MedicamentoControlado` `cascadeOnDelete(false)`, `cascadeOnUpdate(true)` e `cascadeOnActivate(true)`, como para as outras classes.
- O exemplo da opcao **1** cria Morfina (CRM de demonstracao: `CRM-PB 12345`) e a associa a um atendimento da paciente Ana; a opcao **7** cadastra um controlado novo com nome e CRM informados no terminal.
- IDs **nao sao atribuidos manualmente**: o gatilho `creating()` no `GeradorID` (inalterado) escolhe a sequencia da superclasse `Medicamento`, inclusive para `MedicamentoControlado`.
- Datas continuam salvas como `String`. Nao existem `for` explicitos no codigo novo.

### Verificacao pelo terminal Linux

```bash
mkdir -p bin
javac -encoding UTF-8 -cp "lib/*" -d bin $(find src -name "*.java")
javac -encoding UTF-8 -cp "bin:lib/*" -d bin tests/TesteRelacionamentos.java
java -cp "bin:lib/*" TesteRelacionamentos
```

Para testar o ciclo de demonstracao **em um diretorio sem bancos anteriores**, execute `Cadastrar` (digite 1, depois 0), `Listar`, `Alterar`, `Listar`, `Apagar`, `Listar` conforme os comandos da secao de Codespaces. Verifique que o medicamento controlado aparece com `CRM` e ID positivo, e que permanece cadastrado apos a exclusao do paciente Joao.

Para testar a **opcao 7**, execute `Cadastrar`, digite 7, informe nome e CRM, e encerre com 0. Em outra execucao, rode `Listar` para comprovar a persistencia.

**Nota sobre o UML:** edite ou visualize `UML/Emergencia.puml` para ver a heranca atualizada. Os arquivos SVG e PDF versionados ainda nao foram atualizados.
