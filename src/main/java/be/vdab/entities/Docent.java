package be.vdab.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedSubgraph;
import javax.persistence.Table;

import be.vdab.enums.Geslacht;

@Entity
@Table(name = "docenten")
@NamedEntityGraphs({
		@NamedEntityGraph(name = "Docent.metCampus", attributeNodes = @NamedAttributeNode("campus")),
		@NamedEntityGraph(name = "Docent.metCampusEnVerantwoordelijkheden", attributeNodes = {
				@NamedAttributeNode("campus"),
				@NamedAttributeNode("verantwoordelijkheden") }),
		@NamedEntityGraph(name = "Docent.metCampusEnManager", attributeNodes = @NamedAttributeNode(value = "campus", subgraph = "metManager"), subgraphs = @NamedSubgraph(name = "metManager", attributeNodes = @NamedAttributeNode("manager"))) })
public class Docent implements Serializable {
	private static final long serialVersionUID = 1L;
	@ElementCollection
	@CollectionTable(name = "docentenbijnamen", joinColumns = @JoinColumn(name = "docentid"))
	@Column(name = "Bijnaam")
	private Set<String> bijnamen;
	@Id
	@GeneratedValue
	private long id;
	private String voornaam;
	private String familienaam;
	private BigDecimal wedde;
	private long rijksRegisterNr;
	@Enumerated(EnumType.STRING)
	private Geslacht geslacht;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "campusid")
	private Campus campus;

	public Campus getCampus() {
		return campus;
	}

	public void setCampus(Campus campus) {
		if (this.campus != null && this.campus.getDocenten().contains(this)) {
			this.campus.removeDocent(this);
		}
		this.campus = campus;
		if (campus != null && !campus.getDocenten().contains(this)) {
			campus.addDocent(this);

		}
	}

	@ManyToMany
	@JoinTable(name = "docentenverantwoordelijkheden", joinColumns = @JoinColumn(name = "docentId"), inverseJoinColumns = @JoinColumn(name = "verantwoordelijkheidId"))
	private Set<Verantwoordelijkheid> verantwoordelijkheden;

	public void addVerantwoordelijkheid(
			Verantwoordelijkheid verantwoordelijkheid) {
		verantwoordelijkheden.add(verantwoordelijkheid);
		if (!verantwoordelijkheid.getDocenten().contains(this)) {
			verantwoordelijkheid.addDocent(this);
		}
	}

	public void removeVerantwoordelijkheid(
			Verantwoordelijkheid verantwoordelijkheid) {
		verantwoordelijkheden.remove(verantwoordelijkheid);
		if (verantwoordelijkheid.getDocenten().contains(this)) {
			verantwoordelijkheid.removeDocent(this);
		}
	}

	public Set<Verantwoordelijkheid> getVerantwoordelijkheden() {
		return Collections.unmodifiableSet(verantwoordelijkheden);
	}

	public Docent(String voornaam, String familienaam, BigDecimal wedde,
			Geslacht geslacht, long rijksRegisterNr) {
		bijnamen = new HashSet<>();
		setVoornaam(voornaam);
		setFamilienaam(familienaam);
		setWedde(wedde);
		setGeslacht(geslacht);
		setRijksRegisterNr(rijksRegisterNr);
		verantwoordelijkheden = new LinkedHashSet<>();
	}

	protected Docent() {
	}

	public Set<String> getBijnamen() {
		return Collections.unmodifiableSet(bijnamen);
	}

	public void addBijnaam(String bijnaam) {
		bijnamen.add(bijnaam);
	}

	public void removeBijnaam(String bijnaam) {
		bijnamen.remove(bijnaam);
	}

	public static boolean isVoornaamValid(String voornaam) {
		return voornaam != null && !voornaam.isEmpty();
	}

	public static boolean isFamilienaamValid(String familienaam) {
		return familienaam != null && !familienaam.isEmpty();
	}

	public static boolean isWeddeValid(BigDecimal wedde) {
		return wedde != null && wedde.compareTo(BigDecimal.ZERO) >= 0;
	}

	public static boolean isRijksRegisterNrValid(long rijksRegisterNr) {
		long getal = rijksRegisterNr / 100;
		if (rijksRegisterNr / 1_000_000_000 < 50) {
			getal += 2_000_000_000;
		}
		return rijksRegisterNr % 100 == 97 - getal % 97;
	}

	public void setVoornaam(String voornaam) {
		if (!isVoornaamValid(voornaam)) {
			throw new IllegalArgumentException();
		}
		this.voornaam = voornaam;
	}

	public void setFamilienaam(String familienaam) {
		if (!isFamilienaamValid(familienaam)) {
			throw new IllegalArgumentException();
		}
		this.familienaam = familienaam;
	}

	public void setWedde(BigDecimal wedde) {
		if (!isWeddeValid(wedde)) {
			throw new IllegalArgumentException();
		}
		this.wedde = wedde;
	}

	public void setGeslacht(Geslacht geslacht) {
		this.geslacht = geslacht;
	}

	public void setRijksRegisterNr(long rijksRegisterNr) {
		if (!isRijksRegisterNrValid(rijksRegisterNr)) {
			throw new IllegalArgumentException();
		}
		this.rijksRegisterNr = rijksRegisterNr;
	}

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

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Docent)) {
			return false;
		}
		return ((Docent) obj).rijksRegisterNr == rijksRegisterNr;
	}

	@Override
	public int hashCode() {
		return Long.valueOf(rijksRegisterNr).hashCode();
	}
}
