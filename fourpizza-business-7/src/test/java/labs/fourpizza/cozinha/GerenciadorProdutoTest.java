package labs.fourpizza.cozinha;

import org.junit.Test;

public class GerenciadorProdutoTest {
	@Test(expected = IllegalArgumentException.class)
	public void listandoComPaginacaoInicioInvalido() throws Exception {
		GerenciadorProduto produtos = new GerenciadorProduto();
		produtos.listarProdutos(-1, 2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void listandoComPaginacaoQuantidadeInvalida() throws Exception {
		GerenciadorProduto produtos = new GerenciadorProduto();
		produtos.listarProdutos(1, 0);
	}
}
