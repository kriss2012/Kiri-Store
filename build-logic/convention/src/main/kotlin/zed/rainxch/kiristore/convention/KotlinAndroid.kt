package zed.rainxch.kiristore.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun Project.configureKotlinAndroid(commonExtension: CommonExtension) {
    with(commonExtension) {
        (this as? com.android.build.api.dsl.ApplicationExtension)?.compileSdk =
            libs
                .findVersion("projectCompileSdkVersion")
                .get()
                .toString()
                .toInt()

        (this as? com.android.build.api.dsl.LibraryExtension)?.compileSdk =
            libs
                .findVersion("projectCompileSdkVersion")
                .get()
                .toString()
                .toInt()

        (this as? com.android.build.api.dsl.ApplicationExtension)?.defaultConfig?.minSdk =
            libs
                .findVersion("projectMinSdkVersion")
                .get()
                .toString()
                .toInt()

        (this as? com.android.build.api.dsl.LibraryExtension)?.defaultConfig?.minSdk =
            libs
                .findVersion("projectMinSdkVersion")
                .get()
                .toString()
                .toInt()

        compileOptions.run {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
            isCoreLibraryDesugaringEnabled = true
        }

        configureKotlin()

        dependencies {
            add("coreLibraryDesugaring", libs.findLibrary("android-desugarJdkLibs").get())
        }
    }
}

fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}

