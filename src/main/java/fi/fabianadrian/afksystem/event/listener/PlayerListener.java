package fi.fabianadrian.afksystem.event.listener;

import fi.fabianadrian.afksystem.AfkSystem;
import fi.fabianadrian.afksystem.event.EventType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInputEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class PlayerListener implements Listener {
	private final AfkSystem plugin;

	public PlayerListener(AfkSystem plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onInput(PlayerInputEvent event) {
		if (!this.plugin.config().events().contains(EventType.INPUT)) {
			return;
		}
		this.plugin.afkManager().markAsActive(event.getPlayer());
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		this.plugin.afkManager().markAsActive(event.getPlayer());
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		this.plugin.afkManager().remove(event.getPlayer());
	}
}
