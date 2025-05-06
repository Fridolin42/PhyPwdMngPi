package de.fridolin1.io.data.folder

import de.fridolin1.getFolderFromPath
import de.fridolin1.io.data.entry.DeleteEntry
import de.fridolin1.io.serial.SerialListener

object DeleteFolder : SerialListener {
    override val path = "/delete/folder"

    override fun receive(path: String, message: String, sender: (String) -> Unit) {
        val folderPath = message.substringBefore(" ")
        val motherPath = folderPath.substringBeforeLast("/")
        val folder = getFolderFromPath(motherPath)
        if (folder == null) {
            sender.invoke("<error> cant find folder $folderPath")
            return
        }
        folder.entries.forEach { DeleteEntry.deleteEntry(it, folder) }
        sender.invoke("<success>")
    }
}