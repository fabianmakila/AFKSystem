import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml.Load

plugins {
	id("java")
	id("xyz.jpenilla.resource-factory-paper-convention") version "1.3.1"
}

group = "fi.fabianadrian"
version = "1.0-SNAPSHOT"

repositories {
	mavenCentral()
	maven("https://repo.papermc.io/repository/maven-public/")
	maven("https://repo.extendedclip.com/releases/") // PlaceholderAPI
}

dependencies {
	compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
	compileOnly("io.github.miniplaceholders:miniplaceholders-api:3.1.0")
	compileOnly("me.clip:placeholderapi:2.12.2")
	implementation("space.arim.dazzleconf:dazzleconf-toml:2.0.0-M2")
}

java {
	toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

paperPluginYaml {
	main = "fi.fabianadrian.afksystem.AfkSystem"
	author = "FabianAdrian"
	apiVersion = "1.21.11"
	permissions {
		register("afksystem.command.reload")
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