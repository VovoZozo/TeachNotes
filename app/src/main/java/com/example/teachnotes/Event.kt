package com.example.teachnotes

class Event {
    private var eventHandled = false

    fun doIfNotHandled(action: () -> Unit) {
        if (!eventHandled) {
            action()
            eventHandled = true
        }
    }
}

class DataEvent<T>(private val content: T) {
    private var eventHandled = false

    fun getContentIfNotHandled(): T? {
        if (!eventHandled) {
            eventHandled = true
            return content
        } else {
            return null
        }
    }
}