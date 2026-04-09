package zed.rainxch.core.data.services

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.GET_SIGNING_CERTIFICATES
import android.os.Build
import co.touchlab.kermit.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import zed.rainxch.core.domain.model.ApkPackageInfo
import zed.rainxch.core.domain.system.InstallerInfoExtractor
import java.io.File
import java.security.MessageDigest

class AndroidInstallerInfoExtractor(
    private val context: Context,
) : InstallerInfoExtractor {
    override suspend fun extractPackageInfo(filePath: String): ApkPackageInfo? =
        withContext(Dispatchers.IO) {
            try {
                val packageManager = context.packageManager
                val flags =
                    PackageManager.GET_META_DATA or
                        PackageManager.GET_ACTIVITIES or
                        GET_SIGNING_CERTIFICATES

                val packageInfo =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        packageManager.getPackageArchiveInfo(
                            filePath,
                            PackageManager.PackageInfoFlags.of(flags.toLong()),
                        )
                    } else {
                        @Suppress("DEPRECATION")
                        packageManager.getPackageArchiveInfo(filePath, flags)
                    }

                if (packageInfo == null) {
                    Logger.e {
                        "Failed to parse APK at $filePath, file exists: ${
                            File(
                                filePath,
                            ).exists()
                        }, size: ${File(filePath).length()}"
                    }
                    return@withContext null
                }

                val appInfo = packageInfo.applicationInfo
                appInfo?.sourceDir = filePath
                appInfo?.publicSourceDir = filePath

                val appName = appInfo?.let { packageManager.getApplicationLabel(it) }.toString()
                val versionCode =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        packageInfo.longVersionCode
                    } else {
                        @Suppress("DEPRECATION")
                        packageInfo.versionCode.toLong()
                    }
                val fingerprint: String? =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        val sigInfo = packageInfo.signingInfo
                        val certs =
                            if (sigInfo?.hasMultipleSigners() == true) {
                                sigInfo.apkContentsSigners
                            } else {
                                sigInfo?.signingCertificateHistory
                            }
                        certs?.firstOrNull()?.toByteArray()?.let { certBytes ->
                            MessageDigest
                                .getInstance("SHA-256")
                                .digest(certBytes)
                                .joinToString(":") { "%02X".format(it) }
                        }
                    } else {
                        @Suppress("DEPRECATION")
                        val legacyInfo =
                            packageManager.getPackageArchiveInfo(
                                filePath,
                                PackageManager.GET_SIGNATURES,
                            )
                        @Suppress("DEPRECATION")
                        legacyInfo?.signatures?.firstOrNull()?.toByteArray()?.let { certBytes ->
                            MessageDigest
                                .getInstance("SHA-256")
                                .digest(certBytes)
                                .joinToString(":") { "%02X".format(it) }
                        }
                    }

                ApkPackageInfo(
                    appName = appName,
                    packageName = packageInfo.packageName,
                    versionName = packageInfo.versionName ?: "unknown",
                    versionCode = versionCode,
                    signingFingerprint = fingerprint,
                )
            } catch (e: Exception) {
                Logger.e { "Failed to extract APK info: ${e.message}, file: $filePath" }
                null
            }
        }
}
