package de.fridolin1.io.data

import de.fridolin1.io.serial.SerialListener

object Login : SerialListener {
    override val path = "/login"
    private val hash = "1xkOsZT/lJRiVRS20XjIf5nFlz4ow5iWnSIz8pYKVz4=" //123456

    override fun receive(path: String, message: String, sender: (String) -> Unit) {
        if (this.hash == message) sender.invoke("<login> okay")
        else sender.invoke("<login> failed")
    }
}