package fi.fabianadrian.afksystem.plugin.placeholder.placeholder;

import fi.fabianadrian.afksystem.plugin.AFKSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.minimessage.tag.Tag;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public final class IdleDurationPlaceholder extends Placeholder {
	public IdleDurationPlaceholder(AFKSystem plugin) {
		super(plugin);
	}

	public Tag tag(Player player) {
		return Tag.selfClosingInserting(duration(player.getIdleDuration()));
	}

	public String string(Player player, String format) {
		return serializeComponent(duration(player.getIdleDuration()), player, format);
	}

	private Component duration(Duration duration) {
		long hours = duration.toHours();
		int minutes = duration.toMinutesPart();
		int seconds = duration.toSecondsPart();

		List<Component> parts = new ArrayList<>(3);

		if (hours > 0) {
			parts.add(durationPart(hours, "hour"));
		}
		if (minutes > 0) {
			parts.add(durationPart(minutes, "minute"));
		}

		if (seconds > 0 || parts.isEmpty()) {
			parts.add(durationPart(seconds, "second"));
		}

		return Component.join(JoinConfiguration.spaces(), parts);
	}

	private Component durationPart(long value, String unit) {
		String key = "afksystem.placeholder.idle.duration." + unit + (value == 1 ? "" : "s");
		return Component.textOfChildren(
				Component.text(value + " "),
				Component.translatable(key)
		);
	}
}
