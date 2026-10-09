package Emergencia;

import java.util.ArrayList;
import java.util.List;

public class Medicamento {

    private int id;
    private String nome;
    private List<Atendimento> listaAtendimento;

    public Medicamento(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.listaAtendimento = new ArrayList<>();
    }

    public void adicionarAtendimento(Atendimento atendimento) {
        if (!listaAtendimento.contains(atendimento)) {
            listaAtendimento.add(atendimento);
        }

        if (!atendimento.getListaMedicamento().contains(this)) {
            atendimento.getListaMedicamento().add(this);
        }
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public List<Atendimento> getListaAtendimento() {
        return listaAtendimento;
    }

    @Override
    public String toString() {
        return "Medicamento: " + nome + " | ID: " + id;
    }
}
