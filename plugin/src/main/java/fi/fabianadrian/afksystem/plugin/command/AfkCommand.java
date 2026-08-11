package fi.fabianadrian.afksystem.plugin.command;

import fi.fabianadrian.afksystem.plugin.AFKSystem;
import fi.fabianadrian.afksystem.plugin.afk.AfkManager;
import fi.fabianadrian.afksystem.plugin.message.MessageHandler;
import net.strokkur.commands.Command;
import net.strokkur.commands.Executes;
import net.strokkur.commands.paper.Executor;
import net.strokkur.commands.permission.Permission;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command("afk")
@Permission("afksystem.command.afk")
public final class AfkCommand {
	private final AfkManager afkManager;
	private final MessageHandler messageHandler;

	public AfkCommand(AFKSystem plugin) {
		this.afkManager = plugin.afkManager();
		this.messageHandler = plugin.messageHandler();
	}

	@Executes
	void onAfk(CommandSender sender, @Executor Player player) {
		if (this.afkManager.afk(player)) {
			this.afkManager.markAsActiveCommand(player);
			this.messageHandler.sendNoLongerAfkNotification(player);
		} else {
			this.afkManager.markAsAfkCommand(player);
			this.messageHandler.sendAfkNotification(player);
		}
	}
}
