package fi.fabianadrian.afksystem.placeholder.miniplaceholders.resolver;

import fi.fabianadrian.afksystem.AFKSystem;
import fi.fabianadrian.afksystem.placeholder.miniplaceholders.AbstractResolver;
import io.github.miniplaceholders.api.resolver.AudienceTagResolver;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

public final class IndicatorResolver extends AbstractResolver implements AudienceTagResolver<Player> {
	private static final Tag TAG_AFK = Tag.selfClosingInserting(Component.translatable("afksystem.placeholder.indicator.afk"));
	private static final Tag TAG_NOT_AFK = Tag.selfClosingInserting(Component.translatable("afksystem.placeholder.indicator.not_afk"));

	public IndicatorResolver(AFKSystem plugin) {
		super(plugin);
	}

	@Override
	public @NonNull Tag tag(@NonNull Player player, @NonNull ArgumentQueue queue, @NonNull Context ctx) {
		return super.afkManager.afk(player) ? TAG_AFK : TAG_NOT_AFK;
	}
}
