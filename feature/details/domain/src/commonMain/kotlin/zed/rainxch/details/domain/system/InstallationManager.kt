package zed.rainxch.details.domain.system

import zed.rainxch.core.domain.model.ApkPackageInfo
import zed.rainxch.core.domain.model.GithubRepoSummary
import zed.rainxch.core.domain.model.InstalledApp
import zed.rainxch.details.domain.model.ApkValidationResult
import zed.rainxch.details.domain.model.FingerprintCheckResult
import zed.rainxch.details.domain.model.SaveInstalledAppParams
import zed.rainxch.details.domain.model.UpdateInstalledAppParams

/**
 * Encapsulates APK validation, fingerprint checking, and
 * installed-app database persistence so the ViewModel stays thin.
 */
interface InstallationManager {
    /**
     * Extracts [ApkPackageInfo] from [filePath] and validates it.
     * On an update, verifies the package name matches [trackedPackageName].
     */
    suspend fun validateApk(
        filePath: String,
        isUpdate: Boolean,
        trackedPackageName: String?,
    ): ApkValidationResult

    /**
     * Checks whether the signing fingerprint of [apkInfo] matches
     * the fingerprint previously recorded for the same package.
     */
    suspend fun checkSigningFingerprint(apkInfo: ApkPackageInfo): FingerprintCheckResult

    /**
     * Saves a freshly installed app to the database and optionally
     * updates the favourite install status.
     *
     * @return the reloaded [InstalledApp], or `null` on failure.
     */
    suspend fun saveNewInstalledApp(params: SaveInstalledAppParams): InstalledApp?

    /**
     * Updates the version metadata of an already-tracked app.
     */
    suspend fun updateInstalledAppVersion(params: UpdateInstalledAppParams)
}
