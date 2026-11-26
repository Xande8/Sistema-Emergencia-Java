import modelo.Atendimento;
import modelo.Medicamento;
import modelo.MedicamentoControlado;
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
        validar(atendimento.toString().contains("Medicamentos: [Dipirona]"),
                "toString de Atendimento nao exibe os medicamentos");
        validar(joao.toString().contains("Atendimentos: ["),
                "toString de Paciente nao exibe os atendimentos");
        validar(dipirona.toString().contains("Atendimentos: ["),
                "toString de Medicamento nao exibe os atendimentos");
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
        MedicamentoControlado controlado = new MedicamentoControlado("Morfina", "CRM-PB 12345");
        validar(controlado instanceof Medicamento, "MedicamentoControlado nao herda de Medicamento");
        validar("CRM-PB 12345".equals(controlado.getCrm()), "Getter de CRM incorreto");
        validar(controlado.toString().contains("CRM: CRM-PB 12345"), "toString nao exibe o CRM");
        validar(controlado.toString().contains("Medicamento: Morfina"),
                "toString nao reutiliza a representacao da superclasse");

        Atendimento atendimentoControlado = new Atendimento("30/10/2026", maria);
        atendimentoControlado.adicionarMedicamento(controlado);
        validar(atendimentoControlado.getListaMedicamento().contains(controlado),
                "Vinculo com atendimento ausente");
        validar(controlado.getListaAtendimento().contains(atendimentoControlado),
                "Vinculo inverso ausente");
        atendimentoControlado.removerMedicamento(controlado);
        validar(!atendimentoControlado.getListaMedicamento().contains(controlado),
                "Vinculo nao removido do atendimento");
        validar(!controlado.getListaAtendimento().contains(atendimentoControlado),
                "Vinculo inverso nao removido");

        System.out.println("Testes de relacionamentos e heranca concluidos com sucesso.");
    }
}
