package fi.fabianadrian.afksystem.placeholder;

import fi.fabianadrian.afksystem.AfkSystem;
import fi.fabianadrian.afksystem.placeholder.miniplaceholders.MiniPlaceholdersExpansion;
import org.bukkit.plugin.PluginManager;

public final class ExpansionManager {
	private final AfkSystem plugin;

	public ExpansionManager(AfkSystem plugin) {
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
