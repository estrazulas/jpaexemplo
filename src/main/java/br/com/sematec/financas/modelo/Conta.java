package br.com.sematec.financas.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
	@NamedQuery(name="Conta.movimentacoes",query="SELECT c.movimentacoes FROM Conta c WHERE c.numero = :numeroConta"),
	@NamedQuery(name="Conta.listaTodos", query="SELECT c FROM Conta c "),
	@NamedQuery(name="Conta.listaById", query="SELECT c FROM Conta c WHERE c.id_c = :id "),
})
public class Conta {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id_c;
	private String titular;
	private String banco;

	@ManyToOne
	private Agencia agencia;

	private String numero;

	@ManyToMany
	@JoinTable(name = "tab_associa", joinColumns = @JoinColumn(name = "id_conta", referencedColumnName = "id_c"), inverseJoinColumns = @JoinColumn(name = "id_pessoa", referencedColumnName = "id_p"))
	private List<Pessoa> listaTitular = new ArrayList<>();

	// bi-directional many-to-one association to Movimentacao
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "idConta")
	private List<Movimentacao> movimentacoes = new ArrayList<>();

	public List<Movimentacao> getMovimentacoes() {
		return movimentacoes;
	}

	public void setMovimentacoes(List<Movimentacao> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}

	public Integer getId_c() {
		return id_c;
	}

	public void setId_c(Integer id_c) {
		this.id_c = id_c;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	@Override
	public String toString() {

		return this.titular;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public List<Pessoa> getListaTitular() {
		return listaTitular;
	}

	public void setListaTitular(List<Pessoa> listaTitular) {
		this.listaTitular = listaTitular;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

}
