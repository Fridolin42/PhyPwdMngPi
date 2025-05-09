package de.fridolin1.io.serial

interface SerialListener {
    val path: String
    val rawBody: Boolean
        get() = false
    val saveDataAfterCall: Boolean
        get() = false

    fun receive(path: String, message: String, sender: (message: String) -> Unit)
}