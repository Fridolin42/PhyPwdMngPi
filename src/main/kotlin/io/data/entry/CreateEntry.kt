package de.fridolin1.io.data.entry

import data.SerializableEntry
import de.fridolin1.getFolderFromPath
import de.fridolin1.io.serial.SerialListener
import kotlinx.serialization.json.Json

object CreateEntry : SerialListener {
    override val path = "/create/entry"

    override fun receive(path: String, message: String, sender: (String) -> Unit) {
        val folderPath = message.substringBefore(" ")
        val dataRaw = message.substringAfter(" ")
        val folder = getFolderFromPath(folderPath)
        if (folder == null) {
            sender.invoke("<error> Cant find folder with path $folderPath")
            return
        }
        val entry = Json.decodeFromString<SerializableEntry>(dataRaw)
        folder.entries.add(entry)
        sender.invoke("<success>")
    }
}