package appconsole;
import java.util.Comparator;
import java.util.stream.Collectors;
import com.db4o.ObjectContainer;
import modelo.Atendimento;
import modelo.Medicamento;
import modelo.Paciente;
import util.Util;
public class Listar {
    public static void main(String[] args) {
        Util.conectar();
        ObjectContainer manager = Util.getManager();
        try {
            System.out.println("\n=== PACIENTES E SEUS ATENDIMENTOS ===");
            manager.query(Paciente.class).stream().sorted(Comparator.comparing(Paciente::getNome))
                .forEach(paciente -> {
                    System.out.println(paciente);
                    if (paciente.getListaAtendimento().isEmpty()) System.out.println("  Sem atendimentos.");
                    else paciente.getListaAtendimento().stream()
                        .sorted(Comparator.comparingInt(Atendimento::getId))
                        .forEach(atendimento -> System.out.println(
                            "  Atendimento #" + atendimento.getId() + " | Data: " + atendimento.getData()));
                });
            System.out.println("\n=== ATENDIMENTOS, PACIENTES E MEDICAMENTOS ===");
            manager.query(Atendimento.class).stream().sorted(Comparator.comparingInt(Atendimento::getId))
                .forEach(atendimento -> {
                    System.out.println(atendimento);
                    System.out.println("  Paciente: " + (atendimento.getPaciente() == null
                        ? "Não associado" : atendimento.getPaciente().getNome()));
                    String medicamentos = atendimento.getListaMedicamento().stream()
                        .map(Medicamento::getNome).sorted().collect(Collectors.joining(", "));
                    System.out.println("  Medicamentos: " + (medicamentos.isEmpty() ? "Nenhum" : medicamentos));
                });
            System.out.println("\n=== MEDICAMENTOS E SEUS ATENDIMENTOS ===");
            manager.query(Medicamento.class).stream().sorted(Comparator.comparing(Medicamento::getNome))
                .forEach(medicamento -> {
                    System.out.println(medicamento);
                    String atendimentos = medicamento.getListaAtendimento().stream()
                        .map(Atendimento::getId).sorted().map(String::valueOf)
                        .collect(Collectors.joining(", "));
                    System.out.println("  Atendimentos: " + (atendimentos.isEmpty() ? "Nenhum" : atendimentos));
                });
        } finally {
            Util.desconectar();
        }
    }
}
