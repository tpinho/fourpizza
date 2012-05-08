package labs.fourpizza.dao;

import labs.fourpizza.cozinha.dao.IngredientesDao;
import labs.fourpizza.cozinha.dao.RecheiosDao;

/**
 * Fabrica de DAOs usada para recupear referencias suas referências.
 */
public interface DaoFactory {
	/**
	 * Recupera o DAO utilizado para gerenciar instâncias de {@code Ingrediente}
	 * 
	 * @return Instância de {@code IngredientesDao}
	 * @throws DaoException Lançado quando ocorre algum problema recuperando o DAO
	 */
	public IngredientesDao getIngredientesDao() throws DaoException;
	
	/**
	 * Recupera o DAO utilizado para gerenciar instâncias de {@code Recheio}
	 * 
	 * @return Instância de {@code RecheiosDao}
	 * @throws DaoException Lançado quando ocorre algum problema recuperando o DAO 
	 */
	public RecheiosDao getRecheiosDao() throws DaoException;
}
