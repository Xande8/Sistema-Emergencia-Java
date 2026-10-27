package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Medicamento {
    private int id;
    private String nome;
    private List<Atendimento> listaAtendimento;

    public Medicamento(String nome) {
        this.nome = nome;
        this.listaAtendimento = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public List<Atendimento> getListaAtendimento() { return listaAtendimento; }

    public void adicionarAtendimento(Atendimento atendimento) {
        if (atendimento != null) atendimento.adicionarMedicamento(this);
    }

    public void removerAtendimento(Atendimento atendimento) {
        if (atendimento != null) atendimento.removerMedicamento(this);
    }

    @Override
    public String toString() {
        String ids = listaAtendimento.stream()
                .map(a -> String.valueOf(a.getId()))
                .collect(Collectors.joining(", "));
        return "Medicamento: " + nome + " | ID: " + id
                + " | Atendimentos: [" + ids + "]";
    }
}
