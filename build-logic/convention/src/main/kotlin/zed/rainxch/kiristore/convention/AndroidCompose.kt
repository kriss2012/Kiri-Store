package zed.rainxch.kiristore.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.configureAndroidCompose(commonExtension: CommonExtension) {
    commonExtension.apply {
        (this as? com.android.build.api.dsl.ApplicationExtension)?.buildFeatures?.compose = true
        (this as? com.android.build.api.dsl.LibraryExtension)?.buildFeatures?.compose = true

        dependencies {
            val composeBom = libs.findLibrary("androidx-compose-bom").get()

            add("implementation", platform(composeBom))
            add("testImplementation", platform(composeBom))
            add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling-preview").get())
            add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())
        }
    }
}

