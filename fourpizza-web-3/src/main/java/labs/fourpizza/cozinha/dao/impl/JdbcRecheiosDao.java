package labs.fourpizza.cozinha.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import labs.fourpizza.cozinha.MontagemException;
import labs.fourpizza.cozinha.dao.RecheiosDao;
import labs.fourpizza.cozinha.dominio.Ingrediente;
import labs.fourpizza.cozinha.dominio.Recheio;
import labs.fourpizza.dao.DaoException;

/**
 * Implementação da interface {@code RecheiosDao} com suporte a banco de dados.
 */
public class JdbcRecheiosDao implements RecheiosDao {
	/**
	 * SQL para inserir novos ingredientes no banco de dados.
	 */
	private static final String SQL_INSERCAO = "INSERT INTO recheios "
			+ "(nome, descricao, valor) VALUES (?, ?, ?)";

	/**
	 * SQL para inserir uma relação de um ingrediente em um recheio
	 */
	private static final String SQL_INSERCAO_INGREDIENTES = "INSERT "
			+ "INTO recheios_ingredientes VALUES (?, ?)";

	/**
	 * SQL para listar todos recheios, sem nenhum dado de ingrediente ainda.
	 */
	private static final String SQL_LISTAR = "SELECT * FROM recheios";

	/**
	 * SQL utilizada para selecionar os ingredientes de um recheio baseado no
	 * seu ID.
	 */
	private static final String SQL_INGREDIENTES_RECHEIO = "SELECT * FROM "
			+ "ingredientes AS i INNER JOIN recheios_ingredientes AS ri "
			+ "ON i.id =  ri.ingrediente_id WHERE ri.recheio_id = ?";

	/**
	 * SQL parcial para buscar um recheio por nome
	 */
	private static final String SQL_BUSCA_POR_NOME = "SELECT * FROM recheios WHERE nome = ?";

	/**
	 * SQL parcial para buscar um recheio por ID
	 */
	private static final String SQL_BUSCA_ID = "SELECT * FROM recheios WHERE id = ?";

	/**
	 * SQL para remover um recheio do banco
	 */
	private static final String SQL_REMOCAO = "DELETE FROM recheios WHERE id = ?";

	/**
	 * Conexao com banco de dados.
	 */
	private Connection conexao;

	/**
	 * Construtor que recebe como parâmetro, uma conexão com o banco de dados.
	 * 
	 * @param conexao
	 *            Conexão com o banco de dados.
	 */
	public JdbcRecheiosDao(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void adicionar(Recheio recheio) throws DaoException {
		recheio.validar();

		Recheio recheioBanco = buscarPorNome(recheio.getNome());
		if (recheioBanco != null) {
			throw new DaoException("Recheio com esse nome já existe no banco");
		}

		try {
			// Realiza inserção e habilita retorno da chave primária
			// criada pelo banco.
			PreparedStatement ps = conexao.prepareStatement(SQL_INSERCAO,
					Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, recheio.getNome());
			ps.setString(2, recheio.getDescricao());
			ps.setFloat(3, recheio.getValor());
			ps.executeUpdate();

			ResultSet novasChavesPrimarias = ps.getGeneratedKeys();
			if (novasChavesPrimarias.next()) {
				recheio.setId(novasChavesPrimarias.getLong(1));
			}

			adicionarIngredientesRecheio(recheio);
		} catch (SQLException sqle) {
			throw new DaoException("Problema ao adicionar novo recheio", sqle);
		}
	}

	@Override
	public List<Recheio> listar() throws DaoException {
		try {
			PreparedStatement ps = conexao.prepareStatement(SQL_LISTAR);

			ResultSet rs = ps.executeQuery();
			return carregarRecheios(rs);

		} catch (SQLException sqle) {
			throw new DaoException("Problema ao realizar consulta", sqle);
		}
	}

	@Override
	public Recheio buscar(Long recheioId) throws DaoException {
		try {
			PreparedStatement ps = conexao.prepareStatement(SQL_BUSCA_ID);
			ps.setLong(1, recheioId);

			ResultSet rs = ps.executeQuery();
			List<Recheio> recheios = carregarRecheios(rs);
			if (recheios.size() == 0) {
				return null;
			} else {
				return recheios.get(0);
			}
		} catch (SQLException sqle) {
			throw new DaoException("Problema ao realizar consulta", sqle);
		}
	}

	@Override
	public Recheio buscarPorNome(String nomeRecheio) throws DaoException {
		try {
			PreparedStatement ps = conexao.prepareStatement(SQL_BUSCA_POR_NOME);
			ps.setString(1, nomeRecheio);

			ResultSet rs = ps.executeQuery();
			List<Recheio> recheios = carregarRecheios(rs);
			if (recheios.size() == 0) {
				return null;
			} else {
				return recheios.get(0);
			}
		} catch (SQLException sqle) {
			throw new DaoException("Problema ao realizar consulta", sqle);
		}
	}

	@Override
	public Recheio remover(String nomeRecheio) throws DaoException {
		try {
			Recheio recheio = buscarPorNome(nomeRecheio);
			if (recheio != null) {
				PreparedStatement ps = conexao.prepareStatement(SQL_REMOCAO);
				ps.setLong(1, recheio.getId());

				int alteracoes = ps.executeUpdate();
				if (alteracoes == 1) {
					return recheio;
				}
				return null;
			}
			return null;
		} catch (SQLException se) {
			throw new DaoException("Não foi possível realizar a pesquisa", se);
		}
	}

	/**
	 * Carrega ingredientes de um ResultSet em uma lista de {@code Ingredientes}
	 * 
	 * @param rs
	 *            ResultSet da consulta feita.
	 * @return Retorna lista com instâncias de {@code Ingrediente}
	 * @throws SQLException
	 *             Lançada quando algum problema ocorre com o ResultSet.
	 */
	private List<Recheio> carregarRecheios(ResultSet rs) throws SQLException {
		List<Recheio> recheios = new ArrayList<Recheio>();
		while (rs.next()) {
			Recheio recheio = new Recheio();
			recheio.setId(rs.getLong("id"));
			recheio.setNome(rs.getString("nome"));
			recheio.setValor(rs.getFloat("valor"));
			recheios.add(recheio);

			PreparedStatement ps = conexao
					.prepareStatement(SQL_INGREDIENTES_RECHEIO);
			ps.setLong(1, recheio.getId());
			ResultSet rsIngredientes = ps.executeQuery();

			try {
				List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
				while (rsIngredientes.next()) {
					Ingrediente ingrediente = new Ingrediente();
					ingrediente.setId(rsIngredientes.getLong("id"));
					ingrediente.setNome(rsIngredientes.getString("nome"));
					ingrediente.setValorPorPorcao(rsIngredientes
							.getFloat("valor_porcao"));
					ingredientes.add(ingrediente);
				}
				recheio.setIngredientes(ingredientes);
			} catch (MontagemException me) {
				throw new SQLException(
						"Ocorreu um erro ao carregar o recheio: "
								+ me.getMessage());
			}
		}
		return recheios;
	}

	private void adicionarIngredientesRecheio(Recheio recheio)
			throws DaoException {
		try {
			for (Ingrediente ingrediente : recheio.getIngredientes()) {
				PreparedStatement ps = conexao
						.prepareStatement(SQL_INSERCAO_INGREDIENTES);

				ps.setLong(1, recheio.getId());
				ps.setLong(2, ingrediente.getId());
				ps.executeUpdate();
			}
		} catch (SQLException sqle) {
			throw new DaoException("Problema ao adicionar novo recheio", sqle);
		}
	}

}