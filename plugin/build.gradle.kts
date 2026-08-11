import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml.Load

plugins {
	id("afksystem.java-conventions")
	alias(libs.plugins.resourcefactory.paper)
	alias(libs.plugins.shadow)
}

dependencies {
	implementation(project(":api"))
	compileOnly(libs.paper)
	compileOnly(libs.plugin.miniplaceholders)
	compileOnly(libs.plugin.placeholderapi)
	implementation(libs.dazzleconf)
	implementation(libs.faststats)
	compileOnly(libs.strokkcommands.annotations.paper)
	annotationProcessor(libs.strokkcommands.processor.paper)
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