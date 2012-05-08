package labs.fourpizza.cozinha.dominio;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import labs.fourpizza.cozinha.MontagemException;

/**
 * Representa uma pizza tradicional ou montada pelo cliente. O valor
 * da pizza é baseado num valor padrão para pizzas medias e grandes.
 * Além disso, cada recheio conta com um valor diferente dependendo
 * dos ingredientes.
 */
@Entity
public class Pizza implements Serializable {
	private static final long serialVersionUID = -8879480792314931821L;

	/**
	 * Máximo de opções de recheio para um pizza
	 */
	public static final int MAX_RECHEIOS = 3;

	/**
	 * Preço base para pizzas médias
	 */
	public static final float PRECO_MEDIA = 11.0f;
	
	/**
	 * Preço base para pizzas grandes
	 */
	public static final float PRECO_GRANDE = 15.0f;

	/**
	 * Tamanhos possíveis para cada pizza. 
	 */
	public enum Tamanho {
		MEDIA, GRANDE
	}

	@Id
	@GeneratedValue
	private Long id;

	private Tamanho tamanho = Tamanho.GRANDE;

	private float valor;

	public Pizza() {
		
	}
	
	public Pizza(Tamanho tamanho) {
		this.tamanho = tamanho;
	}
	
	/**
	 * Recheios selecionados para esta pizza
	 */
	@ManyToMany
	private Set<Recheio> recheios = new HashSet<Recheio>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Adiciona um recheio à pizza. 
	 * 
	 * @param recheio Recheio que será adicionado.
	 */
	public void adicionar(Recheio recheio) {
		recheios.add(recheio);
		recalcularValor();
	}

	public void remover(Recheio recheio) {
		recheios.remove(recheio);
		recalcularValor();
	}

	private void recalcularValor() {
		float maiorValor = 0.0f;
		for (Recheio recheio : recheios) {
			if (maiorValor < recheio.getValor()) {
				maiorValor = recheio.getValor();
			}
		}
		valor = maiorValor;
		adicionarPrecoBase();
	}

	private void adicionarPrecoBase() {
		switch (tamanho) {
			case MEDIA:
				valor += PRECO_MEDIA;
				break;
			case GRANDE:
				valor += PRECO_GRANDE;
		}
	}

	public Set<Recheio> getRecheios() {
		return recheios;
	}

	public void setRecheios(Set<Recheio> recheios) {
		this.recheios = recheios;
	}

	public Tamanho getTamanho() {
		return tamanho;
	}

	public void setTamanho(Tamanho tamanho) {
		this.tamanho = tamanho;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public void validar() throws MontagemException {
		validarRecheios();
	}

	private void validarRecheios() throws MontagemException {
		if (recheios.size() < 1) {
			throw new MontagemException("Deve haver pelo menos um "
					+ "recheio.");
		} else if (recheios.size() > MAX_RECHEIOS) {
			throw new MontagemException("Númeo total de recheios inválido, "
					+ "máximo: " + MAX_RECHEIOS);
		}
	}

}
