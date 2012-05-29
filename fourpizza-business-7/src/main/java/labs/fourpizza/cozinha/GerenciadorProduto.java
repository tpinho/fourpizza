package labs.fourpizza.cozinha;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import labs.fourpizza.cozinha.dominio.Produto;

/**
 * Gerenciar todos os produtos que são servidos como acompanhamento das pizzas,
 * como refrigerantes e sorvetes da pizzaria.
 */
@Stateless
@LocalBean
public class GerenciadorProduto {

	@PersistenceContext(unitName = "fourpizza-persistenceunit")
	private EntityManager entityManager;

	/**
	 * Adiciona um novo produto a lista de produtos que são vendidos;.
	 * 
	 * @param produto
	 *            Instância de {@code Produto} que será persistida.
	 */
	public void adicionar(Produto produto) {
		entityManager.persist(produto);
	}

	/**
	 * Remove um produto da lista de produtos disponiveis. Se o produto não
	 * existir na lista, nada acontecerá.
	 * 
	 * @param produto
	 *            Instância de {@code Produto} que será removida.
	 */
	public void remover(Produto produto) {
		if (produto != null) {
			entityManager.remove(get(produto.getId()));
		}
	}

	/**
	 * Retorna uma instância de {@code Produto} com o id passsado como
	 * argumento.
	 * 
	 * @param produtoId
	 *            Id do produto que será retornado.
	 * @return Instância com o parametro id, ou caso não encontre, retorna
	 *         {@code null}
	 */
	public Produto get(Long produtoId) {
		return entityManager.find(Produto.class, produtoId);
	}

	/**
	 * Atualiza uma instância de {@code Produto} passado como argumento.
	 * 
	 * @param produto
	 *            Produto que será atualizado.
	 */
	public void atualizar(Produto produto) {
		entityManager.merge(produto);
	}

	@SuppressWarnings("unchecked")
	public List<Produto> listarProdutos() {
		StringBuilder jpqlBuilder = new StringBuilder();
		jpqlBuilder.append(" SELECT produto from Produto produto ");
		Query query = entityManager.createQuery(jpqlBuilder.toString());
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Produto> listarProdutos(int inicio, int quantidade) {
		if (entityManager == null) {
			throw new IllegalArgumentException();
		}
		StringBuilder jpqlBuilder = new StringBuilder();
		jpqlBuilder.append(" SELECT produto from Produto produto ");
		Query query = entityManager.createQuery(jpqlBuilder.toString());
		query.setFirstResult(inicio);
		query.setMaxResults(quantidade);
		return query.getResultList();
	}

}