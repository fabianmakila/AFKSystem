package fi.fabianadrian.afksystem;

import fi.fabianadrian.afksystem.afk.AfkManager;
import fi.fabianadrian.afksystem.command.AfkSystemCommand;
import fi.fabianadrian.afksystem.config.AfkConfig;
import fi.fabianadrian.afksystem.config.ConfigManager;
import fi.fabianadrian.afksystem.event.listener.ChatListener;
import fi.fabianadrian.afksystem.event.listener.PlayerListener;
import fi.fabianadrian.afksystem.locale.TranslationManager;
import fi.fabianadrian.afksystem.placeholder.ExpansionManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.List;

public final class AfkSystem extends JavaPlugin {
	private final ExpansionManager expansionManager = new ExpansionManager(this);
	private ConfigManager configManager;
	private AfkManager afkManager;
	private TranslationManager translationManager;

	@Override
	public void onLoad() {
		this.translationManager = new TranslationManager(getSLF4JLogger(), getDataPath());
		this.configManager = new ConfigManager(this);
		this.afkManager = new AfkManager(this);
	}

	@Override
	public void onEnable() {
		this.translationManager.load();
		this.expansionManager.register();
		new AfkSystemCommand(this).register();
		registerListeners();

		try {
			this.configManager.load();
		} catch (Throwable throwable) {
			getSLF4JLogger().error("Couldn't load configuration", throwable);
			return;
		}
		this.afkManager.load();
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

	private void registerListeners() {
		PluginManager manager = getServer().getPluginManager();
		List.of(
				new ChatListener(this),
				new PlayerListener(this)
		).forEach(listener -> manager.registerEvents(listener, this));
	}
}
