package labs.fourpizza.cozinha;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

import labs.fourpizza.cozinha.dominio.Produto;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GerenciadorProdutoIT {
	private EJBContainer ejbContainer;
	private Context context;
	private GerenciadorProduto produtos;

	@Before
	public void inicializar() throws Exception {
		ejbContainer = EJBContainer.createEJBContainer();
		context = ejbContainer.getContext();
		produtos = (GerenciadorProduto) context
				.lookup("java:global/fourpizza-business/GerenciadorProduto");
	}

	@Test
	public void listaTodosOsProdutosPersistidos() throws Exception {
		produtos.adicionar(new Produto("Refri 2l Coca",
				"Refrigerante Coca-cola 2Litros", 5.50f));
		produtos.adicionar(new Produto("Sorvete 1L Coco",
				"Sorvete de Coco 1 Litro", 10.50f));
		produtos.adicionar(new Produto("Choc Morango",
				"Cholate Morango 500g", 4.20f));
		
		List<Produto> listaProdutos = produtos.listarProdutos();
		assertEquals(3, listaProdutos.size());
	}
	
	@Test
	public void listaTodosOsProdutosPersistidosPaginacao() throws Exception {
		produtos.adicionar(new Produto("Refri 2l Coca",
				"Refrigerante Coca-cola 2Litros", 5.50f));
		produtos.adicionar(new Produto("Sorvete 1L Coco",
				"Sorvete de Coco 1 Litro", 10.50f));
		produtos.adicionar(new Produto("Choc Morango",
				"Cholate Morango 500g", 4.20f));
		
		List<Produto> listaProdutos = produtos.listarProdutos(1,2);
		assertEquals(2, listaProdutos.size());
	}
	
	
	@Test
	public void recuperandoInterfacesDoControleProdutos() throws Exception {
		GerenciadorProduto produtos = (GerenciadorProduto) context
				.lookup("java:global/fourpizza-business/GerenciadorProduto");
		assertNotNull(produtos);
	}

	@Test
	public void adicionandoProduto() throws Exception {
		Produto sorvete = new Produto("Sorv Nestle 1L Cho",
				"Sorvete Nestle 1 Litro Chocolate", 12.50f);
		GerenciadorProduto produtos = (GerenciadorProduto) context
				.lookup("java:global/fourpizza-business/GerenciadorProduto");
		produtos.adicionar(sorvete);

		Produto sorvetePersistido = produtos.get(1L);
		assertNotNull(sorvetePersistido);
	}

	@Test
	public void atualizandoProduto() throws Exception {
		Produto sorvete = new Produto("Sorv Nestle 1L Cho",
				"Sorvete Nestle 1 Litro Chocolate", 12.50f);
		GerenciadorProduto produtos = (GerenciadorProduto) context
				.lookup("java:global/fourpizza-business/GerenciadorProduto");
		produtos.adicionar(sorvete);

		sorvete.setAbreviacao("Svt Nestle 500g Cho");
		produtos.atualizar(sorvete);

		sorvete = produtos.get(1L);
		assertEquals("Svt Nestle 500g Cho", sorvete.getAbreviacao());

		produtos.remover(sorvete);
		assertTrue(produtos.get(1L) == null);
	}

	@After
	public void finalizar() throws Exception {
		ejbContainer.close();
	}
}
