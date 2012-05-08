package labs.fourpizza.cozinha.dominio;

import java.util.Properties;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import labs.fourpizza.cozinha.dominio.Produto;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@Stateless
@LocalBean
public class ProdutoIT {
	private EJBContainer ejbContainer;
	private Context context;
	private ProdutoIT test;

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
		test = (ProdutoIT) context.lookup("java:global/fourpizza-business"
				+ "/ProdutoIT");
	}

	@Test
	public void adicionarNovoProduto() throws Exception {
		test.adicionarNovoProdutoTest();
	}

	public void adicionarNovoProdutoTest() throws Exception {
		Produto p1 = new Produto("Refri 2l Coca",
				"Refrigerante Coca-cola 2Litros", 5.50f);
		entityManager.persist(p1);
	}
	

	@After
	public void finalizar() throws Exception {
		ejbContainer.close();
	}
}
