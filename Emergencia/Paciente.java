package Emergencia;

import java.util.ArrayList;
import java.util.List;

public class Paciente {

    private String cpf;
    private String nome;
    private List<Atendimento> listaAtendimento;

    public Paciente(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
        this.listaAtendimento = new ArrayList<>();
    }

    public void adicionarAtendimento(Atendimento atendimento) {
        if (!listaAtendimento.contains(atendimento)) {
            listaAtendimento.add(atendimento);
        }

        if (atendimento.getPaciente() != this) {
            atendimento.setPaciente(this);
        }
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public List<Atendimento> getListaAtendimento() {
        return listaAtendimento;
    }

    @Override
    public String toString() {
        return "Paciente: " + nome + " | CPF: " + cpf;
    }
}
