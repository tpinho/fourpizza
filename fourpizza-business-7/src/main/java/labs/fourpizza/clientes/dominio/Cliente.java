package labs.fourpizza.clientes.dominio;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
	@NamedQuery(name = "Cliente.listarClientes", query = "SELECT c FROM Cliente c"),
	@NamedQuery(name = "Cliente.buscarClientePorEmail", query = "SELECT c FROM Cliente c WHERE c.email = :email")
})
public class Cliente implements Serializable {
	private static final long serialVersionUID = -5666254968133932633L;

	public enum Sexo {
		MASCULINO, FEMININO
	}

	@Id
	@GeneratedValue
	private Long id;
	
	@Basic(optional = false)
	private String nome;
	
	@Basic(optional = false)
	private String rg;
	
	@Basic(optional = false)
	private String cpf;

	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	@Enumerated(EnumType.ORDINAL)
	private Sexo sexo;

	@Basic(optional = false)
	@Column(unique = true)
	private String email;
	private String password;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ENDERECO_ID")
	private Endereco endereco;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "cliente")
	private CartaoCreditoInfo cartaoCreditoInfo;
	
	private boolean ativo = true;
	
	public Cliente() {
		
	}

	public Cliente(String nome, String rg, String cpf, String email) {
		super();
		this.nome = nome;
		this.rg = rg;
		this.cpf = cpf;
		this.email = email;
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

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public CartaoCreditoInfo getCartaoCreditoInfo() {
		return cartaoCreditoInfo;
	}

	public void setCartaoCreditoInfo(CartaoCreditoInfo cartaoCreditoInfo) {
		this.cartaoCreditoInfo = cartaoCreditoInfo;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}
