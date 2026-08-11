package fi.fabianadrian.afksystem.plugin.api.config;

import fi.fabianadrian.afksystem.plugin.api.config.section.NotificationSection;
import fi.fabianadrian.afksystem.plugin.api.config.section.ProtectionSection;
import fi.fabianadrian.afksystem.plugin.api.event.EventType;

import java.util.List;
import java.util.Locale;

public interface AfkConfig {
	Locale defaultLocale();

	int afkMarkSeconds();

	int afkWarnSeconds();

	int afkKickSeconds();

	default List<EventType> events() {
		return List.of(EventType.BLOCK_BREAK, EventType.CHAT, EventType.COMMAND, EventType.INPUT, EventType.INTERACT);
	}

	ProtectionSection protection();

	NotificationSection notification();
}
