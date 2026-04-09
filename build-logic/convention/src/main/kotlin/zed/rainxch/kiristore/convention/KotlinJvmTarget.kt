package zed.rainxch.kiristore.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getting
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import java.util.Properties

fun Project.configureJvmTarget() {
    extensions.configure<KotlinMultiplatformExtension> {
        jvm()
    }
}

