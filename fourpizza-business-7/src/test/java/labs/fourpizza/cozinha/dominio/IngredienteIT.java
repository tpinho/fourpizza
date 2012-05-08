package labs.fourpizza.cozinha.dominio;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Properties;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@Stateless
@LocalBean
public class IngredienteIT {
	private EJBContainer container;
	
	@PersistenceContext(unitName = "fourpizza-persistenceunit")
	private EntityManager entityManager;
	private IngredienteIT ingredienteIT;

	@Before
	public void inicializar() throws Exception {
		Properties properties = new Properties();
		properties.put("fourpizzaDatabase", "new://Resource?type=DataSource");
		properties.put("fourpizzaDatabase.JdbcDriver", "org.hsqldb.jdbcDriver");
		properties.put("fourpizzaDatabase.JdbcUrl",
				"jdbc:hsqldb:mem:fourpizzadb");

		container = EJBContainer.createEJBContainer(properties);
		Context context = container.getContext();

		ingredienteIT = (IngredienteIT) context
				.lookup("java:global/fourpizza-business/IngredienteIT");
	}

	@Test
	public void persistindoMussarela() throws Exception {
		ingredienteIT.persistindoMussarelaTest();
	}

	public void persistindoMussarelaTest() throws Exception {
		Ingrediente mussarela = new Ingrediente("Mussarela",
				"Mussarela Simples", 1.10f);
		entityManager.persist(mussarela);
		assertNotNull(entityManager.find(Ingrediente.class, mussarela.getId()));
	}

	@Test
	public void persistindoIngredientesIguais() throws Exception {
		ingredienteIT.persistindoIngredientesIguaisTest();
	}

	public void persistindoIngredientesIguaisTest() throws Exception {
		try {
			entityManager.persist(new Ingrediente("Mussarela", null, 1.10f));
			entityManager.persist(new Ingrediente("Mussarela",
					null, 1.10f));
			fail("Não pode haver dois ingredientes com o mesmo nome.");
		} catch (PersistenceException pe) {
			
		}
		
	}
	
	@After
	public void encerrar() {
		container.close();
	}
}
