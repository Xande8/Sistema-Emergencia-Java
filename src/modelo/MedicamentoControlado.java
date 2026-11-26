package modelo;

public class MedicamentoControlado extends Medicamento {
    private String crm;

    public MedicamentoControlado(String nome, String crm) {
        super(nome);
        this.crm = crm;
    }

    public String getCrm() {
        return crm;
    }

    @Override
    public String toString() {
        return super.toString() + " | CRM: " + crm;
    }
}
