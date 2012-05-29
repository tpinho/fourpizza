package labs.fourpizza.cozinha;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import labs.fourpizza.cozinha.dao.IngredientesDao;
import labs.fourpizza.cozinha.dao.RecheiosDao;
import labs.fourpizza.cozinha.dominio.Ingrediente;
import labs.fourpizza.cozinha.dominio.Recheio;
import labs.fourpizza.dao.DaoException;
import labs.fourpizza.dao.JdbcDaoFactory;

/**
 * Permite realizar algumas operações básicas (CRUD) com recheios tradicionais.
 */
@WebServlet(urlPatterns = { "/recheios" })
public class RecheiosServlet extends HttpServlet {

	private static final long serialVersionUID = -6286170368182527451L;

	private static final Locale BRASIL = new Locale("pt", "BR");
	private static final DecimalFormatSymbols REAL = new DecimalFormatSymbols(
			BRASIL);
	private static final DecimalFormat DINHEIRO_REAL = new DecimalFormat(
			"¤ ###,###,##0.00", REAL);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Configura conteúdo da resposta ser HTML.
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		montarCabecalho(out);

		String acao = request.getParameter("acao");
		if ("novorecheio".equals(acao)) {
			novoRecheio(out);
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
		out.println("<title>Recheios Tradicionais</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>Recheios Tradicionais</h1>");
	}

	private void montarRodape(PrintWriter out) {
		out.println("</body>");
		out.println("</html>");
	}

	private void listar(PrintWriter out) {
		List<Recheio> recheios = null;
		try {
			// Instância DAO de recheios
			JdbcDaoFactory daoFactory = new JdbcDaoFactory();
			RecheiosDao recheiosDao = daoFactory.getRecheiosDao();

			recheios = recheiosDao.listar();
		} catch (DaoException daoe) {
			out.println("Ocorreu um problema ao listar os recheios registrados: "
					+ daoe.getMessage());
			return;
		}

		if (recheios.size() > 0) {
			out.println("<table id='tabela-recheios'>");

			out.println("<tr>");
			out.println("<th>Recheio Tradicional</th>");
			out.println("<th>Ingredientes</th>");
			out.println("<th>Valor</th>");
			out.println("<th></th>");
			out.println("</tr>");

			for (Recheio recheio : recheios) {
				out.println("<tr>");
				out.println("<td>" + recheio.getNome() + "</td>");
				out.println("<td>" + recheio.getDescricao() + "</td>");
				out.println("<td>" + DINHEIRO_REAL.format(recheio.getValor())
						+ "</td>");
				out.println("<td>" + "<a href=\"/recheios?acao=remover&nome="
						+ recheio.getNome() + "\">Remover Recheio</a></td>");
				out.println("</tr>");
			}

			out.println("</table>");
		} else {
			out.println("Lista de recheios vazia!");
		}
	}

	private void remover(String nome, PrintWriter out) {
		Recheio recheio = null;
		try {
			// Instância DAO de recheios
			JdbcDaoFactory daoFactory = new JdbcDaoFactory();
			RecheiosDao recheiosDao = daoFactory.getRecheiosDao();

			recheio = recheiosDao.remover(nome);
		} catch (DaoException daoe) {
			out.println("Ocoreu um problema com a base de dados ao remover recheio");
			return;
		}

		if (recheio != null) {
			out.println("<h4>Recheio " + recheio.getNome()
					+ " removido com sucesso!</h4>");
		} else {
			out.println("<h4>Recheio não encontrado</h4>");
		}

		listar(out);
	}

	private void buscar(String nome, PrintWriter out) {
		Recheio recheio = null;
		try {
			// Instância DAO de recheios
			JdbcDaoFactory daoFactory = new JdbcDaoFactory();
			RecheiosDao recheiosDao = daoFactory.getRecheiosDao();

			recheio = recheiosDao.buscarPorNome(nome);
		} catch (DaoException daoe) {
			out.println("Ocoreu um problema com a base de dados ao recuperar o recheio");
			return;
		}

		if (recheio != null) {
			out.println("<h4>Recheio " + recheio.getNome() + ", Valor: R$ "
					+ recheio.getValor() + "</h4>");
		} else {
			out.println("<h4>Recheio não encontrado</h4>");
		}
		listar(out);
	}

	private void novoRecheio(PrintWriter out) {
		try {
			// Instância DAO de recheios
			JdbcDaoFactory daoFactory = new JdbcDaoFactory();
			IngredientesDao ingredientesDao = daoFactory.getIngredientesDao();

			List<Ingrediente> ingredientes;
			ingredientes = ingredientesDao.listar();

			if (ingredientes == null || ingredientes.isEmpty()) {
				out.println("Não foi possível listar os ingredientes "
						+ "para criar o recheio: Nenhum ingrediente registrado");
			} else {
				out.println("<form action=\"/recheios\" method=\"post\">");
				out.println("Recheios: ");
				for (Ingrediente ingrediente : ingredientes) {
					out.println("<input type=\"checkbox\" name=\"ingredientes\" value=\""
							+ ingrediente.getId()
							+ "\"/>"
							+ ingrediente.getNome());
				}
				out.println("<br />Nome: <input type=\"text\" name=\"nome\"/>");
				out.println("<input type=\"submit\" name=\"submit\" value=\"Adicionar\" />");
				out.println("</form>");
			}

		} catch (DaoException daoe) {
			out.println("Ocoreu um problema com a base de dados ao recuperar os ingredientes: " + daoe.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		
		try {

			JdbcDaoFactory daoFactory = new JdbcDaoFactory();
			IngredientesDao ingredientesDao = daoFactory.getIngredientesDao();
			RecheiosDao recheiosDao = daoFactory.getRecheiosDao();
			Recheio recheio = new Recheio();

			String nome = request.getParameter("nome");
			String[] ingredientes = request.getParameterValues("ingredientes");

			List<Ingrediente> ingredienteList = new ArrayList<Ingrediente>();
			for (String idIngrediente : ingredientes) {
				ingredienteList.add(ingredientesDao.buscar(Long
						.valueOf(idIngrediente)));
			}
			recheio.setNome(nome);
			recheio.setIngredientes(ingredienteList);

			recheiosDao.adicionar(recheio);

			
			montarCabecalho(out);
			out.println("Recheio " + recheio.getNome()
					+ " adicionado com sucesso!");
			listar(out);
			montarRodape(out);

		} catch (DaoException daoe) {
			out.println("Ocoreu um problema na adição do novo recheio: "
					+ daoe.getMessage());
		} catch (MontagemException me) {
			out.println("Ocorreu um erro ao carregar o recheio: " + me.getMessage());
		}

	}

}