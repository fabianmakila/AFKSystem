package fi.fabianadrian.afksystem.plugin.message;

import fi.fabianadrian.afksystem.plugin.AFKSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class MessageHandler {
	private static final TranslatableComponent COMPONENT_NOTIFICATION_AFK = Component.translatable("afksystem.notification.afk");
	private static final TranslatableComponent COMPONENT_NOTIFICATION_AFK_BROADCAST = Component.translatable("afksystem.notification.afk.broadcast");
	private static final TranslatableComponent COMPONENT_NOTIFICATION_NO_LONGER_AFK = Component.translatable("afksystem.notification.no-longer-afk");
	private static final TranslatableComponent COMPONENT_NOTIFICATION_NO_LONGER_AFK_BROADCAST = Component.translatable("afksystem.notification.no-longer-afk.broadcast");
	private static final TranslatableComponent COMPONENT_KICK = Component.translatable("afksystem.kick.reason");
	private static final String PERMISSION_BROADCAST = "afksystem.notification.broadcast";
	private static final String PERMISSION_NOTIFICATION = "afksystem.notification";
	private final AFKSystem plugin;

	public MessageHandler(AFKSystem plugin) {
		this.plugin = plugin;
	}

	public void sendAfkNotification(Player player) {
		player.sendMessage(COMPONENT_NOTIFICATION_AFK);

		if (this.plugin.config().notification().afkBroadcast()) {
			Bukkit.getOnlinePlayers().forEach(online -> {
				if (online.equals(player) || !online.hasPermission(PERMISSION_BROADCAST)) {
					return;
				}
				online.sendMessage(COMPONENT_NOTIFICATION_AFK_BROADCAST);
			});
		}
	}

	public void sendAfkNotificationPermission(Player player) {
		if (player.hasPermission(PERMISSION_NOTIFICATION) && this.plugin.config().notification().afk()) {
			player.sendMessage(COMPONENT_NOTIFICATION_AFK);
		}
		if (this.plugin.config().notification().afkBroadcast()) {
			Bukkit.getOnlinePlayers().forEach(online -> {
				if (online.equals(player) || !online.hasPermission(PERMISSION_BROADCAST)) {
					return;
				}
				online.sendMessage(COMPONENT_NOTIFICATION_AFK_BROADCAST);
			});
		}
	}

	public void sendNoLongerAfkNotification(Player player) {
		player.sendMessage(COMPONENT_NOTIFICATION_NO_LONGER_AFK);
		if (this.plugin.config().notification().noLongerAfkBroadcast()) {
			Bukkit.getOnlinePlayers().forEach(online -> {
				if (online.equals(player) || !online.hasPermission(PERMISSION_BROADCAST)) {
					return;
				}
				online.sendMessage(COMPONENT_NOTIFICATION_NO_LONGER_AFK_BROADCAST);
			});
		}
	}

	public void sendNoLongerAfkNotificationPermission(Player player) {
		if (player.hasPermission(PERMISSION_NOTIFICATION) && this.plugin.config().notification().noLongerAfk()) {
			player.sendMessage(COMPONENT_NOTIFICATION_NO_LONGER_AFK);
		}
		if (this.plugin.config().notification().noLongerAfkBroadcast()) {
			Bukkit.getOnlinePlayers().forEach(online -> {
				if (online.equals(player) || !online.hasPermission(PERMISSION_BROADCAST)) {
					return;
				}
				online.sendMessage(COMPONENT_NOTIFICATION_NO_LONGER_AFK_BROADCAST);
			});
		}
	}

	public Component kickMessage(Player player) {
		return GlobalTranslator.render(COMPONENT_KICK, player.locale());
	}
}
