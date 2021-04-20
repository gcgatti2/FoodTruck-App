package com.example.redfinapp.util

import androidx.lifecycle.Observer

/**
 * An [Observer] for [Event]s, simplifying the pattern of checking if the [Event]'s content has
 * already been handled.
 *
 * [action] is *only* called if the [Event]'s contents has not been handled.
 *
 * https://gist.github.com/JoseAlcerreca/e0bba240d9b3cffa258777f12e5c0ae9
 */
class EventObserver<T>(private val action: (T) -> Unit) : Observer<LiveEvent<T>> {
    override fun onChanged(event: LiveEvent<T>?) {
        event?.consume()?.let { value ->
            action(value)
        }
    }
}

class PeekObserver<T>(private val action: (T) -> Unit) : Observer<LiveEvent<T>> {
    override fun onChanged(event: LiveEvent<T>?) {
        event?.peek()?.let { value ->
            action(value)
        }
    }
}