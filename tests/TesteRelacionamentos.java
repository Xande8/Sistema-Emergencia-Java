import modelo.Atendimento;
import modelo.Medicamento;
import modelo.Paciente;

public class TesteRelacionamentos {
    private static void validar(boolean condicao, String mensagem) {
        if (!condicao) throw new AssertionError(mensagem);
    }
    public static void main(String[] args) {
        Paciente joao = new Paciente("12345678900", "João");
        Paciente maria = new Paciente("98765432100", "Maria");
        Medicamento dipirona = new Medicamento("Dipirona");
        Atendimento atendimento = new Atendimento("29/10/2026", joao);
        validar(joao.getListaAtendimento().contains(atendimento), "Atendimento não associado ao paciente");
        joao.adicionarAtendimento(atendimento);
        validar(joao.getListaAtendimento().size() == 1, "Atendimento duplicado");
        atendimento.adicionarMedicamento(dipirona);
        atendimento.adicionarMedicamento(dipirona);
        validar(atendimento.getListaMedicamento().size() == 1, "Medicamento duplicado no atendimento");
        validar(dipirona.getListaAtendimento().contains(atendimento), "Associação inversa ausente");
        atendimento.removerMedicamento(dipirona);
        validar(atendimento.getListaMedicamento().isEmpty(), "Medicamento não desvinculado");
        validar(dipirona.getListaAtendimento().isEmpty(), "Associação inversa não desvinculada");
        atendimento.setPaciente(maria);
        validar(!joao.getListaAtendimento().contains(atendimento), "Paciente anterior ainda está associado");
        validar(maria.getListaAtendimento().contains(atendimento), "Novo paciente não está associado");
        maria.removerAtendimento(atendimento);
        validar(atendimento.getPaciente() == null, "Paciente não desvinculado");
        validar(maria.getListaAtendimento().isEmpty(), "Lista do paciente não foi atualizada");
        validar(joao.toString().contains("João"), "Representação de paciente inválida");
        validar(atendimento.toString().contains("29/10/2026"), "Representação de atendimento inválida");
        validar(dipirona.toString().contains("Dipirona"), "Representação de medicamento inválida");
        System.out.println("Testes de relacionamentos concluídos com sucesso.");
    }
}
