plugins {
	id("java")
	id("com.diffplug.spotless")
}

group = rootProject.group
description = rootProject.description
version = rootProject.version

java {
	toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

tasks {
	build {
		dependsOn(spotlessApply)
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