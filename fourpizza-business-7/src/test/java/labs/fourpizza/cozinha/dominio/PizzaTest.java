package labs.fourpizza.cozinha.dominio;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import labs.fourpizza.cozinha.MontagemException;
import labs.fourpizza.cozinha.dominio.Pizza.Tamanho;

import org.junit.Test;

public class PizzaTest {
	
	@Test
	public void valorTotalPizzaGrandeCom1Recheio() throws Exception {
		Recheio portuguesa = new Recheio("Portuguesa");
		portuguesa.setValor(12.25f);
		Pizza pizza = new Pizza();
		pizza.adicionar(portuguesa);
		assertEquals(new Float(27.25), new Float(pizza.getValor()));
	}
	
	@Test
	public void valorTotalPizzaMediaCom2Recheios() throws Exception {
		Pizza pizza = new Pizza(Tamanho.MEDIA);
		
		Recheio portuguesa = new Recheio("Portuguesa");
		portuguesa.setValor(12.25f);
		pizza.adicionar(portuguesa);
		
		Recheio mussarela = new Recheio("Mussarela");
		mussarela.setValor(9.50f);
		pizza.adicionar(mussarela);
		
		assertEquals(new Float(23.25), new Float(pizza.getValor()));
	}
	
	@Test
	public void validandoPizzaCom1Recheio() throws Exception {
		Recheio recheio = mock(Recheio.class);
		Pizza pizza = new Pizza();
		pizza.adicionar(recheio);
		pizza.validar();
	}
	
	@Test(expected = MontagemException.class)
	public void validandoPizzaSemRecheio() throws Exception {
		Pizza pizza = new Pizza();
		pizza.validar();
	}
	
	@Test(expected = MontagemException.class)
	public void validandoPizzaCom4Recheios() throws Exception {
		Pizza pizza = new Pizza();
		
		Recheio marguerita = new Recheio("Marguerita");
		pizza.adicionar(marguerita);
		
		Recheio portuguesa = new Recheio("Portuguesa");
		pizza.adicionar(portuguesa);
		
		Recheio francesa = new Recheio("Francesa");
		pizza.adicionar(francesa);
		
		Recheio moda = new Recheio("Moda da Casa");
		pizza.adicionar(moda);
		
		pizza.validar();
	}
}
