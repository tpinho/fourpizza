package labs.fourpizza.cozinha.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import labs.fourpizza.cozinha.MontagemException;

/**
 * Representa um recheio composto por ingredientes que pode ser
 * adicionado a pizza.
 */
public class Recheio implements Serializable {
	private static final long serialVersionUID = -3186729288730925166L;

	/**
	 * Número máximo de ingredientes que podem ser incluso em um
	 * recheio.
	 */
	public static final int MAX_INGREDIENTES = 7;


	public Recheio() {
	}

	public Recheio(String nome) {

	}

	public void adicionarIngrediente(Ingrediente ingrediente) {
		
	}

	public void removerIngrediente(Ingrediente ingrediente) {
		
	}

	public Long getId() {
		return null;
	}

	public void setId(Long id) {

	}

	public String getNome() {
		return null;
	}

	public void setNome(String nome) {

	}

	public String getDescricao() {
		return null;
	}

	public void setDescricao(String descricao) {
	}

	public float getValor() {
		return 0;
	}

	public void setValor(float valor) {
	}

	public List<Ingrediente> getIngredientes() {
		return null;
	}

	public void setIngredientes(List<Ingrediente> ingredientes)
			throws MontagemException {
	}

	/**
	 * Valida se o recheio possui os atributos válidos como nome e
	 * quantidade de recheio.
	 * 
	 * @throws IllegalStateException Lançada se o estado dos atributos
	 *         do recheio são inválidos
	 */
	public void validar() {
		
	}
}
