package fi.fabianadrian.afksystem.config;

import fi.fabianadrian.afksystem.config.section.ProtectionSection;
import fi.fabianadrian.afksystem.event.EventType;
import space.arim.dazzleconf.engine.Comments;
import space.arim.dazzleconf.engine.liaison.SubSection;

import java.util.List;

public interface AfkConfig {
	@Comments("Idle time in seconds before a player is marked as AFK")
	default int afkMarkSeconds() {
		return 300;
	}

	@Comments("Total idle time in seconds before a player is kicked")
	default int afkKickSeconds() {
		return 600;
	}

	@Comments("Seconds before the kick when a warning is sent")
	default int afkWarnBeforeKickSeconds() {
		return 30;
	}

	@Comments("Which events will reset the AFK timer")
	@Comments("Supported values: CHAT, INPUT")
	default List<EventType> events() {
		return List.of(EventType.CHAT, EventType.INPUT);
	}

	@SubSection
	ProtectionSection protection();
}
