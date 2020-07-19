package meli.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MutantData {
	

	@Id
	private String dna;

	private boolean isMutant;

	public String getDna() {
		return dna;
	}

	public void setDna(String dna) {
		this.dna = dna;
	}

	public boolean isMutant() {
		return isMutant;
	}

	public void setMutant(boolean isMutant) {
		this.isMutant = isMutant;
	}
}
