package zed.rainxch.kiristore.app.desktop

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object KeyboardNavigation {
    private val _events = Channel<KeyboardNavigationEvent>()
    val events = _events.receiveAsFlow()

    fun onKeyClicked(event: KeyboardNavigationEvent) {
        _events.trySend(event)
    }
}
