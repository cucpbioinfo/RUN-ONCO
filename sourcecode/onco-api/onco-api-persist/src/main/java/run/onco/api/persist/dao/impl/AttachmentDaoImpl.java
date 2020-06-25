package run.onco.api.persist.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import run.onco.api.persist.dao.AttachmentDao;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
public class AttachmentDaoImpl implements AttachmentDao {
	
	@PersistenceContext
	protected EntityManager em;
	
	protected Session getSession() {
		return em.unwrap(org.hibernate.Session.class); 
	}

}
