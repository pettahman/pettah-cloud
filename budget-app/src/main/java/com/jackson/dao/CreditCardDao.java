package com.jackson.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jackson.model.bdg.entities.CreditTransaction;

@Component
public class CreditCardDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public void saveCreditCardTransactions(CreditTransaction creditTransaction){				
		 Session session = sessionFactory.getCurrentSession();
		 session.save(creditTransaction);
	}
	
	@Transactional
	public List<CreditTransaction> retrieveAllCreditTrans(){				
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<CreditTransaction> query = builder.createQuery(CreditTransaction.class);
		Root<CreditTransaction> root = query.from(CreditTransaction.class);
	    query.select(root);
	    return session.createQuery(query).getResultList();
	}
}
