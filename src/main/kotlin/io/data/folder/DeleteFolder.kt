package de.fridolin1.io.data.folder

import de.fridolin1.getFolderFromPath
import de.fridolin1.io.data.entry.DeleteEntry
import de.fridolin1.io.serial.SerialListener

object DeleteFolder : SerialListener {
    override val path = "/delete/folder"

    override fun receive(message: String, sender: (String) -> Unit) {
        val path = message.substringAfter("/delete/folder ").substringBefore(" ")
        val motherPath = path.substringBeforeLast("/")
        val folder = getFolderFromPath(motherPath)
        if (folder == null) {
            sender.invoke("<error> cant find folder $path")
            return
        }
        folder.entries.forEach { DeleteEntry.deleteEntry(it, folder) }
        sender.invoke("<success>")
    }
}