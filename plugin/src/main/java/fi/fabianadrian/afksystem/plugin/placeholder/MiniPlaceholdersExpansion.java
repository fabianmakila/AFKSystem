package fi.fabianadrian.afksystem.plugin.placeholder;

import fi.fabianadrian.afksystem.plugin.AFKSystem;
import fi.fabianadrian.afksystem.plugin.placeholder.placeholder.AfkPlaceholder;
import fi.fabianadrian.afksystem.plugin.placeholder.placeholder.BooleanFormat;
import fi.fabianadrian.afksystem.plugin.placeholder.placeholder.IndicatorPlaceholder;
import fi.fabianadrian.afksystem.plugin.placeholder.placeholder.ListPlaceholder;
import io.github.miniplaceholders.api.Expansion;
import org.bukkit.entity.Player;

import java.util.Locale;

public final class MiniPlaceholdersExpansion {
	private final Expansion expansion;
	private final AfkPlaceholder afkPlaceholder;
	private final IndicatorPlaceholder indicatorPlaceholder;
	private final ListPlaceholder listPlaceholder;

	public MiniPlaceholdersExpansion(AFKSystem plugin) {
		this.afkPlaceholder = new AfkPlaceholder(plugin);
		this.indicatorPlaceholder = new IndicatorPlaceholder(plugin);
		this.listPlaceholder = new ListPlaceholder(plugin);

		this.expansion = Expansion.builder("afksystem")
				.version(plugin.getPluginMeta().getVersion())
				.author(plugin.getPluginMeta().getAuthors().getFirst())
				.audiencePlaceholder(Player.class, "afk", (player, queue, _) -> {
					if (queue.hasNext()) {
						BooleanFormat format = BooleanFormat.valueOf(queue.pop().value().toUpperCase(Locale.ROOT));
						return this.afkPlaceholder.tag(player, format);
					}
					return this.afkPlaceholder.tag(player);
				})
				.audiencePlaceholder(Player.class, "indicator", (player, _, _) -> this.indicatorPlaceholder.tag(player))
				.globalPlaceholder("list", ((_, _) -> this.listPlaceholder.tag()))
				.build();
	}

	public void register() {
		this.expansion.register();
	}
}
