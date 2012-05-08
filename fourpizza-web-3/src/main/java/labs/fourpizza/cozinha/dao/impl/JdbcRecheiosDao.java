package labs.fourpizza.cozinha.dao.impl;

import java.sql.Connection;
import java.util.List;

import labs.fourpizza.cozinha.dao.RecheiosDao;
import labs.fourpizza.cozinha.dominio.Recheio;
import labs.fourpizza.dao.DaoException;

/**
 * Implementação da interface {@code RecheiosDao} com suporte a banco
 * de dados.
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
	 * SQL para listar todos recheios, sem nenhum dado de ingrediente
	 * ainda.
	 */
	private static final String SQL_LISTAR = "SELECT * FROM recheios";

	/**
	 * SQL utilizada para selecionar os ingredientes de um recheio
	 * baseado no seu ID.
	 */
	private static final String SQL_INGREDIENTES_RECHEIO = "SELECT * FROM "
			+ "ingredientes AS i INNER JOIN recheios_ingredientes AS ri "
			+ "ON i.id =  ri.ingrediente_id WHERE ri.recheio_id = ?";

	/**
	 * SQL parcial para buscar um recheio por nome
	 */
	private static final String SQL_BUSCA_POR_NOME = " WHERE nome = ?";

	/**
	 * SQL parcial para buscar um recheio por ID
	 */
	private static final String SQL_BUSCA_ID = " WHERE id = ?";

	/**
	 * SQL para remover um recheio do banco
	 */
	private static final String SQL_REMOCAO = "DELETE FROM recheios WHERE id = ?";

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
	public JdbcRecheiosDao(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void adicionar(Recheio recheio) throws DaoException {
		
	}

	@Override
	public List<Recheio> listar() throws DaoException {
		return null;
	}

	
	@Override
	public Recheio buscar(Long recheioId) throws DaoException {
		return null;

	}

	@Override
	public Recheio buscarPorNome(String nomeRecheio) throws DaoException {
		return null;
	}

	@Override
	public Recheio remover(String nomeRecheio) throws DaoException {
		return null;
	}
	
}
