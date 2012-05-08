package labs.fourpizza.cozinha.dominio;

import java.io.Serializable;

/**
 * Representa um ingrediente que pode ser utilizado nos recheios das
 * pizza.
 */
public class Ingrediente implements Serializable {
    private static final long serialVersionUID = 836346887826680999L;

    private Long id;
    
    /**
     * Nome do ingrediente.
     */
    private String nome;
    
    /**
     * Valor de uma porção utilizada no recheio inteiro.
     */
    private float valorPorPorcao;
    
    public Ingrediente() {
    }

    public Ingrediente(String nome, float valorPorPorcao) {
        super();
        this.nome = nome;
        this.valorPorPorcao = valorPorPorcao;
    }
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean validar() {
        if (nome == null || "".equals(nome)) {
            return false;
        }
        return true;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getValorPorPorcao() {
        return valorPorPorcao;
    }

    public void setValorPorPorcao(float valorPorPorcao) {
        this.valorPorPorcao = valorPorPorcao;
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
