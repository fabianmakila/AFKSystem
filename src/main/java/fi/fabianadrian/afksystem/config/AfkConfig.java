package fi.fabianadrian.afksystem.config;

import fi.fabianadrian.afksystem.config.section.ProtectionSection;
import fi.fabianadrian.afksystem.event.EventType;
import space.arim.dazzleconf.engine.Comments;
import space.arim.dazzleconf.engine.liaison.IntegerRange;
import space.arim.dazzleconf.engine.liaison.SubSection;

import java.util.List;

public interface AfkConfig {
	@Comments("Idle time in seconds before a player is marked as AFK")
	@Comments("Set to -1 to disable")
	@IntegerRange(min = -1)
	default int afkMarkSeconds() {
		return 300;
	}

	@Comments("Total idle time in seconds before a player is warned about being kicked soon")
	@Comments("This should be less than afkKickSeconds")
	@Comments("Set to -1 to disable")
	@IntegerRange(min = -1)
	default int afkWarnSeconds() {
		return 570;
	}

	@Comments("Total idle time in seconds before a player is kicked")
	@Comments("Set to -1 to disable")
	@IntegerRange(min = -1)
	default int afkKickSeconds() {
		return 600;
	}

	@Comments("Which events will reset the AFK timer")
	@Comments("Supported values: CHAT, INPUT")
	default List<EventType> events() {
		return List.of(EventType.CHAT, EventType.INPUT);
	}

	@SubSection
	ProtectionSection protection();
}
