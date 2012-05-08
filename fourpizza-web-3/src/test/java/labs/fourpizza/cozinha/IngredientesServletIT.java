package labs.fourpizza.cozinha;

import org.junit.Test;

public class IngredientesServletIT extends BaseTesteAceitacao {

    @Test
    public void verificandoDeployCorretoDoServlet() throws Exception {
        webTester.beginAt("/ingredientes");
        webTester.assertTitleEquals("Ingredientes");
        webTester.assertTextPresent("Ingredientes");
    }

    @Test
    public void verificandoMensagemNenhumIngrediente() throws Exception {
        webTester.beginAt("/ingredientes");
        webTester.assertTextPresent("Lista de ingredientes vazia!");
        webTester.assertTableNotPresent("tabela-ingredientes");
    }

    @Test
    public void ingredienteInvalidoGeraErro() throws Exception {
        webTester.beginAt("/ingredientes?acao=adicionar&nome=");
        webTester.assertTextPresent("Ocoreu um problema na adição do novo "
                + "ingrediente: Ingrediente não válido!");
        webTester.assertTextPresent("Lista de ingredientes vazia!");
    }

    @Test
    public void adicionandoPrimeiroIngrediente() throws Exception {
        webTester.beginAt("/ingredientes?acao=adicionar"
                + "&nome=Mussarela&valor=1.34");
        webTester.assertTablePresent("tabela-ingredientes");
        webTester.assertTableRowCountEquals("tabela-ingredientes", 2);

        webTester.assertTableEquals("tabela-ingredientes", new String[][] {
                { "Ingredientes" }, { "Mussarela" } });
    }

    @Test
    public void adicionandoIngredienteValorInvalido() throws Exception {
        webTester.beginAt("/ingredientes?acao=adicionar"
                + "&nome=Calabreza&valor=ab");
        webTester.assertTextPresent("Valor do ingrediente 'Calabreza' "
                + "não é um número de ponto flutuante.");
    }

    @Test
    public void removendoIngredienteDaTabela() throws Exception {
        webTester.beginAt("/ingredientes?acao=adicionar"
                + "&nome=Calabreza&valor=1.50");
        webTester.beginAt("/ingredientes?acao=remover&nome=Calabreza");
        webTester.assertTextPresent("Ingrediente Calabreza "
                + "removido com sucesso!");
    }

    @Test
    public void removendoIngredienteNaoExistente() throws Exception {
        webTester.beginAt("/ingredientes?acao=remover&nome=Calabreza");
        webTester.assertTextPresent("Ingrediente não encontrado");
    }

    @Test
    public void buscandoIngrediente() throws Exception {
        webTester.beginAt("/ingredientes?acao=buscar&nome=Mussarela");
        webTester.assertTextPresent("Ingrediente Mussarela, "
                + "Valor por porção: R$ 1.34");
    }
}
