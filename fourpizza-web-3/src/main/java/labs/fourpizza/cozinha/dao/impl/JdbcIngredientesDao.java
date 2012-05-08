package labs.fourpizza.cozinha.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import labs.fourpizza.cozinha.dao.IngredientesDao;
import labs.fourpizza.cozinha.dominio.Ingrediente;
import labs.fourpizza.dao.DaoException;

/**
 * Implementação da interface {@code IngredientesDao} que se comunica
 * com o banco de dados.
 */
public class JdbcIngredientesDao implements IngredientesDao {
	/**
	 * SQL para inserir novos ingredientes no banco de dados.
	 */
	private static final String SQL_INSERCAO = "INSERT INTO ingredientes "
			+ "(NOME, VALOR_PORCAO) VALUES (?, ?)";

	/**
	 * SQL para listar todos ingrediente
	 */
	private static final String SQL_LISTAR = "SELECT * FROM "
			+ "ingredientes";
	
	/**
	 * SQL para buscar um ingrediente por nome
	 */
	private static final String SQL_BUSCA_POR_NOME = "SELECT * FROM "
			+ "ingredientes WHERE nome = ?";

	/**
	 * SQL para buscar um ingrediente por ID
	 */
	private static final String SQL_BUSCA_ID = "SELECT * FROM "
			+ "ingredientes WHERE id = ?";
	
	private static final String SQL_REMOCAO = "DELETE FROM ingredientes WHERE id = ?";

	/**
	 * Conexao com banco de dados.
	 */
	private Connection conexao;

	/**
	 * Construtor que recebe como parâmetro, uma conexão com o banco
	 * de dados.
	 * 
	 * @param conexao Conexão com o banco de dados.
	 */
	public JdbcIngredientesDao(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void adicionar(Ingrediente ingrediente) throws DaoException {
		if (!(ingrediente.validar())) {
			throw new DaoException("Ingrediente não válido!");
		}

		Ingrediente ingredienteBanco = buscarPorNome(ingrediente.getNome());
		if (ingredienteBanco != null) {
			throw new DaoException(
					"Ingrediente com esse nome já existe no banco");
		}

		try {
			// Realiza inserção e habilita retorno da chave primária
			// criada pelo banco.
			PreparedStatement ps = conexao.prepareStatement(SQL_INSERCAO,
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, ingrediente.getNome());
			ps.setFloat(2, ingrediente.getValorPorPorcao());
			ps.executeUpdate();

			ResultSet novasChavesPrimarias = ps.getGeneratedKeys();
			if (novasChavesPrimarias.next()) {
				ingrediente.setId(novasChavesPrimarias.getLong(1));
			}

		} catch (SQLException sqle) {
			throw new DaoException("Problema ao adicionar novo ingrediente",
					sqle);
		}
	}

	@Override
	public List<Ingrediente> listar() throws DaoException {
		try {
			PreparedStatement ps = conexao.prepareStatement(SQL_LISTAR);

			ResultSet rs = ps.executeQuery();
			return carregarIngredientes(rs);
			
		} catch (SQLException sqle) {
			throw new DaoException("Problema ao realizar consulta", sqle);
		}
	}

	@Override
	public Ingrediente buscar(Long ingredienteID) throws DaoException {
		try {
			PreparedStatement ps = conexao.prepareStatement(SQL_BUSCA_ID);
			ps.setLong(1, ingredienteID);

			ResultSet rs = ps.executeQuery();
			List<Ingrediente> ingredientes = carregarIngredientes(rs);
			if (ingredientes.size() == 0) {
				return null;
			} else {
				return ingredientes.get(0);
			}
		} catch (SQLException sqle) {
			throw new DaoException("Problema ao realizar consulta", sqle);
		}
	}

	@Override
	public Ingrediente buscarPorNome(String nomeIngrediente)
			throws DaoException {
		try {
			PreparedStatement ps = conexao.prepareStatement(SQL_BUSCA_POR_NOME);
			ps.setString(1, nomeIngrediente);

			ResultSet rs = ps.executeQuery();
			List<Ingrediente> ingredientes = carregarIngredientes(rs);
			if (ingredientes.size() == 0) {
				return null;
			} else {
				return ingredientes.get(0);
			}
		} catch (SQLException se) {
			throw new DaoException("Não foi possível realizar a pesquisa", se);
		}
	}

	/**
	 * Carrega ingredientes de um ResultSet em uma lista de
	 * {@code Ingredientes}
	 * 
	 * @param rs ResultSet da consulta feita.
	 * @return Retorna lista com instâncias de {@code Ingrediente}
	 * @throws SQLException Lançada quando algum problema ocorre com o
	 *         ResultSet.
	 */
	private List<Ingrediente> carregarIngredientes(ResultSet rs)
			throws SQLException {
		List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
		while (rs.next()) {
			Ingrediente ingrediente = new Ingrediente();
			ingrediente.setId(rs.getLong("id"));
			ingrediente.setNome(rs.getString("nome"));
			ingrediente.setValorPorPorcao(rs.getFloat("valor_porcao"));
			ingredientes.add(ingrediente);
		}
		return ingredientes;
	}

	@Override
	public Ingrediente remover(String nomeIngrediente) throws DaoException {
		try {
			Ingrediente ingrediente = buscarPorNome(nomeIngrediente);
			if (ingrediente != null)  {
				PreparedStatement ps = conexao.prepareStatement(SQL_REMOCAO);
				ps.setLong(1, ingrediente.getId());
				
				int alteracoes = ps.executeUpdate();
				if (alteracoes == 1) {
					return ingrediente;
				}
				return null;
			}
			return null;
		} catch (SQLException se) {
			throw new DaoException("Não foi possível realizar a pesquisa", se);
		}
		
	}

	public Connection getConexao() {
		return conexao;
	}

	public void setConexao(Connection conexao) {
		this.conexao = conexao;
	}
}
