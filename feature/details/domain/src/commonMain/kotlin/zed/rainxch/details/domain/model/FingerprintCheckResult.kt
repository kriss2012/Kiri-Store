package zed.rainxch.details.domain.model

sealed interface FingerprintCheckResult {
    /** Fingerprint matches or no prior fingerprint is recorded. */
    data object Ok : FingerprintCheckResult

    /** Signing key has changed compared to the previously installed version. */
    data class Mismatch(
        val expectedFingerprint: String,
        val actualFingerprint: String,
    ) : FingerprintCheckResult
}
