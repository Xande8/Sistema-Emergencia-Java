package Emergencia;

public class Teste {

    public static void main(String[] args) {

        // Criando um paciente
        Paciente paciente = new Paciente(
            "12345678900",
            "João da Silva"
        );

        // Criando os medicamentos
        Medicamento dipirona = new Medicamento(
            1,
            "Dipirona"
        );

        Medicamento paracetamol = new Medicamento(
            2,
            "Paracetamol"
        );

        // Criando o atendimento
        Atendimento atendimento = new Atendimento(
            1,
            "08/10/2026",
            paciente
        );

        // Adicionando os medicamentos ao atendimento
        atendimento.adicionarMedicamento(dipirona);
        atendimento.adicionarMedicamento(paracetamol);

        // Exibindo os dados
        System.out.println("===== SISTEMA DE EMERGÊNCIA =====");

        System.out.println("\nPaciente:");
        System.out.println("Nome: " + paciente.getNome());
        System.out.println("CPF: " + paciente.getCpf());

        System.out.println("\nAtendimentos do paciente:");

        for (Atendimento a : paciente.getListaAtendimento()) {

            System.out.println(
                "ID: " + a.getId()
                + " | Data: " + a.getData()
            );

            System.out.println("Medicamentos utilizados:");

            for (Medicamento m : a.getListaMedicamento()) {
                System.out.println(
                    "- " + m.getNome()
                );
            }
        }

        System.out.println("\nRelacionamento dos medicamentos:");

        System.out.println(
            dipirona.getNome()
            + " está relacionado a "
            + dipirona.getListaAtendimento().size()
            + " atendimento(s)."
        );

        System.out.println(
            paracetamol.getNome()
            + " está relacionado a "
            + paracetamol.getListaAtendimento().size()
            + " atendimento(s)."
        );
    }
}