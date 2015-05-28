package be.vdab.dao;

import java.math.BigDecimal;
import java.util.List;

import be.vdab.entities.Docent;

public class DocentDAO extends AbstractDAO {
	public Docent read(long id) {
		return getEntityManager().find(Docent.class, id);
	}

	public void create(Docent docent) {
		getEntityManager().persist(docent);
	}

	public void delete(long id) {
		Docent docent = getEntityManager().find(Docent.class, id);
		if (docent != null) {
			getEntityManager().remove(docent);
		}
	}

	public List<Docent> findByWeddeBetween(BigDecimal van, BigDecimal tot) {
		// je gebruikt van en tot later ...
		return getEntityManager().createQuery("select d from Docent d where d.wedde between 2000 and 2200 order by d.wedde, d.id",
				Docent.class).getResultList();
	}
}
