package labs.fourpizza.cozinha;

import org.junit.Test;

public class RecheiosServletIT extends BaseTesteAceitacao {

	@Test
	public void verificandoDeployCorretoDoServlet() throws Exception {
		webTester.beginAt("/recheios");
		webTester.assertTitleEquals("Recheios Tradicionais");
		webTester.assertTextPresent("Recheios Tradicionais");
	}

	@Test
	public void verificandoListaVazia() throws Exception {
		webTester.beginAt("/recheios");
		webTester.assertTextPresent("Lista de recheios vazia!");
		webTester.assertTableNotPresent("tabela-recheios");
	}

	@Test
	public void exibindoFormularioNovoRecheioSemIngredientes() throws Exception {
		webTester.beginAt("/recheios?acao=novorecheio");
		// Não perrmite registrar recheio sem ingrediente cadastrado
		webTester.assertTextPresent("Não foi possível listar os ingredientes "
				+ "para criar o recheio: Nenhum ingrediente registrado");
	}

	@Test
	public void exibindoFormularioNovoRecheio2Ingredientes() throws Exception {
		webTester
				.beginAt("/ingredientes?acao=adicionar&nome=Calabreza&valor=1");
		webTester
				.beginAt("/ingredientes?acao=adicionar&nome=Mussarela&valor=1.34");

		webTester.beginAt("/recheios?acao=novorecheio");
		webTester.assertCheckboxPresent("ingredientes", "0");
		webTester.assertCheckboxPresent("ingredientes", "1");
	}

	@Test
	public void adicionandoRecheioValido() throws Exception {
		// O sistema já contem 2 ingredientes do teste anterior.
		// Criar novo recheio com esses dois ingredientes.
		webTester.beginAt("/recheios?acao=novorecheio");

		webTester.setTextField("nome", "Portuguesa");
		webTester.checkCheckbox("ingredientes", "0");
		webTester.submit("submit");

		webTester
				.assertTextPresent("Recheio Portuguesa adicionado com sucesso!");
		webTester.assertTablePresent("tabela-recheios");
		webTester
				.assertTableMatch("tabela-recheios", new String[][] {
						{ "Recheio Tradicional", "Ingredientes", "Valor", "" },
						{ "Portuguesa", "Calabreza", "R\\$ 1,00",
								"Remover Recheio" } });

		webTester.beginAt("/recheios?acao=novorecheio");

		webTester.setTextField("nome", "Mussarela");
		webTester.checkCheckbox("ingredientes", "0");
		webTester.checkCheckbox("ingredientes", "1");
		webTester.submit("submit");

		webTester
				.assertTextPresent("Recheio Mussarela adicionado com sucesso!");
		webTester.assertTablePresent("tabela-recheios");
		webTester.assertTableMatch("tabela-recheios", new String[][] {
				{ "Recheio Tradicional", "Ingredientes", "Valor", "" },
				{ "Portuguesa", "Calabreza", "R\\$ 1,00", "Remover Recheio" },
				{ "Mussarela", "Calabreza e Mussarela.", "R\\$ 2,34",
						"Remover Recheio" } });
	}

	@Test
	public void removendoRecheio() throws Exception {
		webTester.beginAt("/recheios");

		webTester.assertTableMatch("tabela-recheios", new String[][] {
				{ "Recheio Tradicional", "Ingredientes", "Valor", "" },
				{ "Portuguesa", "Calabreza", "R\\$ 1,00", "Remover Recheio" },
				{ "Mussarela", "Calabreza e Mussarela.", "R\\$ 2,34",
						"Remover Recheio" } });

		webTester.clickLinkWithText("Remover Recheio", 0);

		webTester.assertTableMatch("tabela-recheios", new String[][] {
				{ "Recheio Tradicional", "Ingredientes", "Valor", "" },
				{ "Mussarela", "Calabreza e Mussarela.", "R\\$ 2,34",
						"Remover Recheio" } });

		webTester.clickLinkWithText("Remover Recheio", 0);
		webTester.assertTextPresent("Lista de recheios vazia!");
	}
}
