package br.com.sematec.financas.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Agencia database table.
 * 
 */
@Entity
@NamedQuery(name="Agencia.findAll", query="SELECT a FROM Agencia a")
public class Agencia implements Serializable {
	
	@Column(updatable=false, insertable=false, nullable=false)
	private int cdBanco;

	@Column(updatable=false, insertable=false, nullable=false)
	private int cdAgencia;

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AgenciaPK id;
	
	public Agencia() {
	}

	public void setCdBanco(int cdBanco) {
		this.cdBanco = cdBanco;
		if(id == null){
			id = new AgenciaPK();
		}
		id.setCdBanco(cdBanco);
	}

	public void setCdAgencia(int cdAgencia) {
		this.cdAgencia = cdAgencia;
		if(id == null){
			id = new AgenciaPK();
		}
		id.setCdAgencia(cdAgencia);		
	}

	public int getCdBanco() {
		return cdBanco;
	}

	public int getCdAgencia() {
		return cdAgencia;
	}
	
	

	

}