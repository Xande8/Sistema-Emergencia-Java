package appconsole;

import com.db4o.ObjectContainer;
import modelo.Atendimento;
import modelo.Medicamento;
import modelo.Paciente;
import util.Util;

public class Cadastrar {
    public static void main(String[] args) {
        Util.conectar();
        ObjectContainer manager = Util.getManager();
        try {
            if (!manager.query(Paciente.class).isEmpty()
                    || !manager.query(Atendimento.class).isEmpty()
                    || !manager.query(Medicamento.class).isEmpty()) {
                System.out.println("Banco ja possui objetos. O cadastro de exemplo nao foi repetido.");
                return;
            }

            Paciente joao = new Paciente("12345678900", "Joao da Silva");
            Paciente maria = new Paciente("98765432100", "Maria Souza");
            Paciente ana = new Paciente("11122233344", "Ana Lima");

            Medicamento dipirona = new Medicamento("Dipirona");
            Medicamento soro = new Medicamento("Soro fisiologico");
            Medicamento paracetamol = new Medicamento("Paracetamol");

            Atendimento a1 = new Atendimento("27/10/2026", joao);
            Atendimento a2 = new Atendimento("27/10/2026", maria);
            Atendimento a3 = new Atendimento("28/10/2026", joao);
            Atendimento a4 = new Atendimento("29/10/2026", ana);

            a1.adicionarMedicamento(dipirona);
            a1.adicionarMedicamento(soro);
            a2.adicionarMedicamento(dipirona);
            a3.adicionarMedicamento(paracetamol);
            a4.adicionarMedicamento(soro);

            manager.store(joao);
            manager.store(maria);
            manager.store(ana);
            manager.store(a1);
            manager.store(a2);
            manager.store(a3);
            manager.store(a4);
            manager.store(dipirona);
            manager.store(soro);
            manager.store(paracetamol);
            manager.commit();
            System.out.println("Cadastro concluido: 3 pacientes, 4 atendimentos e 3 medicamentos.");
        } finally {
            Util.desconectar();
        }
    }
}
