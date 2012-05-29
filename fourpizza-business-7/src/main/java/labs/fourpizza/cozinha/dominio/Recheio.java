package labs.fourpizza.cozinha.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import labs.fourpizza.cozinha.MontagemException;

@Entity
public class Recheio implements Serializable {
	public final static int MAX_INGREDIENTES = 7;

	private static final long serialVersionUID = -3993578063135398699L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String nome;
	private String descricao;
	private float valor;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private List<Ingrediente> ingredientes = new ArrayList<Ingrediente>();

	public Recheio() {
	}

	public Recheio(String nome) {
		this.nome = nome;
	}

	public void adicionarIngrediente(Ingrediente ingrediente) {
		ingredientes.add(ingrediente);
		calcularValor();
		criarDescricao();
	}

	public void removerIngrediente(Ingrediente ingrediente) {
		ingredientes.remove(ingrediente);
		calcularValor();
		criarDescricao();
	}

	private void calcularValor() {
		valor = 0.0f;
		for (Ingrediente ingrediente : ingredientes) {
			valor += ingrediente.getValorPorcao();
		}
	}

	private void criarDescricao() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < ingredientes.size(); i++) {
			if (i == ingredientes.size() - 1) {
				builder.append(" e " + ingredientes.get(i).getNome() + ".");
			} else if (i == 0) {
				builder.append(ingredientes.get(i).getNome());
			} else {
				builder.append(", " + ingredientes.get(i).getNome());
			}

		}
		descricao = builder.toString();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public List<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<Ingrediente> ingredientes)
			throws MontagemException {
		if(ingredientes == null || ingredientes.isEmpty()) {
			throw new MontagemException("Deve haver pelo menos um "
					+ "ingrediente.");
		}
		this.ingredientes = ingredientes;
	}

	public void validar() throws MontagemException {
		validarIngredientes();
	}

	private void validarIngredientes() throws MontagemException {
		if (ingredientes.size() < 1) {
			throw new MontagemException("Deve haver pelo menos um "
					+ "ingrediente.");
		} else if (ingredientes.size() > MAX_INGREDIENTES) {
			throw new MontagemException("Númeo total de ingredientes "
					+ "inválido, " + "máximo: " + MAX_INGREDIENTES);
		}
	}

	@Override
	public String toString() {
		return "[" + nome + ": " + descricao + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recheio other = (Recheio) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}
