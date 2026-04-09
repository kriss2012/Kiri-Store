package zed.rainxch.core.domain.repository

import kotlinx.coroutines.flow.Flow
import zed.rainxch.core.domain.model.AppTheme
import zed.rainxch.core.domain.model.DiscoveryPlatform
import zed.rainxch.core.domain.model.FontTheme
import zed.rainxch.core.domain.model.InstallerType

interface TweaksRepository {
    fun getThemeColor(): Flow<AppTheme>

    suspend fun setThemeColor(theme: AppTheme)

    fun getIsDarkTheme(): Flow<Boolean?>

    suspend fun setDarkTheme(isDarkTheme: Boolean?)

    fun getAmoledTheme(): Flow<Boolean>

    suspend fun setAmoledTheme(enabled: Boolean)

    fun getFontTheme(): Flow<FontTheme>

    suspend fun setFontTheme(fontTheme: FontTheme)

    fun getAutoDetectClipboardLinks(): Flow<Boolean>

    suspend fun setAutoDetectClipboardLinks(enabled: Boolean)

    fun getInstallerType(): Flow<InstallerType>

    suspend fun setInstallerType(type: InstallerType)

    fun getAutoUpdateEnabled(): Flow<Boolean>

    suspend fun setAutoUpdateEnabled(enabled: Boolean)

    fun getUpdateCheckInterval(): Flow<Long>

    suspend fun setUpdateCheckInterval(hours: Long)

    fun getIncludePreReleases(): Flow<Boolean>

    suspend fun setIncludePreReleases(enabled: Boolean)

    fun getLiquidGlassEnabled(): Flow<Boolean>

    suspend fun setLiquidGlassEnabled(enabled: Boolean)

    fun getHideSeenEnabled(): Flow<Boolean>

    suspend fun setHideSeenEnabled(enabled: Boolean)

    fun getDiscoveryPlatform(): Flow<DiscoveryPlatform>

    suspend fun setDiscoveryPlatform(platform: DiscoveryPlatform)

    fun getScrollbarEnabled(): Flow<Boolean>

    suspend fun setScrollbarEnabled(enabled: Boolean)
}
