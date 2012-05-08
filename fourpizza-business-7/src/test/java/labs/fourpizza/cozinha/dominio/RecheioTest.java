package labs.fourpizza.cozinha.dominio;

import static org.junit.Assert.*;
import labs.fourpizza.cozinha.MontagemException;

import org.junit.Test;

public class RecheioTest {

	@Test
	public void toStringDoRecheioCaipira() throws Exception {
		Recheio recheio = new Recheio("Caipira");
		recheio.adicionarIngrediente(new Ingrediente("Creme de Milho", 3.50f));
		recheio.adicionarIngrediente(new Ingrediente("Cebola", 1.10f));
		assertEquals("[Caipira: Creme de Milho e Cebola.]", recheio.toString());
	}

	@Test
	public void descricaoDoRecheioMilho() throws Exception {
		Recheio recheio = new Recheio();
		recheio.adicionarIngrediente(new Ingrediente("Creme de Milho", 3.50f));
		recheio.adicionarIngrediente(new Ingrediente("Cebola", 1.10f));
		assertEquals("Creme de Milho e Cebola.", recheio.getDescricao());
	}

	@Test
	public void recalculandoPrecoComNovoIngrediente() throws Exception {
		Recheio recheio = new Recheio();
		Ingrediente mussarela = new Ingrediente("Mussarela", 1.10f);
		recheio.adicionarIngrediente(mussarela);
		assertEquals(new Float(1.10f), new Float(recheio.getValor()));

		recheio.adicionarIngrediente(new Ingrediente("Cebola", 1.10f));
		assertEquals(new Float(2.20f), new Float(recheio.getValor()));

		recheio.removerIngrediente(mussarela);
		assertEquals(new Float(1.10f), new Float(recheio.getValor()));
	}

	@Test
	public void recheioCom2Ingredientes() throws Exception {
		Recheio recheio = new Recheio();
		recheio.adicionarIngrediente(new Ingrediente("Mussarela", 1.10f));
		recheio.adicionarIngrediente(new Ingrediente("Cebola", 1.10f));
		recheio.validar();
	}

	@Test
	public void recheioSemIngredientes() throws Exception {
		Recheio recheio = new Recheio();
		try {
			recheio.validar();
			fail("Não deveria chegar aqui!");
		} catch (MontagemException me) {
			assertEquals("Deve haver pelo menos um " + "ingrediente.",
					me.getMessage());
		}
	}

	@Test
	public void recheioMaisDe10Ingredientes() throws Exception {
		Recheio recheio = new Recheio();
		recheio.adicionarIngrediente(new Ingrediente("Mussarela", 1.10f));
		recheio.adicionarIngrediente(new Ingrediente("Mussarela", 1.10f));
		recheio.adicionarIngrediente(new Ingrediente("Mussarela", 1.10f));
		recheio.adicionarIngrediente(new Ingrediente("Cebola", 3.20f));
		recheio.adicionarIngrediente(new Ingrediente("Bacon", 3.10f));
		recheio.adicionarIngrediente(new Ingrediente("Tomate", 0.80f));
		recheio.adicionarIngrediente(new Ingrediente("Tomate", 0.80f));
		recheio.adicionarIngrediente(new Ingrediente("Alho", 1.30f));
		try {
			recheio.validar();
			fail("Não deveria chegar aqui!");
		} catch (MontagemException me) {
			assertEquals("Númeo total de ingredientes inválido, máximo: 7",
					me.getMessage());
		}
	}
}
