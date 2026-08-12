plugins {
	`kotlin-dsl`
}

dependencies {
	implementation(plugin(libs.plugins.spotless))
}

fun plugin(plugin: Provider<PluginDependency>) =
	plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }