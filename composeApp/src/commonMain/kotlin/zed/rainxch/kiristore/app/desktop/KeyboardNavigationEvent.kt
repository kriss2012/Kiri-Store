package zed.rainxch.kiristore.app.desktop

sealed interface KeyboardNavigationEvent {
    data object OnCtrlFClick : KeyboardNavigationEvent
}

