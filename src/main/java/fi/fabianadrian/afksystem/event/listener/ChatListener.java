package fi.fabianadrian.afksystem.event.listener;

import fi.fabianadrian.afksystem.AfkSystem;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public final class ChatListener implements Listener {
	private final AfkSystem plugin;

	public ChatListener(AfkSystem plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onChat(AsyncChatEvent event) {
		this.plugin.afkManager().markAsActive(event.getPlayer().getUniqueId());
	}
}
