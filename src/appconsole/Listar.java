package appconsole;

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
            System.out.println("\n=== PACIENTES (e atendimentos) ===");
            manager.query(Paciente.class).forEach(System.out::println);

            System.out.println("\n=== ATENDIMENTOS (paciente e medicamentos) ===");
            manager.query(Atendimento.class).forEach(System.out::println);

            System.out.println("\n=== MEDICAMENTOS (e atendimentos) ===");
            manager.query(Medicamento.class).forEach(System.out::println);
        } finally {
            Util.desconectar();
        }
    }
}
