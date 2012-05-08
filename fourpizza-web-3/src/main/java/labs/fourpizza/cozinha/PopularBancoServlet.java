package labs.fourpizza.cozinha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import labs.fourpizza.dao.DaoException;
import labs.fourpizza.dao.PersistenciaUtil;

@WebServlet(urlPatterns = { "/instalar-banco" })
public class PopularBancoServlet extends HttpServlet {
	private static final long serialVersionUID = 6708144442888262196L;

	private static final String CAMINHO_SQL_BANCO = "/META-INF/database.sql";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();

		ServletContext aplicacao = request.getServletContext();
		String sql = getSqlInstalacao(aplicacao, CAMINHO_SQL_BANCO);

		// Criando estrutura do banco de dados
		try {
			out.println("Executando seguintes comandos");
			out.println(sql);
			PersistenciaUtil.popularBanco(sql);
			out.println("------------------------------------");
			out.println("Executando com sucesso!");
		} catch (DaoException e) {
			out.println("Problema ao popular o banco de dados: "
					+ e.getCause().getMessage());
		}
	}

	/**
	 * Recupera o conjunto de SQLs que devem ser executadas na
	 * instalação do banco de dados.
	 * 
	 * @param contexto Contexto da aplicação web.
	 * @param bancoSqlCaminho Caminho absoluto da localização do
	 *        arquivo.
	 * @return SQL que será executada.
	 */
	private String getSqlInstalacao(ServletContext contexto,
			String bancoSqlCaminho) throws IOException {
		InputStream bancoSqlStream = contexto.getResourceAsStream(CAMINHO_SQL_BANCO);
		InputStreamReader leitorBancoStream = new InputStreamReader(
				bancoSqlStream);

		BufferedReader leitorBanco = new BufferedReader(leitorBancoStream);
		StringBuilder bancoDadosSql = new StringBuilder();
		String linha = null;

		while ((linha = leitorBanco.readLine()) != null) {
			bancoDadosSql.append(linha + "\n");
		}
		return bancoDadosSql.toString();
	}

}
