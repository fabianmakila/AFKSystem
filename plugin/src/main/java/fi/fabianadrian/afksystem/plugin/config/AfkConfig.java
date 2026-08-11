package fi.fabianadrian.afksystem.plugin.config;

import fi.fabianadrian.afksystem.plugin.api.event.EventType;
import fi.fabianadrian.afksystem.plugin.config.section.NotificationSection;
import fi.fabianadrian.afksystem.plugin.config.section.ProtectionSection;
import space.arim.dazzleconf.engine.Comments;
import space.arim.dazzleconf.engine.liaison.IntegerRange;
import space.arim.dazzleconf.engine.liaison.SubSection;

import java.util.List;
import java.util.Locale;

public interface AfkConfig extends fi.fabianadrian.afksystem.plugin.api.config.AfkConfig {
	@Comments("The plugin will prefer the player's language when available")
	@Comments("The fallback locale used when a translation is not available for the player's language")
	@Override
	default Locale defaultLocale() {
		return Locale.ENGLISH;
	}

	@Comments("Idle time in seconds before a player is marked as AFK")
	@Comments("Set to -1 to disable")
	@IntegerRange(min = -1)
	@Override
	default int afkMarkSeconds() {
		return 300;
	}

	@Comments("Seconds before the kick when a warning is sent")
	@Comments("Set to -1 to disable")
	@IntegerRange(min = -1)
	@Override
	default int afkWarnSeconds() {
		return 30;
	}

	@Comments("Total idle time in seconds before a player is kicked")
	@Comments("Set to -1 to disable")
	@IntegerRange(min = -1)
	@Override
	default int afkKickSeconds() {
		return 600;
	}

	@Comments("Which events will reset the AFK timer")
	@Comments("Supported values: BLOCK_BREAK, CHAT, COMMAND, INPUT, INTERACT")
	@Override
	default List<EventType> events() {
		return List.of(EventType.BLOCK_BREAK, EventType.CHAT, EventType.COMMAND, EventType.INPUT, EventType.INTERACT);
	}

	@SubSection
	@Override
	ProtectionSection protection();

	@SubSection
	@Override
	NotificationSection notification();
}
