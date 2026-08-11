package fi.fabianadrian.afksystem.plugin.api;

import org.jspecify.annotations.NonNull;

public interface AfkSystemAPI {
	@NonNull
	AfkManager afkManager();
}
