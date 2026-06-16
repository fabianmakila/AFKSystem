import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml.Load

plugins {
	id("java")
	id("xyz.jpenilla.resource-factory-paper-convention") version "1.3.1"
	id("com.gradleup.shadow") version "9.4.1"
	id("com.diffplug.spotless") version "8.5.1"
}

group = "fi.fabianadrian"
version = "1.1.0"

repositories {
	mavenCentral()
	maven("https://repo.papermc.io/repository/maven-public/")
	maven("https://repo.faststats.dev/releases") // FastStats
	maven("https://repo.extendedclip.com/releases/") // PlaceholderAPI
	maven("https://eldonexus.de/repository/maven-public/") // StrokkCommands
}

dependencies {
	compileOnly("io.papermc.paper:paper-api:26.1.2.build.+")
	compileOnly("io.github.miniplaceholders:miniplaceholders-api:3.2.0")
	compileOnly("me.clip:placeholderapi:2.12.2")
	implementation("space.arim.dazzleconf:dazzleconf-toml:2.0.0-M2")
	implementation("dev.faststats.metrics:bukkit:0.26.1")
	compileOnly("net.strokkur.commands:annotations-paper:2.1.1")
	annotationProcessor("net.strokkur.commands:processor-paper:2.1.1")
}

java {
	toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

paperPluginYaml {
	main = "fi.fabianadrian.afksystem.AFKSystem"
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
		dependsOn(shadowJar, spotlessApply)
	}
	compileJava {
		options.encoding = Charsets.UTF_8.name()
	}
}

spotless {
	java {
		endWithNewline()
		formatAnnotations()
		leadingSpacesToTabs()
		removeUnusedImports()
		trimTrailingWhitespace()
	}
}