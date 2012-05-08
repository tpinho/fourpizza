package labs.fourpizza.cozinha;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import labs.fourpizza.cozinha.dao.IngredientesDao;
import labs.fourpizza.cozinha.dominio.Ingrediente;
import labs.fourpizza.dao.DaoException;
import labs.fourpizza.dao.JdbcDaoFactory;

/**
 * Servlet responsável por controlar e exibir todos os ingredientes
 * registrados no sistema.
 */
public class IngredientesServlet extends HttpServlet {
	private static final long serialVersionUID = 3718616899656900039L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Configura conteúdo da resposta ser HTML.
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		montarCabecalho(out);

		String acao = request.getParameter("acao");
		if ("adicionar".equals(acao)) {
			adicionar(request.getParameter("nome"),
					request.getParameter("valor"), out);
		} else if ("buscar".equals(acao)) {
			buscar(request.getParameter("nome"), out);
		} else if ("remover".equals(acao)) {
			remover(request.getParameter("nome"), out);
		} else {
			listar(out);
		}

		montarRodape(out);
	}

	private void montarCabecalho(PrintWriter out) {
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Ingredientes</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Ingredientes</h1>");
	}

	private void montarRodape(PrintWriter out) {
		out.println("</body>");
		out.println("</html>");
	}

	/**
	 * Adiciona um ingrediente novo na base de dados.
	 * 
	 * @param nome Nome do novo ingrediente
	 * @param valor Valor do ingrediente
	 */
	private void adicionar(String nome, String valor, PrintWriter out) {
		try {
			// Instância DAO de ingredientes
			JdbcDaoFactory daoFactory = new JdbcDaoFactory();
			IngredientesDao ingredientesDao = daoFactory.getIngredientesDao();

			float valorIngrediente = 0.0f;
			if (valor != null) {
				valorIngrediente = Float.parseFloat(valor);
			}

			ingredientesDao.adicionar(new Ingrediente(nome, valorIngrediente));
		} catch (NumberFormatException nfe) {
			out.println("<p>Valor do ingrediente '" + nome
					+ "' não é um número de ponto flutuante.</p>");
		} catch (IllegalArgumentException iae) {
			out.println("<p>Parâmetros do ingrediente são inválidos para persisti-lo</p>");
		} catch (DaoException daoe) {
			out.println("Ocoreu um problema na adição do novo ingrediente: "
					+ daoe.getMessage());
		}

		listar(out);
	}

	/**
	 * Lista na tela em forma de tabela todos os ingredientes
	 * persistidos.
	 * 
	 * @param out {@code PrintWriter} para imprimir na tela
	 */
	private void listar(PrintWriter out) {
		List<Ingrediente> ingredientes = null;
		try {
			// Instância DAO de ingredientes
			JdbcDaoFactory daoFactory = new JdbcDaoFactory();
			IngredientesDao ingredientesDao = daoFactory.getIngredientesDao();

			ingredientes = ingredientesDao.listar();
		} catch (DaoException daoe) {
			out.println("Ocorreu um problema ao listar os ingredientes registrados: "
					+ daoe.getMessage());
			return;
		}

		if (ingredientes.size() > 0) {
			out.println("<table id='tabela-ingredientes'>");

			out.println("<tr>");
			out.println("<th>Ingredientes</th>");
			out.println("</tr>");

			for (Ingrediente ingrediente : ingredientes) {
				out.println("<tr>");
				out.println("<td>" + ingrediente.getNome() + "</td>");
				out.println("</tr>");
			}

			out.println("</table>");
		} else {
			out.println("Lista de ingredientes vazia!");
		}
	}

	/**
	 * Busca na base de dados e imprime na tela um ingrediente com o
	 * mesmo nome passado como parâmetro.
	 * 
	 * @param nome Nome do ingrediente.
	 * @param out {@code PrintWriter} para escrever na tela.
	 */
	private void buscar(String nome, PrintWriter out) {
		Ingrediente ingrediente = null;
		try {
			// Instância DAO de ingredientes
			JdbcDaoFactory daoFactory = new JdbcDaoFactory();
			IngredientesDao ingredientesDao = daoFactory.getIngredientesDao();

			ingrediente = ingredientesDao.buscarPorNome(nome);
		} catch (DaoException daoe) {
			out.println("Ocoreu um problema com a base de dados ao recuperar o ingrediente");
			return;
		}

		if (ingrediente != null) {
			out.println("<h4>Ingrediente " + ingrediente.getNome()
					+ ", Valor por porção: R$ "
					+ ingrediente.getValorPorPorcao() + "</h4>");
		} else {
			out.println("<h4>Ingrediente não encontrado</h4>");
		}
		listar(out);
	}

	/**
	 * Remove ingredientes através do nome passado como argumento.
	 * 
	 * @param nome Nome do ingrediente que será removido.
	 * @param out {@code PrintWriter} para escrever na tela.
	 */
	private void remover(String nome, PrintWriter out) {
		Ingrediente ingrediente = null;
		try {
			// Instância DAO de ingredientes
			JdbcDaoFactory daoFactory = new JdbcDaoFactory();
			IngredientesDao ingredientesDao = daoFactory.getIngredientesDao();

			ingrediente = ingredientesDao.remover(nome);
		} catch (DaoException daoe) {
			out.println("Ocoreu um problema com a base de dados ao remover ingrediente");
			return;
		}

		if (ingrediente != null) {
			out.println("<h4>Ingrediente " + ingrediente.getNome()
					+ " removido com sucesso!</h4>");
		} else {
			out.println("<h4>Ingrediente não encontrado</h4>");
		}

		listar(out);
	}
}
