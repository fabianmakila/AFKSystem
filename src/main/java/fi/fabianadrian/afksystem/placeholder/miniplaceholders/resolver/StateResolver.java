package fi.fabianadrian.afksystem.placeholder.miniplaceholders.resolver;

import fi.fabianadrian.afksystem.AFKSystem;
import fi.fabianadrian.afksystem.afk.AfkState;
import fi.fabianadrian.afksystem.placeholder.miniplaceholders.AbstractResolver;
import io.github.miniplaceholders.api.resolver.AudienceTagResolver;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.Locale;

public final class StateResolver extends AbstractResolver implements AudienceTagResolver<Player> {
	public StateResolver(AFKSystem plugin) {
		super(plugin);
	}

	@Override
	public @NonNull Tag tag(@NonNull Player player, @NonNull ArgumentQueue queue, @NonNull Context ctx) {
		AfkState state = super.afkManager.state(player);
		if (queue.hasNext()) {
			switch (queue.pop().lowerValue()) {
				case "lower" -> {
					return Tag.preProcessParsed(state.name().toLowerCase(Locale.ROOT));
				}
				case "upper" -> {
					return Tag.preProcessParsed(state.name());
				}
			}
		}

		return Tag.selfClosingInserting(state.translatable());
	}
}
