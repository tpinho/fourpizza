package labs.fourpizza.cozinha;

/**
 * Exceção lançada normalmente quando há problemas no processo de
 * montagem de um recheio ou pizza.
 */
public class MontagemException extends Exception {
	private static final long serialVersionUID = -6511089720341517385L;

	public MontagemException() {
	}

	public MontagemException(String msg) {
		super(msg);
	}

	public MontagemException(String msg, Exception e) {
		super(msg, e);
	}
}
