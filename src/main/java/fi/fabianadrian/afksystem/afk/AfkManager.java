package fi.fabianadrian.afksystem.afk;

import fi.fabianadrian.afksystem.AFKSystem;
import fi.fabianadrian.afksystem.config.AfkConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.minimessage.translation.Argument;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerKickEvent;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public final class AfkManager {
	private static final TranslatableComponent COMPONENT_KICK = Component.translatable("afksystem.kick.reason");
	private static final TranslatableComponent COMPONENT_NOTIFICATION_AFK = Component.translatable("afksystem.notification.afk");
	private static final TranslatableComponent COMPONENT_NOTIFICATION_AFK_BROADCAST = Component.translatable("afksystem.notification.afk.broadcast");
	private static final TranslatableComponent COMPONENT_NOTIFICATION_NO_LONGER_AFK = Component.translatable("afksystem.notification.no-longer-afk");
	private static final TranslatableComponent COMPONENT_NOTIFICATION_NO_LONGER_AFK_BROADCAST = Component.translatable("afksystem.notification.no-longer-afk.broadcast");
	private final Map<Player, AfkStatus> afkStatusMap = new ConcurrentHashMap<>();
	private final AFKSystem plugin;
	private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
	private long afkMarkNanos;
	private long warnNanos;
	private long kickNanos;
	private TranslatableComponent warnComponent;
	private ScheduledFuture<?> scheduledFuture;
	private AfkConfig config;


	public AfkManager(AFKSystem plugin) {
		this.plugin = plugin;
	}

	public void load() {
		this.config = this.plugin.config();

		this.afkMarkNanos = TimeUnit.SECONDS.toNanos(this.config.afkMarkSeconds());
		this.warnNanos = TimeUnit.SECONDS.toNanos(this.config.afkKickSeconds() - this.config.afkWarnSeconds());
		this.kickNanos = TimeUnit.SECONDS.toNanos(this.config.afkKickSeconds());

		this.warnComponent = Component.translatable(
				"afksystem.kick.warning",
				Argument.numeric("seconds", this.config.afkWarnSeconds())
		);

		if (this.scheduledFuture == null) {
			this.scheduledFuture = this.executorService.scheduleAtFixedRate(() -> Bukkit.getScheduler().runTask(this.plugin, this::tick), 0, 1, TimeUnit.SECONDS);
		}
	}

	public void markAsActive(Player player) {
		this.afkStatusMap.compute(player, (_, status) -> {
			if (status == null) {
				return new AfkStatus();
			}
			if (status.afk()) {
				Bukkit.broadcast(COMPONENT_NOTIFICATION_NO_LONGER_AFK, "afksystem.notification.broadcast");
				if (player.hasPermission("afksystem.notification")) {
					player.sendMessage(COMPONENT_NOTIFICATION_NO_LONGER_AFK);
				}
			}
			status.markAsActive();
			return status;
		});
	}

	public void markAsAfk(Player player) {
		this.afkStatusMap.compute(player, (_, status) -> {
			if (status == null) {
				status = new AfkStatus();
			}
			status.markAsAfk();
			Bukkit.broadcast(COMPONENT_NOTIFICATION_AFK_BROADCAST, "afksystem.notification.broadcast");
			if (player.hasPermission("afksystem.notification")) {
				player.sendMessage(COMPONENT_NOTIFICATION_AFK);
			}
			return status;
		});
	}

	public boolean afk(Player player) {
		AfkStatus status = this.afkStatusMap.get(player);
		if (status == null) {
			return false;
		}

		return status.afk();
	}

	public void remove(Player player) {
		this.afkStatusMap.remove(player);
	}

	public List<Player> afkPlayerList() {
		return this.afkStatusMap.entrySet().stream().filter(entry -> entry.getValue().afk()).map(Map.Entry::getKey).toList();
	}

	private void tick() {
		this.afkStatusMap.forEach((player, status) -> {
			if (this.config.afkKickSeconds() >= 0 && !player.hasPermission("afksystem.kick.bypass")) {
				if (status.hasBeenAfkFor() >= this.kickNanos) {
					Component rendered = GlobalTranslator.render(COMPONENT_KICK, player.locale());
					player.kick(rendered, PlayerKickEvent.Cause.IDLING);
				}
				if (this.config.afkWarnSeconds() >= 0 && !status.warned() && status.hasBeenAfkFor() >= this.warnNanos) {
					status.markAsWarned();
					player.sendMessage(this.warnComponent);
				}
			}
			if (this.config.afkMarkSeconds() >= 0 && !afk(player) && status.hasBeenAfkFor() >= this.afkMarkNanos) {
				status.markAsAfk();
				Bukkit.broadcast(COMPONENT_NOTIFICATION_AFK_BROADCAST, "afksystem.notification.broadcast");
				if (player.hasPermission("afksystem.notification")) {
					player.sendMessage(COMPONENT_NOTIFICATION_AFK);
				}
			}
		});
	}
}
