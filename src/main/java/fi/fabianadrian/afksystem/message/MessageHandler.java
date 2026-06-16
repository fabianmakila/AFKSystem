package fi.fabianadrian.afksystem.message;

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

	public void sendAfkNotification(Player player) {
		player.sendMessage(COMPONENT_NOTIFICATION_AFK);
		Bukkit.broadcast(COMPONENT_NOTIFICATION_AFK_BROADCAST, "afksystem.notification.broadcast");
	}

	public void sendAfkNotificationPermission(Player player) {
		if (player.hasPermission("afksystem.notificaiton")) {
			player.sendMessage(COMPONENT_NOTIFICATION_AFK);
		}
		Bukkit.broadcast(COMPONENT_NOTIFICATION_AFK_BROADCAST, "afksystem.notification.broadcast");
	}

	public void sendNoLongerAfkNotification(Player player) {
		player.sendMessage(COMPONENT_NOTIFICATION_NO_LONGER_AFK);
		Bukkit.broadcast(COMPONENT_NOTIFICATION_NO_LONGER_AFK_BROADCAST, "afksystem.notification.broadcast");
	}

	public void sendNoLongerAfkNotificationPermission(Player player) {
		if (player.hasPermission("afksystem.notificaiton")) {
			player.sendMessage(COMPONENT_NOTIFICATION_NO_LONGER_AFK);
		}
		Bukkit.broadcast(COMPONENT_NOTIFICATION_NO_LONGER_AFK_BROADCAST, "afksystem.notification.broadcast");
	}

	public Component kickMessage(Player player) {
		return GlobalTranslator.render(COMPONENT_KICK, player.locale());
	}
}
