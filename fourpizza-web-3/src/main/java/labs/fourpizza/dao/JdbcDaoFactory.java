package labs.fourpizza.dao;

import labs.fourpizza.cozinha.dao.IngredientesDao;
import labs.fourpizza.cozinha.dao.RecheiosDao;
import labs.fourpizza.cozinha.dao.impl.JdbcIngredientesDao;
import labs.fourpizza.cozinha.dao.impl.JdbcRecheiosDao;

/**
 * Implementação de DaoFactory que recupera DAOs que interagem com o
 * banco de dados.
 */
public class JdbcDaoFactory implements DaoFactory {

	@Override
	public synchronized IngredientesDao getIngredientesDao()
			throws DaoException {
		return new JdbcIngredientesDao(PersistenciaUtil.getConexao());
	}

	@Override
	public synchronized RecheiosDao getRecheiosDao() throws DaoException {
		return new JdbcRecheiosDao(PersistenciaUtil.getConexao());
	}
}
