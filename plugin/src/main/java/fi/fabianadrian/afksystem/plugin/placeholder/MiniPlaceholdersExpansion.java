package fi.fabianadrian.afksystem.plugin.placeholder;

import fi.fabianadrian.afksystem.plugin.AFKSystem;
import fi.fabianadrian.afksystem.plugin.placeholder.placeholder.*;
import io.github.miniplaceholders.api.Expansion;
import org.bukkit.entity.Player;

import java.util.Locale;

public final class MiniPlaceholdersExpansion {
	private final Expansion expansion;

	public MiniPlaceholdersExpansion(AFKSystem plugin) {
		AfkPlaceholder afkPlaceholder = new AfkPlaceholder(plugin);
		IndicatorPlaceholder indicatorPlaceholder = new IndicatorPlaceholder(plugin);
		ListPlaceholder listPlaceholder = new ListPlaceholder(plugin);
		IdleDurationPlaceholder idleDurationPlaceholder = new IdleDurationPlaceholder(plugin);

		this.expansion = Expansion.builder("afksystem")
				.version(plugin.getPluginMeta().getVersion())
				.author(plugin.getPluginMeta().getAuthors().getFirst())
				.audiencePlaceholder(Player.class, "afk", (player, queue, _) -> {
					if (queue.hasNext()) {
						BooleanFormat format = BooleanFormat.valueOf(queue.pop().value().toUpperCase(Locale.ROOT));
						return afkPlaceholder.tag(player, format);
					}
					return afkPlaceholder.tag(player);
				})
				.audiencePlaceholder(Player.class, "indicator", (player, _, _) -> indicatorPlaceholder.tag(player))
				.audiencePlaceholder(Player.class, "idle_duration", (player, _, _) -> idleDurationPlaceholder.tag(player))
				.globalPlaceholder("list", ((_, _) -> listPlaceholder.tag()))
				.build();
	}

	public void register() {
		this.expansion.register();
	}
}
