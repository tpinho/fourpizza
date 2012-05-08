package labs.fourpizza.cozinha.dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Ingrediente implements Serializable {
	private static final long serialVersionUID = 6335640201357379264L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String nome;
	private String descricao;
	private float valorPorcao;

	public Ingrediente() {
	}

	public Ingrediente(String nome, float valorPorcao) {
		this(nome, null, valorPorcao);
	}

	public Ingrediente(String nome, String descricao, float valorPorcao) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.valorPorcao = valorPorcao;
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

	public float getValorPorcao() {
		return valorPorcao;
	}

	public void setValorPorcao(float valorPorcao) {
		this.valorPorcao = valorPorcao;
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
		Ingrediente other = (Ingrediente) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}
