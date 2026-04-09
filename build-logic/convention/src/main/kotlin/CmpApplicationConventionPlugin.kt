import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import zed.rainxch.kiristore.convention.isAndroidDisabled

class CmpApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                if (!isAndroidDisabled) {
                    apply("zed.rainxch.convention.android.application.compose")
                }
                apply("org.jetbrains.kotlin.multiplatform")
                apply("org.jetbrains.compose")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            if (!isAndroidDisabled) {
                configureAndroidTarget()
            }
            configureJvmTarget()

            dependencies {
                if (!isAndroidDisabled) {
                    "debugImplementation"(libs.findLibrary("androidx-compose-ui-tooling").get())
                }
            }
        }
    }
}

