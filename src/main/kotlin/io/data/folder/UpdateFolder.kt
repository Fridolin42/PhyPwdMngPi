package de.fridolin1.io.data.folder

import de.fridolin1.getFolderFromPath
import de.fridolin1.io.serial.SerialListener

object UpdateFolder : SerialListener {
    override val path = "/update/folder"
    override val saveDataAfterCall = true

    override fun receive(path: String, message: String, sender: (String) -> Unit) {
        val folderPath = message.substringBefore(" ")
        val folderName = folderPath.substringAfter(" ")
        val folder = getFolderFromPath(folderPath)
        if (folder == null) {
            sender.invoke("<error> cant find folder with path '$folderPath'")
            return
        }
        folder.name = folderName
        sender.invoke("<success>")
    }
}