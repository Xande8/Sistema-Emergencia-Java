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
                .filter(a -> a.getPaciente() != null && "12345678900".equals(a.getPaciente().getCpf()))
                .filter(a -> a.getListaMedicamento().stream()
                    .anyMatch(m -> "Dipirona".equalsIgnoreCase(m.getNome())))
                .findFirst().orElse(null);
            if (atendimento == null) {
                System.out.println("Vínculo entre João e Dipirona não encontrado.");
                return;
            }
            Medicamento medicamento = atendimento.getListaMedicamento().stream()
                .filter(m -> "Dipirona".equalsIgnoreCase(m.getNome())).findFirst().orElseThrow();
            System.out.println("Antes: " + atendimento + " | Dipirona associada.");
            atendimento.removerMedicamento(medicamento);
            manager.store(atendimento);
            manager.store(medicamento);
            manager.commit();
            System.out.println("Depois: vínculo com Dipirona removido com sucesso.");
            System.out.println("O atendimento e o medicamento continuam cadastrados.");
        } finally {
            Util.desconectar();
        }
    }
}
