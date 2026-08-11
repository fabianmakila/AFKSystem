package fi.fabianadrian.afksystem.plugin.api;

import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.List;

public interface AfkManager {
	/**
	 * Checks whether the given player is currently marked as AFK.
	 *
	 * @param player the player to check
	 * @return {@code true} if the player is AFK, otherwise {@code false}
	 */
	boolean afk(Player player);

	/**
	 * Gets a list of all players who are currently marked as AFK.
	 *
	 * @return a non-null list containing all currently AFK players
	 */
	@NonNull List<@NonNull Player> afkPlayerList();
}
