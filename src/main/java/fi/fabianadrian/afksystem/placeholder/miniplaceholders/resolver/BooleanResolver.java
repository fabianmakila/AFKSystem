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

public final class BooleanResolver extends AbstractResolver implements AudienceTagResolver<Player> {
	private static final Tag TAG_YES = Tag.preProcessParsed("yes");
	private static final Tag TAG_NO = Tag.preProcessParsed("no");
	private static final Tag TAG_TRUE = Tag.preProcessParsed("true");
	private static final Tag TAG_FALSE = Tag.preProcessParsed("false");
	private static final Tag TAG_TRANSLATABLE_TRUE = Tag.selfClosingInserting(Component.translatable("afksystem.placeholder.boolean.true"));
	private static final Tag TAG_TRANSLATABLE_FALSE = Tag.selfClosingInserting(Component.translatable("afksystem.placeholder.boolean.false"));

	public BooleanResolver(AFKSystem plugin) {
		super(plugin);
	}

	@Override
	public @NonNull Tag tag(@NonNull Player player, @NonNull ArgumentQueue queue, @NonNull Context ctx) {
		boolean afk = super.afkManager.afk(player);
		if (queue.hasNext()) {
			switch (queue.pop().lowerValue()) {
				case "yesno" -> {
					return afk ? TAG_YES : TAG_NO;
				}
				case "truefalse" -> {
					return afk ? TAG_TRUE : TAG_FALSE;
				}
			}
		}
		return afk ? TAG_TRANSLATABLE_TRUE : TAG_TRANSLATABLE_FALSE;
	}
}
