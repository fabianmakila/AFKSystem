package fi.fabianadrian.afksystem.config.section;

import space.arim.dazzleconf.engine.Comments;

public interface ProtectionSection {
	@Comments("Protect player from taking damage when AFK")
	default boolean damage() {
		return true;
	}

	@Comments("Protect player from hunger loss when AFK")
	default boolean hunger() {
		return true;
	}

	@Comments("Protect player from being pushed by entities, water, etc. when AFK")
	default boolean movement() {
		return true;
	}
}
