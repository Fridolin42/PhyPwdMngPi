package de.fridolin1.io.data.entry

import de.fridolin1.io.serial.SerialListener
import de.fridolin1.listOfAllEntries

object ReadPassword : SerialListener {
    override val path = "/get/password"
    override val requireUserInteraction = true

    override fun receive(path: String, message: String, sender: (String) -> Unit) {
        val id = message.toLong()
        val entry = listOfAllEntries.firstOrNull { it.id == id }
        if (entry == null) sender.invoke("<error> could not fin entry with id $id")
        else sender.invoke(entry.password)
    }
}