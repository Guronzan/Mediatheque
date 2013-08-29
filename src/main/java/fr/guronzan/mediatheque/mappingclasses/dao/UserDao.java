package fr.guronzan.mediatheque.mappingclasses.dao;

import fr.guronzan.mediatheque.mappingclasses.domain.User;

public interface UserDao extends GenericDao<User, Integer> {
	public User getUserById(int id);

	public User getUserByFullName(String name, String forName);

}