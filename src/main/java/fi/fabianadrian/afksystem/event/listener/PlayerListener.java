package fi.fabianadrian.afksystem.event.listener;

import fi.fabianadrian.afksystem.AfkSystem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInputEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class PlayerListener implements Listener {
	private final AfkSystem plugin;

	public PlayerListener(AfkSystem plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onInput(PlayerInputEvent event) {
		this.plugin.afkManager().markAsActive(event.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		this.plugin.afkManager().remove(event.getPlayer().getUniqueId());
	}
}
