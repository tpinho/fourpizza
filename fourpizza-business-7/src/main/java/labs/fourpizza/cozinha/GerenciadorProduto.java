package labs.fourpizza.cozinha;

import java.util.List;

import labs.fourpizza.cozinha.dominio.Produto;

/**
 * Gerenciar todos os produtos que são servidos como acompanhamento das pizzas,
 * como refrigerantes e sorvetes da pizzaria.
 */
public class GerenciadorProduto {
	/**
	 * Adiciona um novo produto a lista de produtos que são vendidos;.
	 * 
	 * @param produto Instância de {@code Produto} que será persistida.
	 */
	public void adicionar(Produto produto) {
	}

	/**
	 * Remove um produto da lista de produtos disponiveis. Se o produto não
	 * existir na lista, nada acontecerá.
	 * 
	 * @param produto Instância de {@code Produto} que será removida.
	 */
	public void remover(Produto produto) {
	}

	/**
	 * Retorna uma instância de {@code Produto} com o id passsado como
	 * argumento.
	 * 
	 * @param produtoId Id do produto que será retornado.
	 * @return Instância com o parametro id, ou caso não encontre, retorna
	 *         {@code null}
	 */
	public Produto get(Long produtoId) {
		return null;
	}

	/**
	 * Atualiza uma instância de {@code Produto} passado como argumento.
	 * 
	 * @param produto Produto que será atualizado.
	 */
	public void atualizar(Produto produto) {
		
	}
	
	public List<Produto> listarProdutos() {
		return null;
	}
	
	public List<Produto> listarProdutos(int inicio, int quantidade) {
		return null;
	}
	
}
