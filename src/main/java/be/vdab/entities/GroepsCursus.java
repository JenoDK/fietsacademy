package be.vdab.entities;



import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "groepscursussen")
public class GroepsCursus extends Cursus {
	private static final long serialVersionUID = 1L;
	@Temporal(TemporalType.DATE)
	private Date van;
	@Temporal(TemporalType.DATE)
	private Date tot;
}