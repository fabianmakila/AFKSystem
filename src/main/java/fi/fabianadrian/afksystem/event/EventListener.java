package fi.fabianadrian.afksystem.event;

import fi.fabianadrian.afksystem.AFKSystem;
import fi.fabianadrian.afksystem.afk.AfkManager;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;

public final class EventListener implements Listener {
	private final AfkManager afkManager;
	private final AFKSystem plugin;

	public EventListener(AFKSystem plugin) {
		this.plugin = plugin;
		this.afkManager = plugin.afkManager();
	}

	@EventHandler
	public void onInput(PlayerInputEvent event) {
		if (!this.plugin.config().events().contains(EventType.INPUT)) {
			return;
		}
		this.afkManager.markAsActive(event.getPlayer());
	}

	@EventHandler
	public void onChat(AsyncChatEvent event) {
		if (!this.plugin.config().events().contains(EventType.CHAT)) {
			return;
		}
		this.afkManager.markAsActive(event.getPlayer());
	}

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {
		if (!this.plugin.config().events().contains(EventType.COMMAND)) {
			return;
		}
		this.afkManager.markAsActive(event.getPlayer());
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (!this.plugin.config().events().contains(EventType.BLOCK_BREAK)) {
			return;
		}
		this.afkManager.markAsActive(event.getPlayer());
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (!this.plugin.config().events().contains(EventType.INTERACT)) {
			return;
		}
		this.afkManager.markAsActive(event.getPlayer());
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		this.afkManager.markAsActive(event.getPlayer());
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		this.afkManager.remove(event.getPlayer());
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if (this.afkManager.afk(event.getPlayer()) && this.plugin.config().protection().movement()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player player && this.afkManager.afk(player) && this.plugin.config().protection().damage()) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		if (event.getEntity() instanceof Player player && this.afkManager.afk(player) && this.plugin.config().protection().hunger()) {
			event.setCancelled(true);
		}
	}
}
