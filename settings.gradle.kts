rootProject.name = "AFKSystem"

includeBuild("build-logic")
include("api")
include("plugin")

dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		mavenCentral()
		maven("https://repo.papermc.io/repository/maven-public/")
		maven("https://repo.faststats.dev/releases") // FastStats
		maven("https://repo.extendedclip.com/releases/") // PlaceholderAPI
		maven("https://eldonexus.de/repository/maven-public/") // StrokkCommands
	}
}