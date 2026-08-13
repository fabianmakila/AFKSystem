package fi.fabianadrian.afksystem.plugin.api;

import fi.fabianadrian.afksystem.plugin.api.config.AfkConfig;
import org.jspecify.annotations.NonNull;

public interface AfkSystemAPI {
	@NonNull AfkManager afkManager();

	/**
	 * @throws IllegalStateException if config isn't loaded
	 * @return AFKSystem main config
	 */
	AfkConfig config();
}
