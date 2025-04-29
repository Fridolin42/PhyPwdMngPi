package de.fridolin1.io.data.entry

import data.SerializableEntry
import data.SerializableFolder
import de.fridolin1.io.serial.SerialListener
import de.fridolin1.listOfAllEntries
import de.fridolin1.rootFolder

object DeleteEntry : SerialListener {
    override val path = "/delete/entry"

    override fun receive(message: String, sender: (String) -> Unit) {
        val id = message.substringAfter("/delete/entry ").toLong()
        val entry = listOfAllEntries.firstOrNull { it.id == id }
        if (entry == null) {
            sender.invoke("<error> Cant find entry with id $id")
            return
        }
        val folder = rootFolder.searchNodeWithEntry(entry)
        if (folder == null) {
            sender.invoke("<error> Internal Error: entry exist but not found in any folder")
            return
        }
        deleteEntry(entry, folder)
        sender.invoke("<success>")
    }

    fun deleteEntry(entry: SerializableEntry, folder: SerializableFolder) {
        folder.entries.remove(entry)
        listOfAllEntries.remove(entry)
    }
}