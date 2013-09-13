package fr.guronzan.mediatheque.mappingclasses.domain.types;

import fr.guronzan.mediatheque.gui.createDialog.CreateBook;
import fr.guronzan.mediatheque.gui.createDialog.CreateCD;
import fr.guronzan.mediatheque.gui.createDialog.CreateDialog;
import fr.guronzan.mediatheque.gui.createDialog.CreateMovie;
import fr.guronzan.mediatheque.mappingclasses.dao.BookDao;
import fr.guronzan.mediatheque.mappingclasses.dao.CDDao;
import fr.guronzan.mediatheque.mappingclasses.dao.GenericDao;
import fr.guronzan.mediatheque.mappingclasses.dao.MovieDao;
import fr.guronzan.mediatheque.mappingclasses.domain.DomainObject;

public enum DataType {
    MOVIE("Movie", CreateMovie.class, MovieDao.class), MUSIC("Music",
            CreateCD.class, CDDao.class), BOOK("Book", CreateBook.class,
            BookDao.class);
    private final String value;
    private Class<? extends CreateDialog> clazz;
    private Class<? extends GenericDao<? extends DomainObject, Integer>> dao;

    private DataType(
            final String value,
            final Class<? extends CreateDialog> clazz,
            final Class<? extends GenericDao<? extends DomainObject, Integer>> dao) {
        this.value = value;
        this.clazz = clazz;
        this.dao = dao;
    }

    public String getValue() {
        return this.value;
    }

    public Class<? extends CreateDialog> getClazz() {
        return this.clazz;
    }

    public Class<? extends GenericDao<?, Integer>> getDao() {
        return this.dao;
    }
}
