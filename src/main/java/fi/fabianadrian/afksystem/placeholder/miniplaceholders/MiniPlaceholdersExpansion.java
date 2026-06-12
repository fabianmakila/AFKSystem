package fi.fabianadrian.afksystem.placeholder.miniplaceholders;

import fi.fabianadrian.afksystem.AFKSystem;
import fi.fabianadrian.afksystem.placeholder.miniplaceholders.resolver.BooleanResolver;
import fi.fabianadrian.afksystem.placeholder.miniplaceholders.resolver.IndicatorResolver;
import io.github.miniplaceholders.api.Expansion;
import org.bukkit.entity.Player;

public final class MiniPlaceholdersExpansion {
	private final Expansion expansion;

	public MiniPlaceholdersExpansion(AFKSystem plugin) {
		Expansion.Builder builder = Expansion.builder("afksystem");

		builder.audiencePlaceholder(Player.class, "boolean", new BooleanResolver(plugin));
		builder.audiencePlaceholder(Player.class, "indicator", new IndicatorResolver(plugin));

		this.expansion = builder.build();
	}

	public void register() {
		this.expansion.register();
	}
}
