package Emergencia;

import java.util.ArrayList;
import java.util.List;

public class Atendimento {

    private int id;
    private String data;
    private Paciente paciente;
    private List<Medicamento> listaMedicamento;

    public Atendimento(int id, String data, Paciente paciente) {
        this.id = id;
        this.data = data;
        this.listaMedicamento = new ArrayList<>();

        setPaciente(paciente);
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;

        if (paciente != null && !paciente.getListaAtendimento().contains(this)) {
            paciente.getListaAtendimento().add(this);
        }
    }

    public void adicionarMedicamento(Medicamento medicamento) {
        if (!listaMedicamento.contains(medicamento)) {
            listaMedicamento.add(medicamento);
        }

        if (!medicamento.getListaAtendimento().contains(this)) {
            medicamento.getListaAtendimento().add(this);
        }
    }

    public int getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public List<Medicamento> getListaMedicamento() {
        return listaMedicamento;
    }

    @Override
    public String toString() {
        return "Atendimento: " + id + " | Data: " + data;
    }
}
