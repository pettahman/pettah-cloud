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

import com.jackson.model.bdg.entities.Income;
import com.jackson.model.bdg.entities.User;

@Component
public class UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public List<User> retrieveAllUsers() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<User> cq = builder.createQuery(User.class);
		Root<User> root = cq.from(User.class);
		cq.select(root);
		return session.createQuery(cq).getResultList();
	}

	@Transactional
	public List<User> retrieveUserByUserUid(Integer userUid) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> c = cq.from(User.class);
		cq.select(c).where(cb.equal(c.get("userUid"), userUid));
		return session.createQuery(cq).getResultList();
	}

	@Transactional
	public List<Income> retrieveAllIncomes() {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Income> cq = builder.createQuery(Income.class);
		Root<Income> root = cq.from(Income.class);
		cq.select(root);
		return session.createQuery(cq).getResultList();
	}
}
