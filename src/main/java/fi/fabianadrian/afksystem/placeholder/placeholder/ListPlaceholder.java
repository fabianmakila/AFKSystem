package fi.fabianadrian.afksystem.placeholder.placeholder;

import fi.fabianadrian.afksystem.AFKSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.translation.Argument;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class ListPlaceholder extends Placeholder {
	private static final Component SEPARATOR = Component.translatable("afksystem.placeholder.list.separator");

	public ListPlaceholder(AFKSystem plugin) {
		super(plugin);
	}

	public Tag tag() {
		return Tag.selfClosingInserting(this::build);
	}

	public String string(Player player, String format) {
		return serializeComponent(build(), player, format);
	}

	private Component build() {
		List<Component> entries = new ArrayList<>();
		super.afkManager.afkPlayerList().forEach(player -> entries.add(
				Component.translatable("afksystem.placeholder.list.entry").arguments(
						Argument.string("name", player.getName()),
						Argument.component("displayname", player.displayName()),
						Argument.string("uuid", player.getUniqueId().toString())
				)
		));

		return Component.join(JoinConfiguration.separator(SEPARATOR), entries);
	}
}
