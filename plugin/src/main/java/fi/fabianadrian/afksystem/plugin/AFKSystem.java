package fi.fabianadrian.afksystem.plugin;

import dev.faststats.ErrorTracker;
import dev.faststats.bukkit.BukkitContext;
import fi.fabianadrian.afksystem.plugin.afk.AfkManager;
import fi.fabianadrian.afksystem.plugin.api.AfkSystemAPI;
import fi.fabianadrian.afksystem.plugin.command.AfkCommandBrigadier;
import fi.fabianadrian.afksystem.plugin.command.AfkSystemCommandBrigadier;
import fi.fabianadrian.afksystem.plugin.config.AfkConfig;
import fi.fabianadrian.afksystem.plugin.config.ConfigManager;
import fi.fabianadrian.afksystem.plugin.event.EventListener;
import fi.fabianadrian.afksystem.plugin.message.MessageHandler;
import fi.fabianadrian.afksystem.plugin.message.TranslationManager;
import fi.fabianadrian.afksystem.plugin.placeholder.ExpansionManager;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;

import java.io.IOException;

public final class AFKSystem extends JavaPlugin implements AfkSystemAPI {
	public static final ErrorTracker ERROR_TRACKER = ErrorTracker.contextAware();
	private final BukkitContext context = new BukkitContext.Factory(this, "88be6b8b8fe5f8221046675e57d5fd54")
			.errorTrackerService(ERROR_TRACKER)
			.create();
	private final AfkManager afkManager;
	private final ConfigManager configManager;
	private final ExpansionManager expansionManager;
	private final TranslationManager translationManager;
	private final MessageHandler messageHandler = new MessageHandler(this);

	public AFKSystem() {
		this.afkManager = new AfkManager(this);
		this.configManager = new ConfigManager(this);
		this.expansionManager = new ExpansionManager(this);
		this.translationManager = new TranslationManager(this);
	}

	@Override
	public void onEnable() {
		this.context.ready();
		this.expansionManager.register();

		getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
			final Commands commands = event.registrar();
			commands.register(AfkSystemCommandBrigadier.create(this));
			commands.register(AfkCommandBrigadier.create(this));
		});

		getServer().getPluginManager().registerEvents(new EventListener(this), this);

		try {
			this.configManager.load();
		} catch (Throwable throwable) {
			getSLF4JLogger().error("Couldn't load configuration", throwable);
			ERROR_TRACKER.trackError(throwable);
			return;
		}

		this.translationManager.load();
		this.afkManager.load();
	}

	@Override
	public void onDisable() {
		this.context.shutdown();
	}

	@Override
	public @NonNull AfkManager afkManager() {
		return this.afkManager;
	}

	@Override
	public AfkConfig config() {
		return this.configManager.config();
	}

	public void load() throws IOException {
		this.configManager.load();
		this.translationManager.load();
		this.afkManager.load();
	}

	public MessageHandler messageHandler() {
		return this.messageHandler;
	}
}
