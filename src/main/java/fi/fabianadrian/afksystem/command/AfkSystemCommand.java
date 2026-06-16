package fi.fabianadrian.afksystem.command;

import fi.fabianadrian.afksystem.AFKSystem;
import net.kyori.adventure.text.Component;
import net.strokkur.commands.Command;
import net.strokkur.commands.Executes;
import net.strokkur.commands.permission.Permission;
import org.bukkit.command.CommandSender;

@Command("afksystem")
public final class AfkSystemCommand {
	private static final Component COMPONENT_RELOAD_SUCCESS = Component.textOfChildren(
			Component.text("[AFKSystem] "),
			Component.translatable("afksystem.command.reload.success")
	);
	private static final Component COMPONENT_RELOAD_FAILURE = Component.textOfChildren(
			Component.text("[AFKSystem] "),
			Component.translatable("afksystem.command.reload.failure")
	);
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
