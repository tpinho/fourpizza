package labs.fourpizza.cozinha;

import static org.junit.Assert.assertEquals;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import labs.fourpizza.cozinha.dominio.Ingrediente;
import labs.fourpizza.cozinha.dominio.Recheio;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CardapioIT {
	private EJBContainer ejbContainer;
	private Context context;
	private Recheio recheio;
	private Cardapio recheios;

	@Before
	public void inicializar() throws Exception {
		ejbContainer = EJBContainer.createEJBContainer();
		context = ejbContainer.getContext();
		recheios = (Cardapio) context.lookup("java:global/fourpizza-business/Cardapio");
		
		recheio = new Recheio("Catarina");
		recheio.adicionarIngrediente(new Ingrediente("Aliche", 2.40f));
		recheio.adicionarIngrediente(new Ingrediente("Alho", 2.80f));
		recheio.adicionarIngrediente(new Ingrediente("Tomate", 2.40f));
		recheios.adicionarRecheio(recheio);
	}
	
	@Test
	public void adicionandoRecheioPersonalizado() throws Exception {
		Recheio persistido = recheios.getRecheio(recheio.getId());
		assertEquals(new Float(7.60), new Float(persistido.getValor()));
		assertEquals("[Catarina: Aliche, Alho e Tomate.]", recheio.toString());
		
	}
	
	@Test
	public void atualizandoRecheioPersonalizado() throws Exception {
		Ingrediente aliche = recheio.getIngredientes().get(0);		
		
		Recheio catarina = recheios.getRecheio(recheio.getId());
		catarina.removerIngrediente(aliche);
		recheios.atualizarRecheio(catarina);
		
		Recheio atualizado = recheios.getRecheio(recheio.getId());
		assertEquals(new Float(5.20), new Float(atualizado.getValor()));
		assertEquals("[Catarina: Alho e Tomate.]", atualizado.toString());
	}
	
	public void removendoRecheioComSucesso() throws Exception {
		recheios.removerRecheio(recheio);
		assertTrue(recheios.getRecheio(recheio.getId()) == null);
	}
	
	@After
	public void finalizar() throws Exception {
		ejbContainer.close();
	}
}
