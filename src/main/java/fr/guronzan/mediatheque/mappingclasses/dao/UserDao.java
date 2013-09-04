package fr.guronzan.mediatheque.mappingclasses.dao;

import java.util.Collection;

import fr.guronzan.mediatheque.mappingclasses.domain.User;

public interface UserDao extends GenericDao<User, Integer> {
    User getUserById(int id);

    User getUserByFullName(String name, String forName);

    Collection<User> getUsers();

    User getUserByNickName(String nickName);

    User checkPassword(String nickName, String encryptedPassword);

    boolean containsUser(String nickName);

    void populateBooks(User userByNickName);

}