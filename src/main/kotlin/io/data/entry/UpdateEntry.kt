package de.fridolin1.io.data.entry

import data.SerializableEntry
import de.fridolin1.io.serial.SerialListener
import de.fridolin1.listOfAllEntries
import kotlinx.serialization.json.Json

object UpdateEntry : SerialListener {
    override val path = "/update/entry"
    override val saveDataAfterCall = true

    override fun receive(path: String, message: String, sender: (String) -> Unit) {
        val updatedEntry = Json.decodeFromString<SerializableEntry>(message)
        val id = updatedEntry.id
        val entry = listOfAllEntries.firstOrNull { it.id == id }
        if (entry == null) {
            sender.invoke("<error> could not find entry with id $id")
            return
        }
        entry.update(updatedEntry)
        sender.invoke("<success>")
    }
}