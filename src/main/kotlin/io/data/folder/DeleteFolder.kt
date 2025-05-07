package de.fridolin1.io.data.folder

import de.fridolin1.getFolderFromPath
import de.fridolin1.io.data.entry.DeleteEntry
import de.fridolin1.io.serial.SerialListener

object DeleteFolder : SerialListener {
    override val path = "/delete/folder"

    override fun receive(path: String, message: String, sender: (String) -> Unit) {
        val folderPath = message
        val motherPath = folderPath.substringBeforeLast("/")
        val folder = getFolderFromPath(folderPath)
        val motherFolder = getFolderFromPath(motherPath)
        if (folder == null) {
            sender.invoke("<error> cant find folder $folderPath")
            return
        }
        if (motherFolder == null) {
            sender.invoke("<error> cant find folder $motherFolder")
            return
        }
        motherFolder.children.remove(folder)
        folder.entries.forEach { DeleteEntry.deleteEntry(it, motherFolder) }
        sender.invoke("<success>")
    }
}