package fi.fabianadrian.afksystem.afk;

import fi.fabianadrian.afksystem.AfkSystem;
import fi.fabianadrian.afksystem.config.AfkConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.minimessage.translation.Argument;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerKickEvent;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

public final class AfkManager {
	private static final TranslatableComponent COMPONENT_KICK = Component.translatable("afksystem.kick");
	private static final TranslatableComponent COMPONENT_INFO = Component.translatable("afksystem.info");
	private final Map<UUID, AfkStatus> afkStatusMap = new ConcurrentHashMap<>();
	private final AfkSystem plugin;
	private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
	private long afkMarkNanos;
	private long kickNanos;
	private long warnNanos;
	private TranslatableComponent warnComponent;
	private ScheduledFuture<?> scheduledFuture;

	public AfkManager(AfkSystem plugin) {
		this.plugin = plugin;
	}

	public void load() {
		AfkConfig config = this.plugin.config();

		this.afkMarkNanos = TimeUnit.SECONDS.toNanos(this.plugin.config().afkMarkSeconds());
		this.kickNanos = TimeUnit.SECONDS.toNanos(this.plugin.config().afkKickSeconds());
		this.warnNanos = this.kickNanos - TimeUnit.SECONDS.toNanos(config.afkWarnBeforeKickSeconds());

		this.warnComponent = Component.translatable(
				"afksystem.warn",
				Argument.numeric("seconds", config.afkWarnBeforeKickSeconds())
		);

		if (this.scheduledFuture == null) {
			this.scheduledFuture = this.executorService.scheduleAtFixedRate(() -> Bukkit.getScheduler().runTask(this.plugin, this::tick), 0, 5, TimeUnit.SECONDS);
		}
	}

	public void markAsActive(UUID uuid) {
		this.afkStatusMap.compute(uuid, (uuid1, status) -> {
			if (status == null) {
				return new AfkStatus();
			}
			status.markAsActive();
			return status;
		});
	}

	public AfkState state(Player player) {
		AfkStatus status = this.afkStatusMap.get(player.getUniqueId());
		return status == null ? AfkState.NOT_AFK : status.state();
	}

	public boolean afk(Player player) {
		AfkStatus status = this.afkStatusMap.get(player.getUniqueId());
		if (status == null) {
			return false;
		}

		return status.state() != AfkState.NOT_AFK;
	}

	public void remove(UUID uuid) {
		this.afkStatusMap.remove(uuid);
	}

	private void tick() {
		this.afkStatusMap.entrySet().removeIf(entry -> {
			Player player = this.plugin.getServer().getPlayer(entry.getKey());
			if (player == null) {
				return true;
			}

			AfkStatus status = entry.getValue();

			switch (status.state()) {
				case NOT_AFK -> {
					if (status.hasBeenAfkFor() < this.afkMarkNanos) {
						return false;
					}
					if (player.hasPermission("afksystem.kick.bypass")) {
						status.state(AfkState.AFK_BYPASS);
					} else {
						status.state(AfkState.AFK);
					}
					player.sendMessage(COMPONENT_INFO);
				}
				case AFK -> {
					if (status.hasBeenAfkFor() < this.warnNanos) {
						return false;
					}
					status.state(AfkState.AFK_WARNED);
					player.sendMessage(this.warnComponent);
				}
				case AFK_WARNED -> {
					if (status.hasBeenAfkFor() < this.kickNanos) {
						return false;
					}
					Component rendered = GlobalTranslator.render(COMPONENT_KICK, player.locale());
					player.kick(rendered, PlayerKickEvent.Cause.IDLING);
				}
			}
			return false;
		});
	}
}
