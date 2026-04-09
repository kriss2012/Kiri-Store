package zed.rainxch.kiristore.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

val Project.isAndroidDisabled: Boolean
    get() = findProperty("kiristore.disableAndroid")?.toString()?.toBoolean() ?: false

