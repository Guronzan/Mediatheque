package fr.guronzan.mediatheque.gui;

import java.util.Comparator;

import fr.guronzan.mediatheque.mappingclasses.domain.DomainObject;

public class DomainObjectComparator implements Comparator<DomainObject> {

    @Override
    public int compare(final DomainObject o1, final DomainObject o2) {
        final String lblExpression1 = o1.getLblExpression();

        final String lblExpression2 = o2.getLblExpression();
        return lblExpression1.compareTo(lblExpression2);
    }

}
