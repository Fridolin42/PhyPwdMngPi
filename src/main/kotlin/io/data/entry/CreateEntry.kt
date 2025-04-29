package de.fridolin1.io.data.entry

import data.SerializableEntry
import de.fridolin1.getFolderFromPath
import de.fridolin1.io.serial.SerialListener
import kotlinx.serialization.json.Json

object CreateEntry : SerialListener {
    override val path = "/create/entry"

    override fun receive(message: String, sender: (String) -> Unit) {
        val path = message.substringAfter("/create/entry ").substringBefore(" ")
        val dataRaw = message.substringAfter("/create/entry ").substringAfter(" ")
        val folder = getFolderFromPath(path)
        if (folder == null) {
            sender.invoke("<error> Cant find folder with path $path")
            return
        }
        val entry = Json.decodeFromString<SerializableEntry>(dataRaw)
        folder.entries.add(entry)
        sender.invoke("<success>")
    }
}