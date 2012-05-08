package labs.fourpizza.cozinha;

import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class IngredientesServletTest {
    private File mainWebXML;

    @Before
    public void inicializar() {
        mainWebXML = new File("src/main/webapp/WEB-INF/web.xml");
    }

    @Test
    public void verificandoSeExisteArquivoWebXML() throws Exception {
        assertTrue("Arquivo web.xml não foi criado", mainWebXML.exists());
    }

    @Test
    public void tagServletParaIngredientesExiste() throws Exception {
        Document doc = recupearWebXML();

        XPath xpath = XPathFactory.newInstance().newXPath();
        XPathExpression servletTag = xpath
                .compile("//web-app/servlet[servlet-name='IngredientesServlet']");

        NodeList servletTags = (NodeList) servletTag.evaluate(doc,
                XPathConstants.NODESET);
        assertTrue(
                "Nenhum tag servlet criada com servlet-name IngredientesSerlvet",
                servletTags.getLength() > 0);

        XPathExpression servletMappingTag = xpath
                .compile("//web-app/servlet-mapping[servlet-name='IngredientesServlet']");

        NodeList servletMappingTags = (NodeList) servletMappingTag.evaluate(doc,
                XPathConstants.NODESET);
        assertTrue(
                "Nenhum tag servlet-mapping criada com servlet-name IngredientesSerlvet",
                servletMappingTags.getLength() > 0);
    }

    private Document recupearWebXML() throws Exception {
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        return docBuilder.parse(mainWebXML);
    }
}
