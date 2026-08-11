package fi.fabianadrian.afksystem.plugin.config.section;

import space.arim.dazzleconf.engine.Comments;

public interface ProtectionSection extends fi.fabianadrian.afksystem.plugin.api.config.section.ProtectionSection {
	@Comments("Protect player from taking damage when AFK")
	@Override
	default boolean damage() {
		return true;
	}

	@Comments("Protect player from hunger loss when AFK")
	@Override
	default boolean hunger() {
		return true;
	}

	@Comments("Protect player from being pushed by entities, water, etc. when AFK")
	@Override
	default boolean movement() {
		return true;
	}
}
