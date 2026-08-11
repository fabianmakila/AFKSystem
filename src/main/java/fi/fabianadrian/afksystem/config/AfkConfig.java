package fi.fabianadrian.afksystem.config;

import fi.fabianadrian.afksystem.config.section.NotificationSection;
import fi.fabianadrian.afksystem.config.section.ProtectionSection;
import fi.fabianadrian.afksystem.event.EventType;
import space.arim.dazzleconf.engine.Comments;
import space.arim.dazzleconf.engine.liaison.IntegerRange;
import space.arim.dazzleconf.engine.liaison.SubSection;

import java.util.List;
import java.util.Locale;

public interface AfkConfig {
	@Comments("The plugin will prefer the player's language when available")
	@Comments("The fallback locale used when a translation is not available for the player's language")
	default Locale defaultLocale() {
		return Locale.ENGLISH;
	}

	@Comments("Idle time in seconds before a player is marked as AFK")
	@Comments("Set to -1 to disable")
	@IntegerRange(min = -1)
	default int afkMarkSeconds() {
		return 300;
	}

	@Comments("Seconds before the kick when a warning is sent")
	@Comments("Set to -1 to disable")
	@IntegerRange(min = -1)
	default int afkWarnSeconds() {
		return 30;
	}

	@Comments("Total idle time in seconds before a player is kicked")
	@Comments("Set to -1 to disable")
	@IntegerRange(min = -1)
	default int afkKickSeconds() {
		return 600;
	}

	@Comments("Which events will reset the AFK timer")
	@Comments("Supported values: BLOCK_BREAK, CHAT, COMMAND, INPUT, INTERACT")
	default List<EventType> events() {
		return List.of(EventType.BLOCK_BREAK, EventType.CHAT, EventType.COMMAND, EventType.INPUT, EventType.INTERACT);
	}

	@SubSection
	ProtectionSection protection();

	@SubSection
	NotificationSection notification();
}
