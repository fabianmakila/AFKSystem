package fi.fabianadrian.afksystem.config.section;

public interface NotificationSection {
	default boolean afk() {
		return true;
	}

	default boolean noLongerAfk() {
		return false;
	}
}
