package fi.fabianadrian.afksystem.plugin.placeholder.placeholder;

import fi.fabianadrian.afksystem.plugin.AFKSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.minimessage.tag.Tag;
import org.bukkit.entity.Player;

public final class IndicatorPlaceholder extends Placeholder {
	private static final TranslatableComponent INDICATOR_AFK = Component.translatable("afksystem.placeholder.indicator.afk");
	private static final TranslatableComponent INDICATOR_NOT_AFK = Component.translatable("afksystem.placeholder.indicator.not_afk");

	public IndicatorPlaceholder(AFKSystem plugin) {
		super(plugin);
	}

	public Tag tag(Player player) {
		return Tag.selfClosingInserting(component(player));
	}

	public String string(Player player, String format) {
		return serializeComponent(component(player), player, format);
	}

	private Component component(Player player) {
		return super.afkManager.afk(player) ? INDICATOR_AFK : INDICATOR_NOT_AFK;
	}
}
