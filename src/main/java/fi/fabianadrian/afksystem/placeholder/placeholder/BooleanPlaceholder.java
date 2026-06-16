package fi.fabianadrian.afksystem.placeholder.placeholder;

import fi.fabianadrian.afksystem.AFKSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.minimessage.tag.Tag;
import org.bukkit.entity.Player;

public final class BooleanPlaceholder extends Placeholder {
	private static final TranslatableComponent COMPONENT_BOOLEAN_TRUE = Component.translatable("afksystem.placeholder.boolean.true");
	private static final TranslatableComponent COMPONENT_BOOLEAN_FALSE = Component.translatable("afksystem.placeholder.boolean.false");
	private static final Tag TAG_YES = Tag.preProcessParsed("yes");
	private static final Tag TAG_NO = Tag.preProcessParsed("no");

	public BooleanPlaceholder(AFKSystem plugin) {
		super(plugin);
	}

	public Tag tag(Player player) {
		boolean afk = super.afkManager.afk(player);
		return afk ? Tag.selfClosingInserting(COMPONENT_BOOLEAN_TRUE) : Tag.selfClosingInserting(COMPONENT_BOOLEAN_FALSE);
	}

	public Tag tag(Player player, BooleanFormat format) {
		boolean afk = super.afkManager.afk(player);
		switch (format) {
			case YESNO -> {
				return afk ? TAG_YES : TAG_NO;
			}
			case TRUEFALSE -> {
				return Tag.preProcessParsed(Boolean.toString(afk));
			}
			default -> throw new IllegalStateException("Unknown boolean format");
		}
	}

	public String string(Player player, String format) {
		boolean afk = super.afkManager.afk(player);
		switch (format) {
			case "yesno" -> {
				return afk ? "yes" : "no";
			}
			case "truefalse" -> {
				return Boolean.toString(afk);
			}
		}
		Component component = afk ? COMPONENT_BOOLEAN_TRUE : COMPONENT_BOOLEAN_FALSE;
		return serializeComponent(component, player, format);
	}
}
