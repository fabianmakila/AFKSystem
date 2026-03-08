package fi.fabianadrian.afksystem.placeholder.miniplaceholders;

import fi.fabianadrian.afksystem.AFKSystem;
import fi.fabianadrian.afksystem.afk.AfkManager;

public abstract class AbstractResolver {
	protected final AfkManager afkManager;

	public AbstractResolver(AFKSystem plugin) {
		this.afkManager = plugin.afkManager();
	}
}
