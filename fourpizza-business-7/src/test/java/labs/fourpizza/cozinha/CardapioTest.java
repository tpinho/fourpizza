package labs.fourpizza.cozinha;

import org.junit.Test;

public class CardapioTest {
	@Test(expected = IllegalArgumentException.class)
	public void listandoComPaginacaoInicioInvalido() throws Exception {
		Cardapio recheios = new Cardapio();
		recheios.listarRecheios(-1, 2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void listandoComPaginacaoQuantidadeInvalida() throws Exception {
		Cardapio recheios = new Cardapio();
		recheios.listarRecheios(1, 0);
	}
}
