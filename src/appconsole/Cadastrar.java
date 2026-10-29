package appconsole;

import com.db4o.ObjectContainer;
import modelo.Atendimento;
import modelo.Medicamento;
import modelo.Paciente;
import util.Util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Cadastro de demonstracao e cadastro interativo no Db4o.
 * Os IDs sao atribuidos automaticamente pelo GeradorID ao salvar os objetos.
 */
public class Cadastrar {
    private static final DateTimeFormatter DATA_BR = DateTimeFormatter
            .ofPattern("dd/MM/uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    public static void main(String[] args) {
        Util.conectar();
        ObjectContainer manager = Util.getManager();
        try {
            int opcao;
            do {
                System.out.println("\n=== SISTEMA DE EMERGENCIA - CADASTRO ===");
                System.out.println("1 - Cadastrar dados demonstrativos");
                System.out.println("2 - Cadastrar paciente");
                System.out.println("3 - Cadastrar medicamento");
                System.out.println("4 - Cadastrar atendimento");
                System.out.println("5 - Associar medicamento a atendimento");
                System.out.println("6 - Consultar objetos cadastrados");
                System.out.println("0 - Sair");
                opcao = Entrada.numero("Escolha uma opcao: ");

                switch (opcao) {
                    case 1 -> cadastrarDemonstracao(manager);
                    case 2 -> cadastrarPaciente(manager);
                    case 3 -> cadastrarMedicamento(manager);
                    case 4 -> cadastrarAtendimento(manager);
                    case 5 -> associarMedicamento(manager);
                    case 6 -> listar(manager);
                    case 0 -> System.out.println("Cadastro encerrado.");
                    default -> System.out.println("Opcao invalida.");
                }
            } while (opcao != 0);
        } finally {
            Util.desconectar();
        }
    }

    private static void cadastrarDemonstracao(ObjectContainer manager) {
        if (!manager.query(Paciente.class).isEmpty()
                || !manager.query(Atendimento.class).isEmpty()
                || !manager.query(Medicamento.class).isEmpty()) {
            System.out.println("O banco ja possui objetos. Cadastro demonstrativo nao repetido.");
            System.out.println("Use as opcoes 2 a 5 para adicionar novos registros.");
            return;
        }

        Paciente joao = new Paciente("12345678900", "Joao da Silva");
        Paciente maria = new Paciente("98765432100", "Maria Souza");
        Paciente ana = new Paciente("11122233344", "Ana Lima");

        Medicamento dipirona = new Medicamento("Dipirona");
        Medicamento soro = new Medicamento("Soro fisiologico");
        Medicamento paracetamol = new Medicamento("Paracetamol");

        Atendimento a1 = new Atendimento("27/10/2026", joao);
        Atendimento a2 = new Atendimento("27/10/2026", maria);
        Atendimento a3 = new Atendimento("28/10/2026", joao);
        Atendimento a4 = new Atendimento("29/10/2026", ana);

        a1.adicionarMedicamento(dipirona);
        a1.adicionarMedicamento(soro);
        a2.adicionarMedicamento(dipirona);
        a3.adicionarMedicamento(paracetamol);
        a4.adicionarMedicamento(soro);

        manager.store(joao);
        manager.store(maria);
        manager.store(ana);
        manager.store(a1);
        manager.store(a2);
        manager.store(a3);
        manager.store(a4);
        manager.store(dipirona);
        manager.store(soro);
        manager.store(paracetamol);
        manager.commit();
        System.out.println("Cadastro concluido: 3 pacientes, 4 atendimentos e 3 medicamentos.");
    }

    private static void cadastrarPaciente(ObjectContainer manager) {
        String cpf = Entrada.texto("CPF (11 digitos): ");
        if (!cpf.matches("\\d{11}")) {
            System.out.println("CPF deve conter 11 digitos numericos.");
            return;
        }
        if (Entrada.buscarPaciente(manager, cpf) != null) {
            System.out.println("Ja existe um paciente cadastrado com esse CPF.");
            return;
        }
        String nome = Entrada.texto("Nome do paciente: ");
        Paciente paciente = new Paciente(cpf, nome);
        manager.store(paciente);
        manager.commit();
        System.out.println("Paciente salvo no Db4o: " + paciente);
    }

    private static void cadastrarMedicamento(ObjectContainer manager) {
        String nome = Entrada.texto("Nome do medicamento: ");
        Medicamento medicamento = new Medicamento(nome);
        manager.store(medicamento);
        manager.commit();
        System.out.println("Medicamento salvo no Db4o: " + medicamento);
    }

    private static void cadastrarAtendimento(ObjectContainer manager) {
        if (manager.query(Paciente.class).isEmpty()) {
            System.out.println("Cadastre um paciente primeiro.");
            return;
        }
        System.out.println("\nPacientes disponiveis:");
        Entrada.listarPacientes(manager);
        String cpf = Entrada.texto("CPF do paciente do atendimento: ");
        Paciente paciente = Entrada.buscarPaciente(manager, cpf);
        if (paciente == null) {
            System.out.println("Paciente nao encontrado.");
            return;
        }
        String data = Entrada.texto("Data do atendimento (dd/MM/aaaa): ");
        try {
            LocalDate.parse(data, DATA_BR);
        } catch (DateTimeParseException e) {
            System.out.println("Data invalida. Utilize uma data real em dd/MM/aaaa.");
            return;
        }
        Atendimento atendimento = new Atendimento(data, paciente);
        manager.store(atendimento);
        manager.store(paciente);
        manager.commit();
        System.out.println("Atendimento salvo no Db4o: " + atendimento);
    }

    private static void associarMedicamento(ObjectContainer manager) {
        if (manager.query(Atendimento.class).isEmpty() || manager.query(Medicamento.class).isEmpty()) {
            System.out.println("Cadastre pelo menos um atendimento e um medicamento primeiro.");
            return;
        }
        Entrada.listarAtendimentos(manager);
        int idAtendimento = Entrada.numero("ID do atendimento: ");
        Entrada.listarMedicamentos(manager);
        int idMedicamento = Entrada.numero("ID do medicamento: ");
        Atendimento atendimento = Entrada.buscarAtendimento(manager, idAtendimento);
        Medicamento medicamento = Entrada.buscarMedicamento(manager, idMedicamento);
        if (atendimento == null || medicamento == null) {
            System.out.println("Atendimento ou medicamento nao encontrado.");
            return;
        }
        boolean vinculoExistente = atendimento.getListaMedicamento().stream()
                .anyMatch(m -> m.getId() == idMedicamento);
        if (vinculoExistente) {
            System.out.println("Esses objetos ja estao associados.");
            return;
        }
        atendimento.adicionarMedicamento(medicamento);
        manager.store(atendimento);
        manager.store(medicamento);
        manager.commit();
        System.out.println("Relacionamento salvo no Db4o: " + atendimento);
    }

    private static void listar(ObjectContainer manager) {
        Entrada.listarPacientes(manager);
        Entrada.listarAtendimentos(manager);
        Entrada.listarMedicamentos(manager);
    }
}
