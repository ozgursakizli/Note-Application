package com.ozgursakizli.noteapplication.utils

import androidx.lifecycle.Observer

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    /** Returns the content and prevents its use again. */
    fun getContentIfNotHandled(): T? = if (hasBeenHandled) null else {
        hasBeenHandled = true
        content
    }

    /** Returns the content, even if it's already been handled. */
    fun peekContent(): T = content

}

/**
 * An [Observer] for [Event]s, simplifying the pattern of checking if the [Event]'s content has
 * already been handled.
 *
 * [onEventUnhandledContent] is *only* called if the [Event]'s contents has not been handled.
 */
class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let(onEventUnhandledContent)
    }
}

sealed class EventType {
    object ShowProgress : EventType()
    object HideProgress : EventType()
    class ShowToast(val source: Int) : EventType()
    class ShowError(val source: Int) : EventType()
}

sealed class NoteEvents : EventType() {
    object NoteSaved : NoteEvents()
    object NoteDeleted : NoteEvents()
}