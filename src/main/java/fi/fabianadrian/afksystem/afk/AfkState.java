package fi.fabianadrian.afksystem.afk;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;

import java.util.Locale;

public enum AfkState {
	NOT_AFK, AFK, AFK_BYPASS, AFK_WARNED;

	private final TranslatableComponent translatable;

	AfkState() {
		this.translatable = Component.translatable("afksystem.state." + name().toLowerCase(Locale.ROOT));
	}

	public TranslatableComponent translatable() {
		return this.translatable;
	}
}
