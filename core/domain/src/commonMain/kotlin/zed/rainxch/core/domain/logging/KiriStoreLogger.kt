package zed.rainxch.core.domain.logging

/**
 * Logger interface for KiriStore.
 */
interface KiriStoreLogger {
    fun debug(message: String)

    fun info(message: String)

    fun warn(message: String)

    fun error(message: String, throwable: Throwable? = null)
}
