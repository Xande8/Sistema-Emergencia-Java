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
