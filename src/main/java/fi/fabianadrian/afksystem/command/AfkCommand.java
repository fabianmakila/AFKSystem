package fi.fabianadrian.afksystem.command;

import fi.fabianadrian.afksystem.AFKSystem;
import fi.fabianadrian.afksystem.afk.AfkManager;
import net.strokkur.commands.Command;
import net.strokkur.commands.Executes;
import net.strokkur.commands.paper.Executor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command("afk")
public final class AfkCommand {
	private final AfkManager afkManager;

	public AfkCommand(AFKSystem plugin) {
		this.afkManager = plugin.afkManager();
	}

	@Executes
	void onAfk(CommandSender sender, @Executor Player player) {
		if (this.afkManager.afk(player)) {
			this.afkManager.markAsActive(player);
		} else {
			this.afkManager.markAsAfk(player);
		}
	}
}
