package fr.guronzan.mediatheque.mappingclasses.dao;

import java.util.UUID;

public class IdGenerator {

	public static String createId() {
		final UUID uuid = java.util.UUID.randomUUID();
		return uuid.toString();
	}
}