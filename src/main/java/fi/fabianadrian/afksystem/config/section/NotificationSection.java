package fi.fabianadrian.afksystem.config.section;

import space.arim.dazzleconf.engine.Comments;

public interface NotificationSection {
	@Comments("Should we notify player when they go AFK")
	@Comments("Player must have the afksystem.notification permission to be able to see the notification")
	default boolean afk() {
		return true;
	}

	@Comments("Should we notify player when they are no longer AFK")
	@Comments("Player must have the afksystem.notification permission to be able to see the notification")
	default boolean noLongerAfk() {
		return true;
	}

	@Comments("Should other players be notified when a player goes AFK")
	@Comments("Players must have the afksystem.notification.broadcast permission to be able to see the notification")
	default boolean afkBroadcast() {
		return true;
	}

	@Comments("Should other players be notified when a player is no longer AFK")
	@Comments("Players must have the afksystem.notification.broadcast permission to be able to see the notification")
	default boolean noLongerAfkBroadcast() {
		return true;
	}
}
