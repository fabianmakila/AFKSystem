package fi.fabianadrian.afksystem.placeholder;

import fi.fabianadrian.afksystem.AFKSystem;
import fi.fabianadrian.afksystem.afk.AfkManager;
import fi.fabianadrian.afksystem.afk.AfkState;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Map;
import java.util.function.BiFunction;

public final class PlaceholderAPIExpansion extends PlaceholderExpansion {
	private static final TranslatableComponent COMPONENT_BOOLEAN_TRUE = Component.translatable("afksystem.boolean.true");
	private static final TranslatableComponent COMPONENT_BOOLEAN_FALSE = Component.translatable("afksystem.boolean.false");
	private static final TranslatableComponent INDICATOR_AFK = Component.translatable("afksystem.indicator.afk");
	private static final TranslatableComponent INDICATOR_NOT_AFK = Component.translatable("afksystem.indicator.not_afk");
	private final AfkManager afkManager;

	private final Map<String, BiFunction<Component, Locale, String>> serializers = Map.of(
			"plain", (component, locale) -> PlainTextComponentSerializer.plainText()
					.serialize(GlobalTranslator.render(component, locale)),

			"legacy_section", (component, player) -> LegacyComponentSerializer.legacySection()
					.serialize(GlobalTranslator.render(component, player)),

			"legacy_ampersand", (component, player) -> LegacyComponentSerializer.legacyAmpersand()
					.serialize(GlobalTranslator.render(component, player)),

			"minimessage", (component, _) -> MiniMessage.miniMessage().serialize(component)
	);

	public PlaceholderAPIExpansion(AFKSystem plugin) {
		this.afkManager = plugin.afkManager();
	}

	@Override
	public @NotNull String getIdentifier() {
		return "afksystem";
	}

	@Override
	public @NotNull String getAuthor() {
		return "FabianAdrian";
	}

	@Override
	public @NotNull String getVersion() {
		return "1.0.0";
	}

	@Override
	public boolean persist() {
		return true;
	}

	@Override
	public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
		String lower = params.toLowerCase(Locale.ROOT);
		if (lower.startsWith("boolean_")) {
			boolean afk = this.afkManager.afk(player);
			String key = lower.substring("boolean_".length());
			switch (key) {
				case "yesno" -> {
					return afk ? "yes" : "no";
				}
				case "truefalse" -> {
					return Boolean.toString(afk);
				}
				default -> {
					return serializeBoolean(afk, player, key);
				}
			}
		}
		if (lower.startsWith("state_")) {
			AfkState state = this.afkManager.state(player);
			String key = lower.substring("state_".length());
			switch (key) {
				case "lower" -> {
					return state.name().toLowerCase(Locale.ROOT);
				}
				case "upper" -> {
					return state.name();
				}
				default -> {
					return serializeComponent(state.translatable(), player, key);
				}
			}
		}
		if (lower.startsWith("indicator_")) {
			Component component = this.afkManager.afk(player) ? INDICATOR_AFK : INDICATOR_NOT_AFK;
			return serializeComponent(component, player, lower.substring("indicator_".length()));
		}
		return null;
	}

	private String serializeBoolean(boolean value, Player player, String key) {
		Component component = value ? COMPONENT_BOOLEAN_TRUE : COMPONENT_BOOLEAN_FALSE;
		return serializeComponent(component, player, key);
	}

	private String serializeComponent(Component component, Player player, String key) {
		BiFunction<Component, Locale, String> serializer = this.serializers.get(key);
		return serializer != null ? serializer.apply(component, player.locale()) : null;
	}
}
