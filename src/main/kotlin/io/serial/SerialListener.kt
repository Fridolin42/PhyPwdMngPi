package de.fridolin1.io.serial

interface SerialListener {
    val path: String
    fun receive(path: String, message: String, sender: (message: String) -> Unit)
}