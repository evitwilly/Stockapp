package ru.freeit.stocker.core.connection

class InternetConnectionSubscribers {

    private var isConnected = true

    private val subscribers = mutableSetOf<ConnectionListener>()

    fun subscribe(subscriber: (Boolean) -> Unit) {
        subscribers.add(subscriber)
        subscriber.invoke(isConnected)
    }

    fun unsubscribe(subscriber: (Boolean) -> Unit) {
        subscribers.remove(subscriber)
    }

    fun notifySubscribers(isConnected: Boolean) {
        this.isConnected = isConnected
        subscribers.forEach { subscriber -> subscriber.invoke(isConnected) }
    }
}