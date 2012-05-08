package labs.fourpizza.clientes;

import java.util.List;

import javax.persistence.EntityManager;

import labs.fourpizza.clientes.dominio.Cliente;
public class GerenciadorClientes {
	public void registrar(Cliente cliente) throws ClienteException {
	}

	
	public Cliente buscarCliente(Long clienteId) {
		return null;
	}
	
	public Cliente buscarClientePorEmail(String string) {
		return null;
	}
	
	public void desativar(Long clienteId) {
		
	}
	
	public void atualizar(Cliente cliente) {
		
	}
	
	public List<Cliente> listarClientes() {
		return null;
	}
	
	public List<Cliente> listarClientes(int inicio, int quantidade) {
		return null;
	}

	public EntityManager getEntityManager() {
		return null;
	}
	
	public void setEntityManager(EntityManager manager) {
	}
	
}
