package fr.guronzan.mediatheque;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MediathequeApplicationContext {
	public static ApplicationContext context = null;

	public MediathequeApplicationContext() {
		context = new ClassPathXmlApplicationContext("spring.xml");
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public static <T> T getBean(final String clazz) {
		if (context == null) {
			new MediathequeApplicationContext();
		}
		return (T) context.getBean(clazz);
	}

	@SuppressWarnings("unused")
	public static <T> T getBean(final Class<T> clazz) {
		if (context == null) {
			new MediathequeApplicationContext();
		}
		return context.getBean(clazz);
	}
}
