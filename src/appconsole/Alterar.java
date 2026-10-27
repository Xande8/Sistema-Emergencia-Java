package appconsole;

import com.db4o.ObjectContainer;
import modelo.Atendimento;
import modelo.Medicamento;
import util.Util;

public class Alterar {
    public static void main(String[] args) {
        Util.conectar();
        ObjectContainer manager = Util.getManager();
        try {
            Atendimento atendimento = manager.query(Atendimento.class).stream()
                    .filter(a -> a.getListaMedicamento().stream()
                            .anyMatch(m -> m.getNome().equals("Dipirona")))
                    .findFirst().orElse(null);
            if (atendimento == null) {
                System.out.println("Nenhum atendimento com Dipirona foi encontrado.");
                return;
            }
            Medicamento medicamento = atendimento.getListaMedicamento().stream()
                    .filter(m -> m.getNome().equals("Dipirona"))
                    .findFirst().orElse(null);

            System.out.println("Antes: " + atendimento);
            atendimento.removerMedicamento(medicamento);
            manager.store(atendimento);
            manager.store(medicamento);
            manager.commit();
            System.out.println("Vinculo removido sem excluir nenhum dos objetos.");
            System.out.println("Depois: " + atendimento);
        } finally {
            Util.desconectar();
        }
    }
}
