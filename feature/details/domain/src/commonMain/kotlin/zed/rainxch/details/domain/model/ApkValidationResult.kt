package zed.rainxch.details.domain.model

import zed.rainxch.core.domain.model.ApkPackageInfo

sealed interface ApkValidationResult {
    /** APK is valid and ready to install. */
    data class Valid(
        val apkInfo: ApkPackageInfo,
    ) : ApkValidationResult

    /** Could not extract package information from the APK. */
    data object ExtractionFailed : ApkValidationResult

    /** Package name in the APK does not match the currently installed app. */
    data class PackageMismatch(
        val apkPackageName: String,
        val installedPackageName: String,
    ) : ApkValidationResult
}
