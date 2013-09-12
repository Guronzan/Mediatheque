package fr.guronzan.mediatheque;

public enum DataType {
	MOVIE("Movie"), MUSIC("Music"), BOOK("Book");
	private final String value;

	private DataType(final String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
