package br.com.sematec.financas.modelo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the Agencia database table.
 * 
 */
@Embeddable
public class AgenciaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int cdBanco;

	private int cdAgencia;

	public AgenciaPK() {
	}

	
	public void setCdBanco(int cdBanco) {
		this.cdBanco = cdBanco;
	}


	public void setCdAgencia(int cdAgencia) {
		this.cdAgencia = cdAgencia;
	}


	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AgenciaPK)) {
			return false;
		}
		AgenciaPK castOther = (AgenciaPK)other;
		return 
			(this.cdBanco == castOther.cdBanco)
			&& (this.cdAgencia == castOther.cdAgencia);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cdBanco;
		hash = hash * prime + this.cdAgencia;
		
		return hash;
	}


	public int getCdBanco() {
		return cdBanco;
	}


	public int getCdAgencia() {
		return cdAgencia;
	}
	
}