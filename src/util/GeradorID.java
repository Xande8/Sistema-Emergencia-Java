package util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.EmbeddedObjectContainer;
import com.db4o.ObjectContainer;
import com.db4o.cs.Db4oClientServer;
import com.db4o.events.EventRegistry;
import com.db4o.events.EventRegistryFactory;
import com.db4o.query.Query;


public class GeradorID {
	private static ObjectContainer sequencia; 

	@SuppressWarnings("unused")
	static void ativar(ObjectContainer manager) {
		if (manager == null)
			throw new RuntimeException("Não pode ativar GeradorID - manager desconectado"); // desativado

		if (manager instanceof EmbeddedObjectContainer) {
			sequencia = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "sequencia.db4o");
		} else {
			String ipservidor = Util.getIP();
			sequencia = Db4oClientServer.openClient(Db4oClientServer.newClientConfiguration(), ipservidor, 35000,
					"usuario0", "senha0");
		}

		EventRegistry eventRegistry = EventRegistryFactory.forObjectContainer(manager);

		eventRegistry.creating().addListener((event, args) -> {
			try {
				Object objeto = args.object(); 
				Class<?> classe = objeto.getClass();
				Field campo = null;
				for (Field f : getAllFieldsList(classe)) {
					if (f.getName().equals("id")) {
						campo = f;
						break;
					}
				}

				if (campo != null) { 
					Class<?> cls = objeto.getClass();
					String nomedaclasse = "";
					if(cls.getSuperclass()==Object.class)
						nomedaclasse = objeto.getClass().getName();
					else
						nomedaclasse = cls.getSuperclass().getName();
					
					RegistroID registro = lerRegistroID(nomedaclasse); 
					registro.incrementarID(); 
					campo.setAccessible(true); 
					campo.setInt(objeto, registro.getid()); 
					sequencia.store(registro);
					sequencia.commit();				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
			}
		});

		eventRegistry.created().addListener((event, args) -> {
			manager.ext().purge();
			sequencia.ext().purge();
		});

		eventRegistry.closing().addListener((event, args) -> {
			if (sequencia != null && !sequencia.ext().isClosed())
				sequencia.close(); 
		});

		eventRegistry.queryFinished().addListener((event, args) -> {
		
			manager.ext().purge();
			sequencia.ext().purge();
		});
	}

	private static RegistroID lerRegistroID(String nomeclasse) {
		Query q = sequencia.query();
		q.constrain(RegistroID.class);
		q.descend("nomedaclasse").constrain(nomeclasse);
		List<RegistroID> resultados = q.execute();
		if (resultados.size() > 0)
			return resultados.get(0);
		else
			return new RegistroID(nomeclasse);
	}

	
	private static <X> List<Field> getAllFieldsList(final Class<X> cls) {

		final List<Field> allFields = new ArrayList<>();
		Class<?> currentClass = cls;
		while (currentClass != null) {
			final Field[] declaredFields = currentClass.getDeclaredFields();
			Collections.addAll(allFields, declaredFields);
			currentClass = currentClass.getSuperclass();
		}
		return allFields;
	}
} 

class RegistroID {
	private String nomedaclasse;
	private int ultimoid;

	public RegistroID(String nomedaclasse) {
		this.nomedaclasse = nomedaclasse;
		this.ultimoid = 0;
	}
	public RegistroID(String nomedaclasse, int valorInicial) {
		this.nomedaclasse = nomedaclasse;
		this.ultimoid = valorInicial;
	}

	public String getNomedaclasse() {
		return nomedaclasse;
	}

	public int getid() {
		return ultimoid;
	}

	public void incrementarID() {
		ultimoid++;
	}

	@Override
	public String toString() {
		return "RegistroID [nomedaclasse=" + nomedaclasse + ", ultimoid=" + ultimoid + "]";
	}

}
