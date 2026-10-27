package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Paciente {
    private String cpf;
    private String nome;
    private List<Atendimento> listaAtendimento;

    public Paciente(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
        this.listaAtendimento = new ArrayList<>();
    }

    public String getCpf() { return cpf; }
    public String getNome() { return nome; }
    public List<Atendimento> getListaAtendimento() { return listaAtendimento; }

    public void adicionarAtendimento(Atendimento atendimento) {
        if (atendimento != null) atendimento.setPaciente(this);
    }

    public void removerAtendimento(Atendimento atendimento) {
        if (atendimento != null && atendimento.getPaciente() == this) {
            atendimento.setPaciente(null);
        }
    }

    @Override
    public String toString() {
        String ids = listaAtendimento.stream()
                .map(a -> String.valueOf(a.getId()))
                .collect(Collectors.joining(", "));
        return "Paciente: " + nome + " | CPF: " + cpf
                + " | Atendimentos: [" + ids + "]";
    }
}
