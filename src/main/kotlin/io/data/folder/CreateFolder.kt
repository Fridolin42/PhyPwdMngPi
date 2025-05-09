package de.fridolin1.io.data.folder

import data.SerializableFolder
import de.fridolin1.getFolderFromPath
import de.fridolin1.io.serial.SerialListener

object CreateFolder : SerialListener {
    override val path = "/create/folder"
    override val saveDataAfterCall = true

    override fun receive(path: String, message: String, sender: (String) -> Unit) {
        val folderPath = message.substringBefore(" ")
        val folderName = message.substringAfter(" ")
        val motherFolder = getFolderFromPath(folderPath)
        if (motherFolder == null) {
            sender.invoke("<error> Cant find folder with path $folderPath")
            return
        }
        val newFolder = SerializableFolder(folderName, mutableListOf(), mutableListOf())
        motherFolder.children.add(newFolder)
        sender.invoke("<success>")
    }
}