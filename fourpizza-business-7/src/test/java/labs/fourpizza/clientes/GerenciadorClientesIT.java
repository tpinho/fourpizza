package labs.fourpizza.clientes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import labs.fourpizza.clientes.dominio.Cliente;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GerenciadorClientesIT {
	private EJBContainer ejbContainer;
	private Context context;
	private GerenciadorClientes clientes;
	
	@Before
	public void inicializar() throws Exception {
		ejbContainer = EJBContainer.createEJBContainer();
		context = ejbContainer.getContext();
		clientes = (GerenciadorClientes) context.lookup("java:global/fourpizza-business/GerenciadorClientes");
	}
	
	@Test
	public void registrandoClienteValido() throws Exception {
		Cliente cliente = new Cliente("Gabriel Ozeas", "", "", "gabriel.ozeas@4linux.com.br");
		clientes.registrar(cliente);
		
		Cliente persistido = clientes.buscarClientePorEmail("gabriel.ozeas@4linux.com.br");
		assertEquals(cliente.getNome(), persistido.getNome());
	}
	
	@Test
	public void listandoTodosClientes() throws Exception {
		Cliente c1 = new Cliente("Gabriel Ozeas", "", "", "gabriel.ozeas@4linux.com.br");
		clientes.registrar(c1);
		
		Cliente c2 = new Cliente("Gustavo Lira", "", "", "gustavo@4linux.com.br");
		clientes.registrar(c2);
		
		Cliente c3 = new Cliente("Gustavo Maia", "", "", "gustavo.maia@4linux.com.br");
		clientes.registrar(c3);
		
		Cliente c4 = new Cliente("Leonardo Siqueira", "", "", "leonardo.siqueira@4linux.com.br");
		clientes.registrar(c4);
		
		List<Cliente> lista = clientes.listarClientes();
		assertEquals(4, lista.size());
	}
	
	@Test
	public void listandoTodosClientesComPaginacao() throws Exception {
		Cliente c1 = new Cliente("Gabriel Ozeas", "", "", "gabriel.ozeas@4linux.com.br");
		clientes.registrar(c1);
		
		Cliente c2 = new Cliente("Gustavo Lira", "", "", "gustavo@4linux.com.br");
		clientes.registrar(c2);
		
		Cliente c3 = new Cliente("Gustavo Maia", "", "", "gustavo.maia@4linux.com.br");
		clientes.registrar(c3);
		
		Cliente c4 = new Cliente("Leonardo Siqueira", "", "", "leonardo.siqueira@4linux.com.br");
		clientes.registrar(c4);
		
		List<Cliente> lista = clientes.listarClientes(2, 10);
		assertEquals(2, lista.size());
	}
	
	@Test
	public void desativarClienteAtivo() throws Exception {
		Cliente c1 = new Cliente("Gabriel Ozeas", "", "", "gabriel.ozeas@4linux.com.br");
		clientes.registrar(c1);
		
		clientes.desativar(c1.getId());
		Cliente cliente = clientes.buscarClientePorEmail("gabriel.ozeas@4linux.com.br");
		assertFalse(cliente.isAtivo());
	}
	
	@After
	public void finalizar() throws Exception {
		ejbContainer.close();
	}
}
