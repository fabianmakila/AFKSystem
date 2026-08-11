package fi.fabianadrian.afksystem.plugin.api.config.section;

public interface NotificationSection {
	boolean afk();

	boolean noLongerAfk();

	boolean afkBroadcast();

	boolean noLongerAfkBroadcast();
}
