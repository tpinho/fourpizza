package labs.fourpizza.clientes;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import labs.fourpizza.clientes.dominio.CartaoCreditoInfo;
import labs.fourpizza.clientes.dominio.Cliente;
import labs.fourpizza.clientes.dominio.Endereco;

@Stateful
@LocalBean
public class CadastroCliente {

	@PersistenceContext(unitName = "fourpizza-persistenceunit")
	private EntityManager entityManager;

	private Cliente cliente = new Cliente();

	public void setEndereco(Endereco endereco) {
		cliente.setEndereco(endereco);
	}

	public void setCartaoCreditoInfo(CartaoCreditoInfo ccInfo) {
		cliente.setCartaoCreditoInfo(ccInfo);
		ccInfo.setCliente(cliente);
	}

	public void setClienteInfo(ClienteInfo clienteInfo) {
		if (clienteInfo == null) {
			throw new IllegalStateException();
		}
		cliente.setNome(clienteInfo.getNome());
		cliente.setRg(clienteInfo.getRg());
		cliente.setCpf(clienteInfo.getCpf());
		cliente.setDataNascimento(clienteInfo.getDataNascimento());
		cliente.setSexo(clienteInfo.getSexo());
	}

	public void setSecurity(String email, String senha) {
		cliente.setEmail(email);
		cliente.setPassword(senha);
	}

	public void cadastrarCliente() throws ClienteException {
		entityManager.persist(cliente);
	}

}
