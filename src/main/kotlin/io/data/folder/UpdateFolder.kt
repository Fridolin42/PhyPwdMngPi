package de.fridolin1.io.data.folder

import de.fridolin1.getFolderFromPath
import de.fridolin1.io.serial.SerialListener

object UpdateFolder : SerialListener {
    override val path = "/update/folder"

    override fun receive(message: String, sender: (String) -> Unit) {
        val path = message.substringAfter("/update/folder ").substringBefore(" ")
        val name = path.substringAfter("/update/folder ").substringBefore(" ")
        val folder = getFolderFromPath(path)
        if (folder == null) {
            sender.invoke("<error> cant find folder with path '$path'")
            return
        }
        folder.name = name
        sender.invoke("<success>")
    }
}