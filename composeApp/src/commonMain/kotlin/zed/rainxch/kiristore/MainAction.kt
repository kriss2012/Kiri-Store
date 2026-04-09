package zed.rainxch.kiristore

sealed interface MainAction {
    data object DismissRateLimitDialog : MainAction

    data object DismissSessionExpiredDialog : MainAction
}

