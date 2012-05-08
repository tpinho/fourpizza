package labs.fourpizza.clientes;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import labs.fourpizza.clientes.dominio.Cliente;

import org.junit.Before;
import org.junit.Test;

public class GerenciadorClientesTest {
	private GerenciadorClientes clientes;
	
	@Before
	public void inicializar() {
		clientes = new GerenciadorClientes();
		EntityManager entityManager = mock(EntityManager.class);
		clientes.setEntityManager(entityManager);
	}
	
	@Test
	public void persistindoClienteValido() throws Exception {
		EntityManager entityManager = clientes.getEntityManager();
		Query query = mock(Query.class);
		when(entityManager.createNamedQuery("Cliente.buscarClientePorEmail")).thenReturn(query);
		
		when(query.getSingleResult()).thenReturn(null);
		
		Cliente novoCliente = new Cliente("Gabriel", null, null, "gabriel.ozeas@4linux.com.br");
		clientes.registrar(novoCliente);
		
		verify(entityManager).persist(novoCliente);
	}
	
	@Test(expected=ClienteException.class)
	public void persistindoEmailJaRegistrado() throws Exception {
		EntityManager manager = clientes.getEntityManager();
		Query query = mock(Query.class);
		when(manager.createNamedQuery("Cliente.buscarClientePorEmail")).thenReturn(query);
		
		Cliente novoCliente = new Cliente("Gabriel", null, null, "gabriel.ozeas@4linux.com.br");
		when(query.getSingleResult()).thenReturn(novoCliente);
		
		clientes.registrar(novoCliente);
	}
}
