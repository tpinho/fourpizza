package labs.fourpizza.dao;

/**
 * Exceção lançada pela camada de acesso a dados quando alguma operação não
 * ocorreu com sucesso.
 */
public class DaoException extends Exception {
	private static final long serialVersionUID = 6071289539109717844L;

	public DaoException() {
	}

	public DaoException(String msg) {
		super(msg);
	}

	public DaoException(String msg, Exception e) {
		super(msg, e);
	}

}
