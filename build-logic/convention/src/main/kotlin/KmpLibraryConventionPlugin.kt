import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import zed.rainxch.kiristore.convention.configureKotlinAndroid
import zed.rainxch.kiristore.convention.configureKotlinMultiplatform
import zed.rainxch.kiristore.convention.isAndroidDisabled
import zed.rainxch.kiristore.convention.libs
import zed.rainxch.kiristore.convention.pathToResourcePrefix

class KmpLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                if (!isAndroidDisabled) {
                    apply("com.android.library")
                }
                apply("org.jetbrains.kotlin.multiplatform")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            configureKotlinMultiplatform()

            if (!isAndroidDisabled) {
                extensions.configure<LibraryExtension> {
                    configureKotlinAndroid(this)

                    resourcePrefix = this@with.pathToResourcePrefix()

                    experimentalProperties["android.experimental.kmp.enableAndroidResources"] = "true"
                }
            }

            dependencies {
                "commonMainImplementation"(libs.findLibrary("kotlinx-serialization-json").get())
            }
        }
    }
}

