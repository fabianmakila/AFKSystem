package fi.fabianadrian.afksystem.placeholder;

import fi.fabianadrian.afksystem.AFKSystem;
import fi.fabianadrian.afksystem.placeholder.placeholder.AfkPlaceholder;
import fi.fabianadrian.afksystem.placeholder.placeholder.IndicatorPlaceholder;
import fi.fabianadrian.afksystem.placeholder.placeholder.ListPlaceholder;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public final class PlaceholderAPIExpansion extends PlaceholderExpansion {
	private final AfkPlaceholder afkPlaceholder;
	private final IndicatorPlaceholder indicatorPlaceholder;
	private final ListPlaceholder listPlaceholder;
	private final AFKSystem plugin;

	public PlaceholderAPIExpansion(AFKSystem plugin) {
		this.plugin = plugin;
		this.afkPlaceholder = new AfkPlaceholder(plugin);
		this.indicatorPlaceholder = new IndicatorPlaceholder(plugin);
		this.listPlaceholder = new ListPlaceholder(plugin);
	}

	@Override
	public @NotNull String getIdentifier() {
		return "afksystem";
	}

	@Override
	public @NotNull String getAuthor() {
		return this.plugin.getPluginMeta().getAuthors().getFirst();
	}

	@Override
	public @NotNull String getVersion() {
		return this.plugin.getPluginMeta().getVersion();
	}

	@Override
	public boolean persist() {
		return true;
	}

	@Override
	public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
		String[] split = params.toLowerCase(Locale.ROOT).split("_", 2);
		if (split.length < 2) {
			return null;
		}
		switch (split[0]) {
			case "afk" -> {
				return this.afkPlaceholder.string(player, split[1]);
			}
			case "indicator" -> {
				return this.indicatorPlaceholder.string(player, split[1]);
			}
			case "list" -> {
				return this.listPlaceholder.string(player, split[1]);
			}
		}
		return null;
	}
}
