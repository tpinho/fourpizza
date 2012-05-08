package labs.fourpizza.cozinha.dominio;

import static org.junit.Assert.*;
import labs.fourpizza.cozinha.dominio.Produto;

import org.junit.Test;

public class ProdutoTest {
	@Test
	public void comparando2ProdutosIguais() throws Exception {
		Produto p1 = new Produto("Refri Coca", "Refrigerante Coca-cola", 3.50f);
		Produto p2 = new Produto("Refri Coca", "Refrigerante Coca-cola", 3.50f);
		
		assertEquals(p1, p2);
	}
	
	@Test
	public void abreviacaoComoToString() throws Exception {
		Produto p1 = new Produto("Refri Coca", "Refrigerante Coca-cola", 3.50f);
		assertEquals("Refri Coca", p1.toString());
	}
}
