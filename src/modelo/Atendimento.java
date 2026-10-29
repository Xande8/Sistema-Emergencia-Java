package modelo;
import java.util.ArrayList;
import java.util.List;
public class Atendimento {
    private int id;
    private String data;
    private Paciente paciente;
    private List<Medicamento> listaMedicamento;
    public Atendimento(String data, Paciente paciente) {
        this.data = data; this.listaMedicamento = new ArrayList<>(); setPaciente(paciente);
    }
    public int getId() { return id; }
    public String getData() { return data; }
    public Paciente getPaciente() { return paciente; }
    public List<Medicamento> getListaMedicamento() { return listaMedicamento; }
    public void setPaciente(Paciente novoPaciente) {
        if (paciente == novoPaciente) return;
        if (paciente != null) paciente.getListaAtendimento().remove(this);
        paciente = novoPaciente;
        if (novoPaciente != null && !novoPaciente.getListaAtendimento().contains(this))
            novoPaciente.getListaAtendimento().add(this);
    }
    public void adicionarMedicamento(Medicamento medicamento) {
        if (medicamento == null) return;
        if (!listaMedicamento.contains(medicamento)) listaMedicamento.add(medicamento);
        if (!medicamento.getListaAtendimento().contains(this))
            medicamento.getListaAtendimento().add(this);
    }
    public void removerMedicamento(Medicamento medicamento) {
        if (medicamento == null) return;
        listaMedicamento.remove(medicamento);
        medicamento.getListaAtendimento().remove(this);
    }
    @Override
    public String toString() { return "Atendimento ID: " + id + " | Data: " + data; }
}
