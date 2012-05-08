package labs.fourpizza.clientes;

public class ClienteException extends Exception {
	private static final long serialVersionUID = -1281380361118709585L;

	public ClienteException() {
	}

	public ClienteException(String msg) {
		super(msg);
	}

	public ClienteException(String msg, Exception e) {
		super(msg, e);
	}
}
