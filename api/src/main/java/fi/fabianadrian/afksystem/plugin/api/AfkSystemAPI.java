package fi.fabianadrian.afksystem.plugin.api;

import fi.fabianadrian.afksystem.plugin.api.config.AfkConfig;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public interface AfkSystemAPI {
	@NonNull AfkManager afkManager();

	@Nullable AfkConfig config();
}
