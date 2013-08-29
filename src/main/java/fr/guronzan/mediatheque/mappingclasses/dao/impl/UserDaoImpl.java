package fr.guronzan.mediatheque.mappingclasses.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fr.guronzan.mediatheque.mappingclasses.dao.UserDao;
import fr.guronzan.mediatheque.mappingclasses.domain.User;

@Repository("userDao")
@Scope("singleton")
@SuppressWarnings("unchecked")
public class UserDaoImpl extends GenericDaoImpl<User, Integer> implements
		UserDao {

	@Autowired
	public UserDaoImpl(
			@Qualifier("sessionFactory") final SessionFactory sessionFactory) {
		super(sessionFactory, User.class);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY, readOnly = true)
	public User getUserById(final int id) {
		final StringBuffer hql = new StringBuffer("select user from User user ");
		hql.append(" where user.user_id=:id ");
		final Query query = getSession().createQuery(hql.toString());

		query.setInteger("id", id);
		final List<User> list = query.list();
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY, readOnly = true)
	public User getUserByFullName(final String name, final String forName) {
		final StringBuffer hql = new StringBuffer("select user from User user ");
		hql.append(" where user.name=:name ");
		hql.append(" and user.forName=:forName ");
		final Query query = getSession().createQuery(hql.toString());

		query.setString("name", name);
		query.setString("forName", forName);
		final List<User> list = query.list();
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

}