package de.fridolin1.io.data

import de.fridolin1.io.serial.SerialListener
import de.fridolin1.rootFolder

object ReadStructure : SerialListener {
    override val path = "/get/structure"

    override fun receive(message: String, sender: (String) -> Unit) {
        sender.invoke(rootFolder.toString())
    }
}