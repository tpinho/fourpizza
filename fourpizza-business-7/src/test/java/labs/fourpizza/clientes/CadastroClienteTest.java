package labs.fourpizza.clientes;

import org.junit.Test;

public class CadastroClienteTest {
	@Test(expected=IllegalStateException.class)
	public void criandoClienteSemInfo() throws Exception {
		CadastroCliente cadastro = new CadastroCliente();
		cadastro.setClienteInfo(null);
		cadastro.cadastrarCliente();
	}
}
