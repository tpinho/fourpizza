package labs.fourpizza.clientes;

import java.io.Serializable;
import java.util.Date;

import labs.fourpizza.clientes.dominio.Cliente.Sexo;

 
public class ClienteInfo implements Serializable {
	private static final long serialVersionUID = 6486102839115567734L;
	private String nome;
	private String rg;
	private String cpf;
	private Date dataNascimento;
	private Sexo sexo;
	
	public ClienteInfo() {}
	
	public ClienteInfo(String nome, String rg, String cpf) {
		this(nome, rg, cpf, null, null);
	}

	public ClienteInfo(String nome, String rg, String cpf, Date dataNascimento,
			Sexo sexo) {
		super();
		this.nome = nome;
		this.rg = rg;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.sexo = sexo;
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

}
