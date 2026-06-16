package fi.fabianadrian.afksystem.placeholder;

import fi.fabianadrian.afksystem.AFKSystem;
import fi.fabianadrian.afksystem.placeholder.placeholder.BooleanFormat;
import fi.fabianadrian.afksystem.placeholder.placeholder.AfkPlaceholder;
import fi.fabianadrian.afksystem.placeholder.placeholder.IndicatorPlaceholder;
import fi.fabianadrian.afksystem.placeholder.placeholder.ListPlaceholder;
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

		Expansion.Builder builder = Expansion.builder("afksystem");

		builder.audiencePlaceholder(Player.class, "afk", (player, queue, _) -> {
			if (queue.hasNext()) {
				BooleanFormat format = BooleanFormat.valueOf(queue.pop().value().toUpperCase(Locale.ROOT));
				return this.afkPlaceholder.tag(player, format);
			}
			return this.afkPlaceholder.tag(player);
		});
		builder.audiencePlaceholder(Player.class, "indicator", (player, _, _) -> this.indicatorPlaceholder.tag(player));
		builder.globalPlaceholder("list", ((_, _) -> this.listPlaceholder.tag()));

		this.expansion = builder.build();
	}

	public void register() {
		this.expansion.register();
	}
}
