package be.vdab.dao;

import java.util.List;

import be.vdab.entities.Cursus;

public class CursusDAO extends AbstractDAO {
	public List<Cursus> findByNaamContains(String woord) {
		return getEntityManager()
				.createNamedQuery("Cursus.findByNaamContains", Cursus.class)
				.setParameter("zoals", '%' + woord + '%').getResultList();
	}
}