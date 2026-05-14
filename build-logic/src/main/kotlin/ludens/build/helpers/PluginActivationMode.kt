package ludens.build.helpers

import org.gradle.api.Project

/**
 * Internal switch that controls when a build-logic plugin should be activated.
 *
 * `All` applies the plugin for every build. `ReleaseOnly` applies it only when the requested
 * Gradle tasks include a release build name.
 */
internal enum class PluginActivationMode {
    All,
    ReleaseOnly,
}

/**
 * Returns `true` when the current Gradle invocation matches the selected activation mode.
 */
internal fun Project.isPluginEnabled(mode: PluginActivationMode): Boolean {
    return when (mode) {
        PluginActivationMode.All -> true
        PluginActivationMode.ReleaseOnly -> gradle.startParameter.taskNames.any {
            it.contains("release", ignoreCase = true)
        }
    }
}
