import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml.Load

plugins {
	id("afksystem.java-conventions")
	id("xyz.jpenilla.resource-factory-paper-convention") version "1.3.1"
	id("com.gradleup.shadow") version "9.6.1"
}

dependencies {
	compileOnly("io.papermc.paper:paper-api:26.2.build.+")
	compileOnly("io.github.miniplaceholders:miniplaceholders-api:3.2.0")
	compileOnly("me.clip:placeholderapi:2.12.3")
	implementation("space.arim.dazzleconf:dazzleconf-toml:2.0.0-M2")
	implementation("dev.faststats.metrics:bukkit:0.29.4")
	compileOnly("net.strokkur.commands:annotations-paper:2.1.4")
	annotationProcessor("net.strokkur.commands:processor-paper:2.1.4")
}

paperPluginYaml {
	main = "fi.fabianadrian.afksystem.plugin.AFKSystem"
	website = "https://modrinth.com/project/afksystem"
	author = "FabianAdrian"
	apiVersion = "1.21.11"
	permissions {
		register("afksystem.command.afk")
		register("afksystem.command.afksystem.reload")
		register("afksystem.kick.bypass")
		register("afksystem.notification")
		register("afksystem.notification.broadcast")
	}
	dependencies {
		server {
			register("MiniPlaceholders") {
				load = Load.BEFORE
				required = false
			}
			register("PlaceholderAPI") {
				load = Load.BEFORE
				required = false
			}
		}
	}
}

tasks {
	build {
		dependsOn(shadowJar)
	}
}