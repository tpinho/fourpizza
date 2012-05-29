package labs.fourpizza.clientes;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import labs.fourpizza.clientes.dominio.Cliente;

@Stateless
@LocalBean
public class GerenciadorClientes {

	@PersistenceContext(unitName = "fourpizza-persistenceunit")
	private EntityManager entityManager;

	public void registrar(Cliente cliente) throws ClienteException {
		Cliente clientePersistido = buscarClientePorEmail(cliente.getEmail());
		if (clientePersistido != null) {
			throw new ClienteException();
		}
		entityManager.persist(cliente);
	}

	public Cliente buscarCliente(Long clienteId) {
		return entityManager.find(Cliente.class, clienteId);
	}

	public Cliente buscarClientePorEmail(String string) {
		Query query = entityManager
				.createNamedQuery("Cliente.buscarClientePorEmail");
		query.setParameter("email", string);
		try {
			return (Cliente) query.getSingleResult();
		} catch (NoResultException exception) {
			return null;
		}
	}

	public void desativar(Long clienteId) {
		Cliente cliente = buscarCliente(clienteId);
		cliente.setAtivo(false);
		atualizar(cliente);
	}

	public void atualizar(Cliente cliente) {
		entityManager.merge(cliente);
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> listarClientes() {
		Query query = entityManager.createNamedQuery("Cliente.listarClientes");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> listarClientes(int inicio, int quantidade) {
		Query query = entityManager.createNamedQuery("Cliente.listarClientes");
		query.setFirstResult(inicio);
		query.setMaxResults(quantidade);
		return query.getResultList();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager manager) {
		this.entityManager = manager;
	}

}