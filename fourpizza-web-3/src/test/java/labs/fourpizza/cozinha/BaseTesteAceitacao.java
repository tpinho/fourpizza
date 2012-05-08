package labs.fourpizza.cozinha;

import java.io.File;

import labs.fourpizza.dao.PersistenciaUtil;
import net.sourceforge.jwebunit.junit.WebTester;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.JettyWebXmlConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.TagLibConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * Classe base para outras classes que fazem teste de aceitação na
 * aplicaçãoa da pizzaria.
 */
public class BaseTesteAceitacao {
	/**
	 * Caminho para o diretorio do ShrinkWrap
	 */
	private static final String SHRINKWRAP_DIR = "target/shrinkwrap";

	/**
	 * Caminho para o arquivo WAR gerado.
	 */
	private static final String WAR_PATH = SHRINKWRAP_DIR + "/pizzaria.war";

	/**
	 * Porta em que o container servlet irá rodar
	 */
	public static final int SERVER_PORT = 9091;

	/**
	 * Context em qual a aplicação irá subir
	 */
	public static final String CONTEXT = "/";

	/**
	 * Local onde estõão os arquivos estáticos da aplicação
	 */
	public static final String WEBAPP_PATH = "src/main/webapp";

	/**
	 * Arquivo com os comandos SQL para iniciar o banco de dados
	 */
	public static final String SQL_INSTALACAO_BANCO = "src/main/webapp/META-INF/database.sql";

	/**
	 * Componente do JWebUnit para testar a interface da aplicação
	 */
	protected static WebTester webTester;

	/**
	 * Container servlet que será utilizado no teste.
	 */
	protected static Server server;

	/**
	 * Na inicialização carrega container de servlet e o banco de
	 * dados para realizar os testes.
	 */
	@BeforeClass
	public static void inicializar() throws Exception {
		server = new Server(SERVER_PORT);
		server.setStopAtShutdown(false);

		encerrandoBanco();
		gerandoArquivoWar();

		WebAppContext contexto = gerandoContextoAplicacao();

		server.setHandler(contexto);
		server.start();

		webTester = new WebTester();
		webTester.setBaseUrl("http://localhost:9091");
		webTester.beginAt("/instalar-banco?acao=instalar-estrutura");
	}

	/**
	 * Gera uma instância do contexto da aplicação.
	 * 
	 * @return Contexto para ser instalado no servidor.
	 */
	private static WebAppContext gerandoContextoAplicacao() throws Exception {
		WebAppContext context = new WebAppContext();
		context.setWar(new File(WAR_PATH).getAbsolutePath());
		context.setContextPath(CONTEXT);

		context.setConfigurations(new Configuration[] {
				new AnnotationConfiguration(), new JettyWebXmlConfiguration(),
				new WebInfConfiguration(), new WebXmlConfiguration(),
				new TagLibConfiguration(), new MetaInfConfiguration(),
				new PlusConfiguration(), new FragmentConfiguration(),
				new EnvConfiguration() });
		return context;
	}

	/**
	 * Gera arquivo zipado com a aplicação e salva no diretório
	 * target.
	 */
	private static void gerandoArquivoWar() throws Exception {
		// Exclui o arquivo .war da aplicação, se existir
		excluirArquivosShrinkWrap();

		// Cria diretorio shrinkwrap dentro de target/
		final File webAppDir = new File(SHRINKWRAP_DIR);
		webAppDir.mkdirs();

		WebArchive war = ShrinkWrap.create(WebArchive.class, WAR_PATH);

		//war.setWebXML(new File(WEBAPP_PATH, "WEB-INF/web.xml"));

		// Adiciona todas as classes do pacote labs.fourpizza
		war.addPackages(true, "labs.fourpizza");
		
		// Importa todos os arquivos dentro de webaap, como js, img, css, jsps.
		ExplodedImporter exploded = ShrinkWrap.create(JavaArchive.class).as(ExplodedImporter.class);
		exploded.importDirectory("src/main/webapp");
		
		// Junta com as classes já adicionadas.
		war.merge(exploded.as(JavaArchive.class), Filters.includeAll());
	
		/*
		 * Exportando aplicação com um arquivo .war
		 */
		final File webappFile = new File(WAR_PATH);
		war.as(ZipExporter.class).exportTo(webappFile);
	}

	/**
	 * Exclui os arquivos gerados pelo Shrink Wrap
	 */
	private static void excluirArquivosShrinkWrap() throws Exception {

		final File webappFile = new File(WAR_PATH);
		if (webappFile.exists()) {
			webappFile.delete();
		}

		final File shrinkWrapDir = new File(SHRINKWRAP_DIR);
		if (shrinkWrapDir.exists()) {
			shrinkWrapDir.delete();
		}
	}

	@AfterClass
	public static void encerrar() throws Exception {
		// Parar o container de servlet
		server.stop();

		encerrandoBanco();

		// Excluir arquivos de deploy
		excluirArquivosShrinkWrap();
	}

	private static void encerrandoBanco() throws Exception {
		PersistenciaUtil.encerrarConexao();
	}

}
