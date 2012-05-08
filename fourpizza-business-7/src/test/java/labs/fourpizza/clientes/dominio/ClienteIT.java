package labs.fourpizza.clientes.dominio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import labs.fourpizza.clientes.dominio.CartaoCreditoInfo.Bandeira;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@Stateless
@LocalBean
public class ClienteIT {
	private EJBContainer ejbContainer;
	private Context context;
	private ClienteIT clienteIT;

	@PersistenceContext(unitName = "fourpizza-persistenceunit")
	private EntityManager entityManager;

	@Resource(name = "fourpizzaDatabaseNonJta")
	private DataSource dataSource;

	@Before
	public void inicializar() throws Exception {
		Properties properties = new Properties();
		properties.put("fourpizzaDatabase", "new://Resource?type=DataSource");
		properties.put("fourpizzaDatabase.JdbcDriver", "org.hsqldb.jdbcDriver");
		properties.put("fourpizzaDatabase.JdbcUrl",
				"jdbc:hsqldb:mem:fourpizzadb");

		ejbContainer = EJBContainer.createEJBContainer(properties);
		context = ejbContainer.getContext();
		clienteIT = (ClienteIT) context
				.lookup("java:global/fourpizza-business/ClienteIT");
	}

	@Test
	public void criandoClienteValido() throws Exception {
		clienteIT.criandoClienteValidoTest();
	}

	public void criandoClienteValidoTest() throws Exception {
		Cliente clienteValido = new Cliente();
		clienteValido.setNome("Gabriel Ozeas");
		clienteValido.setRg("11.111.111-11");
		clienteValido.setCpf("222.222.222-22");
		clienteValido.setEmail("gabriel.ozeas@4linux.com.br");
		clienteValido.setDataNascimento(new Date());

		entityManager.persist(clienteValido);
	}

	@Test(expected=EJBException.class)
	public void doisClientesMesmoEmail() throws Exception {
		clienteIT.doisClientesMesmoEmailTest();
	}

	public void doisClientesMesmoEmailTest() throws Exception {
		Cliente c1 = new Cliente("Gabriel Ozeas", "11.111.111-11",
				"222.222.222-22", "gabriel.ozeas@4linux.com.br");
		entityManager.persist(c1);

		Cliente c2 = new Cliente("Gabriel Ozeas de Oliveira", "33.333.333-33",
				"444.444.444-44", "gabriel.ozeas@4linux.com.br");
		entityManager.persist(c2);
	}

	@Test(expected = EJBException.class)
	public void criandoClienteSemNome() throws Exception {
		clienteIT.criandoClienteSemNomeTest();
	}

	public void criandoClienteSemNomeTest() throws Exception {
		Cliente cliente = new Cliente();
		entityManager.persist(cliente);
	}

	@Test
	public void criandoClienteValidoComEndereco() throws Exception {
		clienteIT.criandoClienteValidoComEnderecoTest();
	}

	public void criandoClienteValidoComEnderecoTest() throws Exception {
		Endereco endereco = new Endereco();
		endereco.setLogradouro("Teixeira da Silva");
		endereco.setNumero("660");
		endereco.setCep("04002-033");

		Cliente clienteValido = new Cliente();
		clienteValido.setNome("Gabriel Ozeas");
		clienteValido.setRg("11.111.111-11");
		clienteValido.setCpf("222.222.222-22");
		clienteValido.setEmail("gabriel.ozeas@4linux.com.br");
		clienteValido.setDataNascimento(new Date());
		clienteValido.setEndereco(endereco);

		entityManager.persist(clienteValido);

		Cliente persistido = entityManager.find(Cliente.class,
				clienteValido.getId());
		assertNotNull(persistido);

		assertEquals("Teixeira da Silva", persistido.getEndereco()
				.getLogradouro());
	}

	@Test
	public void criandoClienteValidoComCartao() throws Exception {
		clienteIT.criandoClienteValidoComCartaoTest();
	}

	public void criandoClienteValidoComCartaoTest() throws Exception {
		Cliente cliente = new Cliente();
		cliente.setNome("Gabriel Ozeas");
		cliente.setRg("11.111.111-11");
		cliente.setCpf("222.222.222-22");
		cliente.setEmail("gabriel.ozeas@4linux.com.br");
		cliente.setDataNascimento(new Date());

		CartaoCreditoInfo ccInfo = new CartaoCreditoInfo();
		ccInfo.setBandeira(Bandeira.AMEX);
		ccInfo.setCodigoVerificador("254");
		ccInfo.setDataVencimento(new Date());
		ccInfo.setNome("Gabriel Ozeas");
		ccInfo.setNumero("546856957452");

		cliente.setCartaoCreditoInfo(ccInfo);
		ccInfo.setCliente(cliente);

		entityManager.persist(cliente);

		Cliente persistido = entityManager.find(Cliente.class, cliente.getId());
		assertNotNull(persistido);
		assertNotNull(persistido.getCartaoCreditoInfo());

		verificarCartaoInfo(persistido);
	}

	private void verificarCartaoInfo(Cliente cliente) throws Exception {
		entityManager.flush();
		Connection connection = dataSource.getConnection();

		Statement stmt = connection.createStatement();
		ResultSet resultSet = stmt
				.executeQuery("SELECT * FROM CartaoCreditoInfo "
						+ "as c where c.cliente_id = " + cliente.getId());

		resultSet.next();
		assertEquals("AMEX", resultSet.getString("bandeira"));
		assertEquals(new Long(cliente.getId()),
				new Long(resultSet.getLong("cliente_id")));
	}
	
	@After
	public void encerrar() {
		ejbContainer.close();
	}
}
