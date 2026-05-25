package fi.fabianadrian.afksystem;

import dev.faststats.bukkit.BukkitMetrics;
import dev.faststats.core.ErrorTracker;
import fi.fabianadrian.afksystem.afk.AfkManager;
import fi.fabianadrian.afksystem.command.AfkSystemCommand;
import fi.fabianadrian.afksystem.config.AfkConfig;
import fi.fabianadrian.afksystem.config.ConfigManager;
import fi.fabianadrian.afksystem.event.EventListener;
import fi.fabianadrian.afksystem.locale.TranslationManager;
import fi.fabianadrian.afksystem.placeholder.ExpansionManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class AFKSystem extends JavaPlugin {
	public static final ErrorTracker ERROR_TRACKER = ErrorTracker.contextAware();
	private final BukkitMetrics metrics = BukkitMetrics.factory()
			.token("88be6b8b8fe5f8221046675e57d5fd54")
			.errorTracker(ERROR_TRACKER)
			.create(this);
	private final AfkManager afkManager;
	private final ConfigManager configManager;
	private final ExpansionManager expansionManager;
	private final TranslationManager translationManager;

	public AFKSystem() {
		this.afkManager = new AfkManager(this);
		this.configManager = new ConfigManager(this);
		this.expansionManager = new ExpansionManager(this);
		this.translationManager = new TranslationManager(getSLF4JLogger(), getDataPath());
	}

	@Override
	public void onEnable() {
		this.metrics.ready();
		this.translationManager.load();
		this.expansionManager.register();
		new AfkSystemCommand(this).register();
		getServer().getPluginManager().registerEvents(new EventListener(this), this);

		try {
			this.configManager.load();
		} catch (Throwable throwable) {
			getSLF4JLogger().error("Couldn't load configuration", throwable);
			ERROR_TRACKER.trackError(throwable);
			return;
		}
		this.afkManager.load();
	}

	@Override
	public void onDisable() {
		this.metrics.shutdown();
	}

	public void load() throws IOException {
		this.translationManager.load();
		this.configManager.load();
		this.afkManager.load();
	}

	public AfkConfig config() {
		return this.configManager.config();
	}

	public AfkManager afkManager() {
		return this.afkManager;
	}
}
