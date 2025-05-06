package de.fridolin1.io.data

import de.fridolin1.io.serial.SerialListener

object Login : SerialListener {
    override val path = "/login"
    private val hash = "1xkOsZT/lJRiVRS20XjIf5nFlz4ow5iWnSIz8pYKVz4=" //123456

    override fun receive(message: String, sender: (String) -> Unit) {
        val hash = message.substringAfter("/login ")
        if (this.hash == hash) sender.invoke("<login> okay")
        else sender.invoke("<login> failed")
    }
}