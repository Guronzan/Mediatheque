package fr.guronzan.mediatheque.mappingclasses.domain.types;

import fr.guronzan.mediatheque.gui.createDialog.CreateBook;
import fr.guronzan.mediatheque.gui.createDialog.CreateCD;
import fr.guronzan.mediatheque.gui.createDialog.CreateDialog;
import fr.guronzan.mediatheque.gui.createDialog.CreateMovie;

public enum DataType {
    MOVIE("Movie", CreateMovie.class), MUSIC("Music", CreateCD.class), BOOK(
            "Book", CreateBook.class);
    private final String value;
    private Class<? extends CreateDialog> clazz;

    private DataType(final String value,
            final Class<? extends CreateDialog> clazz) {
        this.value = value;
        this.clazz = clazz;
    }

    public String getValue() {
        return this.value;
    }

    public Class<? extends CreateDialog> getClazz() {
        return this.clazz;
    }
}
