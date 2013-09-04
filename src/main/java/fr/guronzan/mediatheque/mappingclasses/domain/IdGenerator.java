package fr.guronzan.mediatheque.mappingclasses.domain;

import java.util.UUID;

public final class IdGenerator {

    private IdGenerator() {
        // no instantiation
    }

    public static String createId() {
        final UUID uuid = java.util.UUID.randomUUID();
        return uuid.toString();
    }
}