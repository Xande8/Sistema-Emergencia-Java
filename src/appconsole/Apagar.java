package appconsole;

import java.util.ArrayList;
import com.db4o.ObjectContainer;
import modelo.Paciente;
import util.Util;

public class Apagar {
    public static void main(String[] args) {
        Util.conectar();
        ObjectContainer manager = Util.getManager();
        try {
            Paciente paciente = manager.query(Paciente.class).stream()
                    .filter(p -> p.getCpf().equals("12345678900"))
                    .findFirst().orElse(null);
            if (paciente == null) {
                System.out.println("Paciente escolhido nao encontrado.");
                return;
            }
            new ArrayList<>(paciente.getListaAtendimento()).forEach(atendimento -> {
                new ArrayList<>(atendimento.getListaMedicamento()).forEach(medicamento -> {
                    atendimento.removerMedicamento(medicamento);
                    manager.store(medicamento);
                });
                paciente.removerAtendimento(atendimento);
                manager.delete(atendimento);
            });
            manager.delete(paciente);
            manager.commit();
            System.out.println("Paciente excluido com seus atendimentos e respectivos vinculos.");
            System.out.println("Medicamentos compartilhados foram preservados.");
        } finally {
            Util.desconectar();
        }
    }
}
