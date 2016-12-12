package br.com.sematec.financas.modelo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

@Entity
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id_p;
	
	@ManyToMany
	@JoinTable(name="tab_associa", 
	joinColumns = @JoinColumn(name = "id_pessoa", referencedColumnName = "id_p"), 
	inverseJoinColumns = @JoinColumn(name = "id_conta", referencedColumnName = "id_c"))
	private List<Conta> contas;
	
	public List<Conta> getContas() {
		return contas;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}

	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getId_p() {
		return id_p;
	}
}
