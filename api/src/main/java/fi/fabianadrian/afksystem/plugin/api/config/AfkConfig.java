package fi.fabianadrian.afksystem.plugin.api.config;

import fi.fabianadrian.afksystem.plugin.api.config.section.NotificationSection;
import fi.fabianadrian.afksystem.plugin.api.config.section.ProtectionSection;
import fi.fabianadrian.afksystem.plugin.api.event.EventType;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Locale;

public interface AfkConfig {
	@NonNull Locale defaultLocale();

	int afkMarkSeconds();

	int afkWarnSeconds();

	int afkKickSeconds();

	@NonNull List<@NonNull EventType> events();

	@NonNull ProtectionSection protection();

	@NonNull NotificationSection notification();
}
