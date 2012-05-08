package labs.fourpizza.cozinha.dominio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Produto implements Serializable {
	private static final long serialVersionUID = -1901596616984010891L;
	
	@Id
	@GeneratedValue
	private Long id;
	private String nome;
	private String abreviacao;
	private String descricao;
	private float valor;

	public Produto() {
	}
	
	public Produto(String abreviacao, String nome, float valor) {
		this(abreviacao, nome, null, valor);
	}

	public Produto(String abreviacao, String nome, String descricao, float valor) {
		setAbreviacao(abreviacao);
		setNome(nome);
		setDescricao(descricao);
		setValor(valor);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAbreviacao() {
		return abreviacao;
	}

	public void setAbreviacao(String abreviacao) {
		if (abreviacao.length() > 20)
			throw new IllegalArgumentException(
					"Abreviacao maior que 10 caracteres.");
		this.abreviacao = abreviacao;
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

	@Override
	public String toString() {
		return abreviacao;
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
		Produto other = (Produto) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
}
