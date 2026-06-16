package fi.fabianadrian.afksystem.message;

import fi.fabianadrian.afksystem.AFKSystem;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.minimessage.translation.MiniMessageTranslationStore;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.Translator;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public final class TranslationManager {
	private static final List<Locale> BUNDLED_LOCALES = List.of(Locale.US, Locale.of("fi", "FI"));
	private final Path localeDirectoryPath;

	private final Logger logger;
	private MiniMessageTranslationStore store;

	public TranslationManager(Logger logger, Path dataDirectory) {
		this.logger = logger;
		this.localeDirectoryPath = dataDirectory.resolve("locale");
	}

	private static boolean isAdventureDuplicatesException(Exception e) {
		return e instanceof IllegalArgumentException && (e.getMessage().startsWith("Invalid key") || e.getMessage().startsWith("Translation already exists"));
	}

	public void load() {
		if (this.store != null) {
			GlobalTranslator.translator().removeSource(this.store);
		}

		this.store = MiniMessageTranslationStore.create(Key.key("afksystem", "main"));

		createLocaleDirectory();
		copyToLocaleDirectory();
		registerFromLocaleDirectory();
		registerDefaultLocale();

		GlobalTranslator.translator().addSource(this.store);
	}

	public void defaultLocale(Locale locale) {
		this.store.defaultLocale(locale);
	}

	private void createLocaleDirectory() {
		try {
			Files.createDirectories(this.localeDirectoryPath);
		} catch (IOException exception) {
			AFKSystem.ERROR_TRACKER.trackError(exception);
			this.logger.error("Couldn't create locale directory", exception);
		}
	}

	private void copyToLocaleDirectory() {
		if (localeDirectoryContainsTranslations()) {
			return;
		}

		BUNDLED_LOCALES.forEach(locale -> {
			String fileName = "messages_" + locale.getLanguage() + ".properties";
			Path defaultBundlePath = this.localeDirectoryPath.resolve(fileName);
			if (Files.exists(defaultBundlePath)) {
				return;
			}

			try {
				Files.copy(this.getClass().getClassLoader().getResourceAsStream(fileName), defaultBundlePath);
			} catch (IOException exception) {
				AFKSystem.ERROR_TRACKER.trackError(exception);
				this.logger.error("Couldn't write bundled locale", exception);
			}
		});
	}

	private boolean localeDirectoryContainsTranslations() {
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.localeDirectoryPath, "*.properties")) {
			if (stream.iterator().hasNext()) {
				return true;
			}
		} catch (IOException exception) {
			AFKSystem.ERROR_TRACKER.trackError(exception);
			this.logger.error("Couldn't read locale directory", exception);
			return true; //Assume there are files even if we can't read them
		}
		return false;
	}

	private void registerFromLocaleDirectory() {
		StringJoiner loadedLocaleNamesJoiner = new StringJoiner(", ");

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.localeDirectoryPath, "*.properties")) {
			for (Path localeFilePath : stream) {
				String fileName = localeFilePath.getFileName().toString();
				if (!fileName.startsWith("messages_")) {
					this.logger.warn("Couldn't load {}. Locale files must follow specified naming convention", fileName);
					continue;
				}

				Locale locale = parseLocaleFromFileName(fileName);
				loadedLocaleNamesJoiner.add(locale.getLanguage());

				ResourceBundle bundle;
				try (BufferedReader reader = Files.newBufferedReader(localeFilePath, StandardCharsets.UTF_8)) {
					bundle = new PropertyResourceBundle(reader);
				}

				this.store.registerAll(locale, bundle, false);
			}

			if (loadedLocaleNamesJoiner.length() != 0) {
				this.logger.info("Loaded locales: {}", loadedLocaleNamesJoiner);
			}
		} catch (IOException exception) {
			AFKSystem.ERROR_TRACKER.trackError(exception);
			this.logger.warn("Couldn't read the locale directory", exception);
		}
	}

	private void registerDefaultLocale() {
		ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.US);
		try {
			this.store.registerAll(Locale.US, bundle, false);
		} catch (IllegalArgumentException exception) {
			if (isAdventureDuplicatesException(exception)) {
				return;
			}
			AFKSystem.ERROR_TRACKER.trackError(exception);
			this.logger.error("Error registering default locale", exception);
		}
	}

	private Locale parseLocaleFromFileName(String fileName) {
		String localeString = fileName.substring(
				"messages_".length(),
				fileName.length() - ".properties".length()
		);
		Locale locale = Translator.parseLocale(localeString);
		if (locale == null) {
			throw new IllegalStateException("Couldn't parse locale for file name: " + fileName);
		}
		return locale;
	}
}
