package fi.fabianadrian.afksystem.placeholder.miniplaceholders;

import fi.fabianadrian.afksystem.AfkSystem;
import fi.fabianadrian.afksystem.afk.AfkManager;

public abstract class AbstractResolver {
	protected final AfkManager afkManager;

	public AbstractResolver(AfkSystem plugin) {
		this.afkManager = plugin.afkManager();
	}
}
