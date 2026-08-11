package fi.fabianadrian.afksystem.plugin.api;

import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.List;

public interface AfkManager {
	boolean afk(Player player);

	@NonNull
	List<@NonNull Player> afkPlayerList();
}
