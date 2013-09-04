package fr.guronzan.mediatheque.mappingclasses.dao;

import java.util.Collection;

import fr.guronzan.mediatheque.mappingclasses.domain.CD;

public interface CDDao extends GenericDao<CD, Integer> {

    CD getCDByID(int id);

    CD getCdByTitle(String title);

    Collection<CD> getCdsByAuthor(String name);

}