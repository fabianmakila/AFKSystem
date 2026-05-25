package fi.fabianadrian.afksystem.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fi.fabianadrian.afksystem.AFKSystem;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.Plugin;

import static io.papermc.paper.command.brigadier.Commands.literal;

public final class AfkSystemCommand {
	private static final Component COMPONENT_PREFIX = MiniMessage.miniMessage().deserialize(
			"[AfkSystem] "
	);
	private static final Component COMPONENT_RELOAD_SUCCESS = COMPONENT_PREFIX.append(Component.translatable(
			"afksystem.command.reload.success", NamedTextColor.GREEN
	));
	private static final Component COMPONENT_RELOAD_FAILURE = COMPONENT_PREFIX.append(Component.translatable(
			"afksystem.command.reload.failure", NamedTextColor.RED
	));
	private static final String PERMISSION_RELOAD = "afksystem.command.reload";
	private final AFKSystem plugin;
	private final LifecycleEventManager<Plugin> manager;

	public AfkSystemCommand(AFKSystem plugin) {
		this.plugin = plugin;
		this.manager = plugin.getLifecycleManager();
	}

	public void register() {
		LiteralArgumentBuilder<CommandSourceStack> rootBuilder = literal("afksystem")
				.requires(stack -> stack.getSender().hasPermission(PERMISSION_RELOAD));

		LiteralCommandNode<CommandSourceStack> reloadNode = rootBuilder.then(literal("reload")
				.executes(this::executeReload)
		).build();

		this.manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
			final Commands commands = event.registrar();
			commands.register(reloadNode);
		});
	}

	private int executeReload(CommandContext<CommandSourceStack> ctx) {
		try {
			this.plugin.load();
			ctx.getSource().getSender().sendMessage(COMPONENT_RELOAD_SUCCESS);
		} catch (Throwable throwable) {
			AFKSystem.ERROR_TRACKER.trackError(throwable);
			this.plugin.getSLF4JLogger().error("Couldn't reload plugin", throwable);
			ctx.getSource().getSender().sendMessage(COMPONENT_RELOAD_FAILURE);
		}

		return Command.SINGLE_SUCCESS;
	}
}
