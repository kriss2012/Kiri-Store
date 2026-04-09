import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import zed.rainxch.kiristore.convention.configureKotlinAndroid
import zed.rainxch.kiristore.convention.libs

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
            }

            val signingPropsFile = rootProject.file("local.properties")
            val signingProps = java.util.Properties()
            if (signingPropsFile.exists()) {
                signingPropsFile.inputStream().use { signingProps.load(it) }
            }

            extensions.configure<ApplicationExtension> {
                namespace = "zed.rainxch.kiristore"
                compileSdk =
                    libs
                        .findVersion("projectCompileSdkVersion")
                        .get()
                        .toString()
                        .toInt()

                defaultConfig {
                    applicationId = libs.findVersion("projectApplicationId").get().toString()
                    minSdk =
                        libs
                            .findVersion("projectMinSdkVersion")
                            .get()
                            .toString()
                            .toInt()
                    targetSdk =
                        libs
                            .findVersion("projectTargetSdkVersion")
                            .get()
                            .toString()
                            .toInt()
                    versionCode =
                        libs
                            .findVersion("projectVersionCode")
                            .get()
                            .toString()
                            .toInt()
                    versionName = libs.findVersion("projectVersionName").get().toString()
                }
                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
                signingConfigs {
                    create("release") {
                        storeFile = signingProps.getProperty("signing.keyStorePath")?.let { file(it) }
                        storePassword = signingProps.getProperty("signing.keyStorePassword")
                        keyAlias = signingProps.getProperty("signing.keyAlias")
                        keyPassword = signingProps.getProperty("signing.keyPassword")
                    }
                }

                buildTypes {
                    getByName("release") {
                        isMinifyEnabled = true
                        isShrinkResources = true

                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro",
                        )

                        if (signingConfigs.getByName("release").storeFile != null) {
                            signingConfig = signingConfigs.getByName("release")
                        }
                    }
                }

                configureKotlinAndroid(this)
            }
        }
    }
}

