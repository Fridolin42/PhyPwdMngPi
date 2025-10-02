package de.fridolin1.io.data

import de.fridolin1.io.serial.SerialListener
import de.fridolin1.rootFolder

object ReadStructure : SerialListener {
    override val path = "/get/structure"
    override val requireUserInteraction = true

    override fun receive(path: String, message: String, sender: (String) -> Unit) {
        sender.invoke(rootFolder.toString())
    }
}