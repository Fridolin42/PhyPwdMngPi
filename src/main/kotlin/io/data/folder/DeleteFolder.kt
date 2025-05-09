package de.fridolin1.io.data.folder

import data.SerializableFolder
import de.fridolin1.getFolderFromPath
import de.fridolin1.io.data.entry.DeleteEntry
import de.fridolin1.io.serial.SerialListener

object DeleteFolder : SerialListener {
    override val path = "/delete/folder"
    override val saveDataAfterCall = true

    override fun receive(path: String, message: String, sender: (String) -> Unit) {
        val folderPath = if (message.length > 1 && message.last() == '/') message.substringBeforeLast("/") else message
        val motherPath = folderPath.substringBeforeLast("/")
        println("Path: $message")
        println("motherPath: $motherPath")
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
        val successful = motherFolder.children.remove(folder)
        if (!successful) {
            sender.invoke("<error> internal error, folder is not child of her mother")
            return
        }
        deleteFolderEntries(folder)
//        folder.entries.forEach { DeleteEntry.deleteEntry(it, motherFolder) }
        sender.invoke("<success>")
    }

    fun deleteFolderEntries(folder: SerializableFolder) {
        while (folder.entries.isNotEmpty()) {
            DeleteEntry.deleteEntry(folder.entries.first(), folder)
        }
        while (folder.children.isNotEmpty()) {
            deleteFolderEntries(folder.children.first())
        }
    }
}