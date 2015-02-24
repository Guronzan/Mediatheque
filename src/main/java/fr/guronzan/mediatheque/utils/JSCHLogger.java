package fr.guronzan.mediatheque.utils;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import ch.qos.logback.classic.Level;

@Slf4j
public class JSCHLogger implements com.jcraft.jsch.Logger {
	private final Map<Integer, Level> levels = new HashMap<Integer, Level>();

	public JSCHLogger() {
		// Mapping between JSch levels and our own levels
		this.levels.put(DEBUG, Level.DEBUG);
		this.levels.put(INFO, Level.INFO);
		this.levels.put(WARN, Level.WARN);
		this.levels.put(ERROR, Level.ERROR);
		this.levels.put(FATAL, Level.ERROR);

	}

	@Override
	public boolean isEnabled(final int pLevel) {
		return true; // here, all levels enabled
	}

	@Override
	public void log(final int pLevel, final String pMessage) {
		final Level level = this.levels.get(pLevel);

		if (Level.DEBUG.equals(level)) {
			log.debug(pMessage);
		} else if (Level.INFO.equals(level)) {
			log.info(pMessage);
		} else if (Level.WARN.equals(level)) {
			log.warn(pMessage);
		} else {
			// Default...
			log.error(pMessage);
		}
	}
}