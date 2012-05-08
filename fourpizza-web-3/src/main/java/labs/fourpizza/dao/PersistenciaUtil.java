package labs.fourpizza.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Possui algum métodos utilitarios para conexão com banco de dados.
 */
public class PersistenciaUtil {
	/**
	 * Localização do banco de dados
	 */
	public static final String FILE_DATABASE_PATH = "target/database/";

	/**
	 * Conexao com o banco de dados.
	 */
	private static Connection conexao;

	private PersistenciaUtil() {

	}

	/**
	 * Recupera uma conexão com o banco de dados, our crie uma caso
	 * ela não exista ainda.
	 * 
	 * @return Conexão com o banco de dados.
	 * @throws DaoException
	 */
	public static synchronized Connection getConexao() throws DaoException {
		if (conexao == null) {
			conexao = criarConexao();
		}
		return conexao;
	}

	/**
	 * Cria conexão com o banco de dados e retorna está conexão.
	 * 
	 * @return Conexão com o banco de dados.
	 * @throws DaoException Indica que a conexão nao pode ser criada
	 *         devido a algum problema.
	 */
	private static synchronized Connection criarConexao() throws DaoException {
		// Carrega driver do banco de dados e cria conexão com banco
		// em memória.
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			Connection conexao = DriverManager.getConnection(
					"jdbc:hsqldb:file:" + FILE_DATABASE_PATH + "/pizzariadb;shutdown=true;hsqldb.lock_file=false",
					"SA", "");
			return conexao;
		} catch (ClassNotFoundException e) {
			throw new DaoException("Problema ao carregar driver jdbc", e);
		} catch (SQLException e) {
			throw new DaoException("Problema ao inicializar banco de dados", e);
		}
	}

	/**
	 * Encerra uma conexão com o banco de dados quando não for mais
	 * utilizada.
	 * 
	 * @throws DaoException Indica que ocorreu algum problema no
	 *         encerramento da conexão.
	 */
	public static synchronized void encerrarConexao() throws DaoException {
		try {
			if (conexao != null) {
				conexao.close();
				conexao = null;
			}
		} catch (SQLException e) {
			throw new DaoException("Problema o encerrar conexão", e);
		}
	}

	/**
	 * Remove os diretórios do banco de dados.
	 * 
	 * @throws IOException Indica que algum arquivo não pode ser
	 *         removido.
	 */
	public static synchronized void apagarBancoDados() throws IOException {
		removerArquivo(new File(FILE_DATABASE_PATH));
	}

	private static void removerArquivo(File arquivo) throws IOException {
		if (arquivo.exists()) {
			if (arquivo.isDirectory()) {
				for (File filho : arquivo.listFiles())
					removerArquivo(filho);
			}
			if (!arquivo.delete()) {
				throw new FileNotFoundException("Não foi possivel remover o "
						+ "arquivo: " + arquivo.getCanonicalPath());
			}
		}
	}

	/**
	 * Popular banco de dados com SQL passada como parametro
	 * 
	 * @param sql SQL que será executada no banco de dados.
	 * @throws DaoException Indica que ocorreu algum problema na
	 *         execução da SQL
	 */
	public static synchronized void popularBanco(String sql)
			throws DaoException {
		if (conexao == null) {
			conexao = criarConexao();
		}

		// Carrega arquivo com a estrutura das tabelas e executa
		// no banco de dados
		try {
			Statement stmt = conexao.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			throw new DaoException("Problema executar sql do arquivo", e);
		}

	}
}
