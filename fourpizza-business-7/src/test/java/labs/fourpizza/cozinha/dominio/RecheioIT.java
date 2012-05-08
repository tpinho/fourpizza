package labs.fourpizza.cozinha.dominio;

import java.util.Properties;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

@Stateless
@LocalBean
public class RecheioIT {
	private EJBContainer ejbContainer;
	private Context context;
	private RecheioIT recheioIt;

	@PersistenceContext(unitName = "fourpizza-persistenceunit")
	private EntityManager entityManager;

	@Before
	public void inicializar() throws Exception {
		Properties properties = new Properties();
		properties.put("fourpizzaDatabase", "new://Resource?type=DataSource");
		properties.put("fourpizzaDatabase.JdbcDriver", "org.hsqldb.jdbcDriver");
		properties.put("fourpizzaDatabase.JdbcUrl",
				"jdbc:hsqldb:mem:fourpizzadb");

		ejbContainer = EJBContainer.createEJBContainer(properties);
		context = ejbContainer.getContext();
		recheioIt = (RecheioIT) context
				.lookup("java:global/fourpizza-business/RecheioIT");
	}

	@Test
	public void adicionarPortuguesa() throws Exception {
		recheioIt.adicionarPortuguesaTest();
	}

	public void adicionarPortuguesaTest() throws Exception {
		Recheio portuguesa = new Recheio("Portuguesa");
		portuguesa.adicionarIngrediente(new Ingrediente("Mussarela", 1.10f));
		portuguesa.adicionarIngrediente(new Ingrediente("Ervilha", 1.50f));
		portuguesa.adicionarIngrediente(new Ingrediente("Cebola", 1.05f));
		portuguesa.adicionarIngrediente(new Ingrediente("Ovo", 2.30f));
		entityManager.persist(portuguesa);

		Recheio persistido = entityManager.find(Recheio.class,
				portuguesa.getId());
		assertEquals(4, persistido.getIngredientes().size());
	}

	@After
	public void finalizar() throws Exception {
		ejbContainer.close();
	}
}
