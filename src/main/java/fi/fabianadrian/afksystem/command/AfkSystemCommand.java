package fi.fabianadrian.afksystem.command;

import fi.fabianadrian.afksystem.AFKSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.strokkur.commands.Command;
import net.strokkur.commands.Executes;
import net.strokkur.commands.permission.Permission;
import org.bukkit.command.CommandSender;

@Command("afksystem")
public final class AfkSystemCommand {
	private static final Component COMPONENT_PREFIX = MiniMessage.miniMessage().deserialize(
			"[AfkSystem] "
	);
	private static final Component COMPONENT_RELOAD_SUCCESS = COMPONENT_PREFIX.append(Component.translatable(
			"afksystem.command.reload.success", NamedTextColor.GREEN
	));
	private static final Component COMPONENT_RELOAD_FAILURE = COMPONENT_PREFIX.append(Component.translatable(
			"afksystem.command.reload.failure", NamedTextColor.RED
	));
	private final AFKSystem plugin;

	public AfkSystemCommand(AFKSystem plugin) {
		this.plugin = plugin;
	}

	@Executes("reload")
	@Permission("afksystem.command.afksystem.reload")
	void onReload(CommandSender sender) {
		try {
			this.plugin.load();
			sender.sendMessage(COMPONENT_RELOAD_SUCCESS);
		} catch (Throwable throwable) {
			sender.sendMessage(COMPONENT_RELOAD_FAILURE);
			this.plugin.getSLF4JLogger().error("Couldn't reload plugin", throwable);
			AFKSystem.ERROR_TRACKER.trackError(throwable);
		}
	}
}
