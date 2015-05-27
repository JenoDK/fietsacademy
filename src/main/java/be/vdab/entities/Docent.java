package be.vdab.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import be.vdab.enums.Geslacht;

@Entity
@Table(name = "docenten")
public class Docent implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long id;
	private String voornaam;
	private String familienaam;
	private BigDecimal wedde;
	private long rijksRegisterNr;
	@Enumerated(EnumType.STRING)
	private Geslacht geslacht;

	public long getId() {
		return id;
	}

	public String getVoornaam() {
		return voornaam;
	}

	public String getFamilienaam() {
		return familienaam;
	}

	public BigDecimal getWedde() {
		return wedde;
	}

	public long getRijksRegisterNr() {
		return rijksRegisterNr;
	}
	
	public Geslacht getGeslacht() {
		return geslacht;
	}

	public void opslag(BigDecimal percentage) {
		BigDecimal factor = BigDecimal.ONE.add(percentage.divide(BigDecimal
				.valueOf(100)));
		wedde = wedde.multiply(factor).setScale(2, RoundingMode.HALF_UP);
	}

	public String getNaam() {
		return voornaam + ' ' + familienaam;
	}
}
