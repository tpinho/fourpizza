package labs.fourpizza.cozinha.dao;

import java.util.List;

import labs.fourpizza.cozinha.dominio.Recheio;
import labs.fourpizza.dao.DaoException;

/**
* Abstrai e centraliza o acesso aos ingredientes na base de dados.
* Permite operações como adicionar, listar, recuperar e remover
* ingredientes.
*/
public interface RecheiosDao {
	/**
	 * Adiciona recheios a base de dados, se um recheio com esse nome já
	 * estiver persistido, o novo recheio não será persistido.
	 * 
	 * @param recheio Recheio que será persistido
	 * @throws DaoException Lançanda quando algum problema ocorreu na
	 *         adição de um novo recheio.
	 */
	public void adicionar(Recheio recheio) throws DaoException;

	/**
	 * Lista todos os recheios que estão persistidos na base de
	 * dados. Caso nenhum recheio esteja registrado, deve retornar
	 * uma lista vazia.
	 * @throws DaoException Lançanda quando algum problema ocorreu na
	 *         listagem de recheios.
	 */
	public List<Recheio> listar() throws DaoException;

	/**
	 * Recupera uma instância de {@code Recheio} através do ID
	 * passado como parâmetro. Se nenhum recheio for encontrado
	 * com esse ID, será retornado {@code null}.
	 * 
	 * @param recheioId ID do recheio que será recuperado
	 * @return Instância de {@code Recheio} recuperada.
	 * @throws DaoException Lançanda quando algum problema ocorreu na
	 *         adição de um novo recheio.
	 */
	public Recheio buscar(Long recheioId) throws DaoException;

	/**
	 * Recupera uma instância de {@code Recheio} através do nome
	 * passado como parâmetro. Se nenhum recheio for encontrado
	 * com esse nome, será retornado {@code null}.
	 * 
	 * @param nomeRecheio Nome do recheio que será recuperado.
	 * @return Instância de {@code Recheio} recuperada.
	 * @throws DaoException Lançanda quando algum problema ocorreu na
	 *         adição de um novo recheio.
	 */
	public Recheio buscarPorNome(String nomeRecheio)
			throws DaoException;

	/**
	 * Remove um recheio persistido na base de dados através do
	 * nome passado como argumento. Caso não exista nenhum recheio
	 * com esse nome, será retornado {@code null}.
	 * 
	 * @param nomeRecheio Nome do recheio que será removido.
	 * @return Instância de {@code Recheio} que foi removido.
	 * @throws DaoException Lançanda quando algum problema ocorreu na
	 *         adição de um novo recheio.
	 */
	public Recheio remover(String nomeRecheio) throws DaoException;
}
