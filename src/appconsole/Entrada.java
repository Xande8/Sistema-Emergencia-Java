package appconsole;

import com.db4o.ObjectContainer;
import modelo.Atendimento;
import modelo.Medicamento;
import modelo.Paciente;

import java.util.Scanner;

/** Entrada pelo terminal, sem loops for explicitos. */
final class Entrada {
    private static final Scanner TECLADO = new Scanner(System.in);

    private Entrada() {}

    static String texto(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            if (!TECLADO.hasNextLine()) {
                throw new IllegalStateException("Entrada do terminal encerrada.");
            }
            String valor = TECLADO.nextLine().trim();
            if (!valor.isEmpty()) return valor;
            System.out.println("Preencha o campo.");
        }
    }

    static int numero(String mensagem) {
        while (true) {
            try {
                return Integer.parseInt(texto(mensagem));
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero inteiro valido.");
            }
        }
    }

    static Paciente buscarPaciente(ObjectContainer db, String cpf) {
        return db.query(Paciente.class).stream()
                .filter(p -> p.getCpf().equals(cpf)).findFirst().orElse(null);
    }

    static Atendimento buscarAtendimento(ObjectContainer db, int id) {
        return db.query(Atendimento.class).stream()
                .filter(a -> a.getId() == id).findFirst().orElse(null);
    }

    static Medicamento buscarMedicamento(ObjectContainer db, int id) {
        return db.query(Medicamento.class).stream()
                .filter(m -> m.getId() == id).findFirst().orElse(null);
    }

    static void listarPacientes(ObjectContainer db) {
        System.out.println("\n=== PACIENTES ===");
        db.query(Paciente.class).forEach(System.out::println);
    }

    static void listarAtendimentos(ObjectContainer db) {
        System.out.println("\n=== ATENDIMENTOS ===");
        db.query(Atendimento.class).forEach(System.out::println);
    }

    static void listarMedicamentos(ObjectContainer db) {
        System.out.println("\n=== MEDICAMENTOS ===");
        db.query(Medicamento.class).forEach(System.out::println);
    }
}
