package labs.fourpizza.cozinha;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import labs.fourpizza.cozinha.dominio.Recheio;

@Stateless
@LocalBean
public class Cardapio {

	@PersistenceContext(unitName = "fourpizza-persistenceunit")
	private EntityManager entityManager;

	public void adicionarRecheio(Recheio recheio) throws MontagemException {
		entityManager.persist(recheio);
	}

	public void removerRecheio(Recheio recheio) {
		if (recheio != null) {
			entityManager.remove(getRecheio(recheio.getId()));
		}
	}

	public void atualizarRecheio(Recheio recheio) {
		entityManager.merge(recheio);
	}

	public Recheio getRecheio(Long recheioId) {
		return entityManager.find(Recheio.class, recheioId);
	}

	@SuppressWarnings("unchecked")
	public List<Recheio> listarRecheios() {
		StringBuilder jpqlBuilder = new StringBuilder();
		jpqlBuilder.append(" SELECT recheio from Recheio recheio ");
		Query query = entityManager.createQuery(jpqlBuilder.toString());
		return query.getResultList();
	}

	public List<Recheio> listarRecheios(int inicio, int quantidade) {
		throw new IllegalArgumentException();
	}
}