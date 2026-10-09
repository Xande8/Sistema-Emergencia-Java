# Sistema de Emergência

## 1. Descrição

Este projeto foi desenvolvido como atividade acadêmica com o objetivo de aplicar conceitos de Programação Orientada a Objetos e modelagem UML na representação de um sistema de emergência.

O sistema representa pacientes, atendimentos e medicamentos, estabelecendo associações entre essas entidades por meio de classes Java.

## 2. Modelagem UML

O diagrama de classes foi elaborado utilizando PlantUML e representa os atributos e os relacionamentos entre as classes do sistema.

Foram definidos os seguintes relacionamentos:

* **Paciente e Atendimento:** um paciente pode possuir nenhum ou vários atendimentos, enquanto cada atendimento está associado a um paciente.
* **Atendimento e Medicamento:** um atendimento pode estar associado a vários medicamentos, e um medicamento pode estar relacionado a vários atendimentos.

O diagrama está disponível na pasta `UML`, nos formatos `.puml`, `.svg` e `.pdf`.

## 3. Classes do sistema

### Paciente

Armazena o CPF e o nome do paciente, além de manter uma lista dos atendimentos associados a ele.

### Atendimento

Armazena o identificador e a data do atendimento, a referência ao paciente correspondente e a lista de medicamentos utilizados.

### Medicamento

Armazena o identificador e o nome do medicamento, além de manter uma lista dos atendimentos aos quais está associado.

### Teste

Contém o método `main`, responsável por instanciar os objetos e demonstrar, em memória, os relacionamentos entre pacientes, atendimentos e medicamentos.

## 4. Tecnologias e conceitos utilizados

* Java;
* Programação Orientada a Objetos;
* Classes, objetos, atributos e métodos;
* Listas utilizando `ArrayList` e `List`;
* Associações entre objetos;
* UML e PlantUML.

## 5. Estrutura do projeto

```text
Sistema-Emergencia-Java/
├── Emergencia/
│   ├── Atendimento.java
│   ├── Medicamento.java
│   ├── Paciente.java
│   └── Teste.java
├── UML/
│   ├── Emergencia.puml
│   ├── Emergencia.svg
│   └── Emergencia.pdf
└── README.md
```
