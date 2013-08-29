package fr.guronzan.mediatheque.mappingclasses.dao;

import java.util.Collection;

import fr.guronzan.mediatheque.mappingclasses.domain.CD;

public interface CDDao extends GenericDao<CD, Integer> {

	public CD getCDByID(int id);

	public CD getCdByTitle(String title);

	public Collection<CD> getCdsByAuthor(String name);

}