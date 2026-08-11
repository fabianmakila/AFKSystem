package fi.fabianadrian.afksystem.plugin.placeholder;

import fi.fabianadrian.afksystem.plugin.AFKSystem;
import org.bukkit.plugin.PluginManager;

public final class ExpansionManager {
	private final AFKSystem plugin;

	public ExpansionManager(AFKSystem plugin) {
		this.plugin = plugin;
	}

	public void register() {
		PluginManager manager = this.plugin.getServer().getPluginManager();
		if (manager.isPluginEnabled("MiniPlaceholders")) {
			new MiniPlaceholdersExpansion(this.plugin).register();
		}
		if (manager.isPluginEnabled("PlaceholderAPI")) {
			new PlaceholderAPIExpansion(this.plugin).register();
		}
	}
}
