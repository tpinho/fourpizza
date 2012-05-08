package labs.fourpizza.cozinha.dao;

import java.util.List;

import labs.fourpizza.cozinha.dominio.Ingrediente;
import labs.fourpizza.dao.DaoException;

/**
 * Abstrai e centraliza o acesso aos ingredientes na base de dados.
 * Permite operações como adicionar, listar, recuperar e remover
 * ingredientes.
 */
public interface IngredientesDao {
	/**
	 * Adiciona ingrediente a base de dados, se um ingrediente já
	 * estiver persistido, o novo ingrendete não deve substituir o
	 * antigo.
	 * 
	 * @param ingrediente Ingrediente que será persistido
	 * @throws DaoException Lançanda quando algum problema ocorreu na
	 *         adição de um novo ingrediente.
	 */
	public void adicionar(Ingrediente ingrediente) throws DaoException;

	/**
	 * Lista todos os ingrediente que estão persistidos na base de
	 * dados. Caso nenhum ingrediente esteja registrado, deve retornar
	 * uma lista vazia.
	 * @throws DaoException Lançanda quando algum problema ocorreu na
	 *         listagem de ingredientes.
	 */
	public List<Ingrediente> listar() throws DaoException;

	/**
	 * Recupera uma instância de {@code Ingrediente} através do ID
	 * passado como parâmetro. Se nenhum ingrediente for encontrado
	 * com esse ID, será retornado {@code null}.
	 * 
	 * @param ingredienteId ID do ingrediente que será recuperado
	 * @return Instância de {@code Ingrediente} recuperada.
	 * @throws DaoException Lançanda quando algum problema ocorreu na
	 *         adição de um novo ingrediente.
	 */
	public Ingrediente buscar(Long ingredienteId) throws DaoException;

	/**
	 * Recupera uma instância de {@code Ingrediente} através do nome
	 * passado como parâmetro. Se nenhum ingrediente for encontrado
	 * com esse nome, será retornado {@code null}.
	 * 
	 * @param nomeIngrediente Nome do ingrediente que será recuperado.
	 * @return Instância de {@code Ingrediente} recuperada.
	 * @throws DaoException Lançanda quando algum problema ocorreu na
	 *         adição de um novo ingrediente.
	 */
	public Ingrediente buscarPorNome(String nomeIngrediente)
			throws DaoException;

	/**
	 * Remove um ingrediente persistido na base de dados através do
	 * nome passado como argumento. Caso não exista nenhum ingrediente
	 * com esse nome, será retornado {@code null}.
	 * 
	 * @param nomeIngrediente Nome do ingrediente que será removido.
	 * @return Instância de {@code Ingrediente} que foi removido.
	 * @throws DaoException Lançanda quando algum problema ocorreu na
	 *         adição de um novo ingrediente.
	 */
	public Ingrediente remover(String nomeIngrediente) throws DaoException;
}
