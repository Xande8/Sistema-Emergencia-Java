package util;

import javax.swing.JOptionPane;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;

import modelo.Paciente;
import modelo.Atendimento;
import modelo.Medicamento;

public class Util {
	private static ObjectContainer manager;
	private static String ip="localhost";

	public static void conectar() {
		if (manager != null)
			return;
		try {
			conectarBancoLocal(); 
			GeradorID.ativar(manager); 
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco " + ip + "\n" + e.getMessage());
			System.exit(0);
		}
	}

	private static void conectarBancoLocal() {
		if (manager != null)
			return; 

		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		config.common().messageLevel(0);
		config.common().objectClass(Paciente.class).cascadeOnDelete(false);
		config.common().objectClass(Paciente.class).cascadeOnUpdate(true);
		config.common().objectClass(Paciente.class).cascadeOnActivate(true);
		config.common().objectClass(Atendimento.class).cascadeOnDelete(false);
		config.common().objectClass(Atendimento.class).cascadeOnUpdate(true);
		config.common().objectClass(Atendimento.class).cascadeOnActivate(true);
		config.common().objectClass(Medicamento.class).cascadeOnDelete(false);
		config.common().objectClass(Medicamento.class).cascadeOnUpdate(true);
		config.common().objectClass(Medicamento.class).cascadeOnActivate(true);
		config.common().activationDepth(Integer.MAX_VALUE); // profundidade de ativacao para acessar os objetos

		try {
			manager = Db4oEmbedded.openFile(config, "emergencia.db4o");
			System.out.println("banco conectado. ");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco local \n" + e.getMessage());
			System.exit(0);
		}
	}

	public static void desconectar() {
		if (manager != null) {
			manager.close();
			manager = null;
		}
	}

	public static ObjectContainer getManager() {
		return manager;
	}

	public static String getIP() {
		return ip;
	}

}
