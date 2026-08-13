package fi.fabianadrian.afksystem.plugin.config;

import fi.fabianadrian.afksystem.plugin.AFKSystem;
import fi.fabianadrian.afksystem.plugin.config.liaison.LocaleLiaison;
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
	private AfkConfig config;

	public ConfigManager(AFKSystem plugin) {
		this.dataDirectory = plugin.getDataPath();

		this.configuration = Configuration.defaultBuilder(AfkConfig.class).addTypeLiaisons(new LocaleLiaison()).build();
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
		if (this.config == null) {
			throw new IllegalStateException("Config hasn't loaded yet");
		}
		return this.config;
	}
}
