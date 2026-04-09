package zed.rainxch.auth.presentation.model

data class GithubDeviceStartUi(
    val deviceCode: String,
    val userCode: String,
    val verificationUri: String,
    val verificationUriComplete: String? = null,
    val intervalSec: Int = 5,
    val expiresInSec: Int,
)
