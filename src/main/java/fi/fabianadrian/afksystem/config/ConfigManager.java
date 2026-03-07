package fi.fabianadrian.afksystem.config;

import fi.fabianadrian.afksystem.AfkSystem;
import org.slf4j.Logger;
import space.arim.dazzleconf.Configuration;
import space.arim.dazzleconf.LoadResult;
import space.arim.dazzleconf.backend.Backend;
import space.arim.dazzleconf.backend.PathRoot;
import space.arim.dazzleconf.backend.toml.TomlBackend;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ConfigManager {
	private final Configuration<AfkConfig> configuration;
	private final Backend backend;
	private final Path dataDirectory;
	private final Logger logger;
	private AfkConfig config;

	public ConfigManager(AfkSystem plugin) {
		this.dataDirectory = plugin.getDataPath();
		this.logger = plugin.getSLF4JLogger();

		this.configuration = Configuration.defaultBuilder(AfkConfig.class).build();
		this.backend = new TomlBackend(new PathRoot(this.dataDirectory.resolve("config.toml")));
	}

	public void load() throws IOException {
		Files.createDirectories(this.dataDirectory);
		LoadResult<AfkConfig> result = this.configuration.configureWith(this.backend);

		if (result.isFailure() && this.config == null) {
			this.config = this.configuration.loadDefaults();
		}

		this.config = result.getOrThrow();
	}

	public AfkConfig config() {
		return this.config;
	}
}
