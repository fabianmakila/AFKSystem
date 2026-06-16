package fi.fabianadrian.afksystem.placeholder.placeholder;

import fi.fabianadrian.afksystem.AFKSystem;
import fi.fabianadrian.afksystem.afk.AfkManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.entity.Player;

import java.util.Locale;
import java.util.Map;
import java.util.function.BiFunction;

public abstract class Placeholder {
	final AfkManager afkManager;
	private final Map<StringFormat, BiFunction<Component, Locale, String>> serializers = Map.of(
			StringFormat.PLAIN, (component, locale) -> PlainTextComponentSerializer.plainText()
					.serialize(GlobalTranslator.render(component, locale)),
			StringFormat.LEGACY_AMPERSAND, (component, player) -> LegacyComponentSerializer.legacyAmpersand()
					.serialize(GlobalTranslator.render(component, player)),
			StringFormat.LEGACY_SECTION, (component, player) -> LegacyComponentSerializer.legacySection()
					.serialize(GlobalTranslator.render(component, player)),
			StringFormat.MINIMESSAGE, (component, _) -> MiniMessage.miniMessage().serialize(component)
	);

	Placeholder(AFKSystem plugin) {
		this.afkManager = plugin.afkManager();
	}

	String serializeComponent(Component component, Player player, String format) {
		StringFormat parsedFormat = StringFormat.valueOf(format.toUpperCase(Locale.ROOT));
		BiFunction<Component, Locale, String> serializer = this.serializers.get(parsedFormat);
		return serializer != null ? serializer.apply(component, player.locale()) : null;
	}

	private enum StringFormat {
		PLAIN, LEGACY_AMPERSAND, LEGACY_SECTION, MINIMESSAGE
	}
}
