import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import zed.rainxch.kiristore.convention.libs
import zed.rainxch.kiristore.convention.isAndroidDisabled
import kotlin.text.get

class CmpLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                if (!isAndroidDisabled) {
                    apply("com.android.library")
                }
                apply("zed.rainxch.convention.kmp.library")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.compose")
            }

            if (!isAndroidDisabled) {
                configureAndroidTarget()
            }
            configureJvmTarget()

            dependencies {
                "commonMainImplementation"(libs.findLibrary("jetbrains-compose-ui").get())
                "commonMainImplementation"(libs.findLibrary("jetbrains-compose-foundation").get())
                "commonMainImplementation"(libs.findLibrary("jetbrains-compose-material3").get())
                "commonMainImplementation"(libs.findLibrary("jetbrains-compose-material-icons-extended").get())

                if (!isAndroidDisabled) {
                    "debugImplementation"(libs.findLibrary("androidx-compose-ui-tooling").get())
                }
            }
        }
    }
}

