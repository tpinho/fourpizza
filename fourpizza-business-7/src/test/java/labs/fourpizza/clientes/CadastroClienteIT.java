package labs.fourpizza.clientes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import labs.fourpizza.clientes.dominio.CartaoCreditoInfo;
import labs.fourpizza.clientes.dominio.CartaoCreditoInfo.Bandeira;
import labs.fourpizza.clientes.dominio.Cliente;
import labs.fourpizza.clientes.dominio.Endereco;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CadastroClienteIT {
	private EJBContainer ejbContainer;
	private Context context;
	private CadastroCliente cadastroCliente;
	private GerenciadorClientes clientes;

	@Before
	public void inicializar() throws Exception {
		ejbContainer = EJBContainer.createEJBContainer();
		context = ejbContainer.getContext();
		cadastroCliente = (CadastroCliente) context.lookup("java:global/"
				+ "fourpizza-business/CadastroCliente");
		clientes = (GerenciadorClientes) context.lookup("java:global/"
				+ "fourpizza-business/GerenciadorClientes");
	}

	@Test
	public void registrandoClienteSucesso() throws Exception {

		ClienteInfo info = new ClienteInfo("Gustavo Lira", "11222333444",
				"33344455566");

		cadastroCliente.setClienteInfo(info);
		cadastroCliente.setSecurity("gustavo@4linux.com.br", "123456");

		Endereco endereco = new Endereco("Rua Oswaldo Cruz", "550",
				"Bela Vista");
		cadastroCliente.setEndereco(endereco);

		CartaoCreditoInfo ccInfo = new CartaoCreditoInfo();
		ccInfo.setBandeira(Bandeira.MASTERCARD);
		ccInfo.setDataVencimento(new Date());
		ccInfo.setNome("Gustavo Lira e Silva");
		ccInfo.setNumero("456789123");
		cadastroCliente.setCartaoCreditoInfo(ccInfo);

		cadastroCliente.cadastrarCliente();
		
		Cliente persistido = clientes.buscarClientePorEmail("gustavo@4linux.com.br");
		assertNotNull(persistido);
		assertEquals(Bandeira.MASTERCARD, persistido.getCartaoCreditoInfo().getBandeira());
		assertEquals("550", persistido.getEndereco().getNumero());
	}

	@After
	public void finalizar() throws Exception {
		ejbContainer.close();
	}
}
