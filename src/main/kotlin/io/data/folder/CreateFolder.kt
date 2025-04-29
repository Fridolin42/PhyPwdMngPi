package de.fridolin1.io.data.folder

import data.SerializableFolder
import de.fridolin1.getFolderFromPath
import de.fridolin1.io.serial.SerialListener

object CreateFolder : SerialListener {
    override val path = "/create/folder"

    override fun receive(message: String, sender: (String) -> Unit) {
        val path = message.substringAfter("/create/folder ").substringBefore(" ")
        val folderName = message.substringAfter("/create/folder ").substringAfter(" ")
        val motherFolder = getFolderFromPath(path)
        if (motherFolder == null) {
            sender.invoke("<error> Cant find folder with path $path")
            return
        }
        val newFolder = SerializableFolder(folderName, mutableListOf(), mutableListOf())
        motherFolder.children.add(newFolder)
        sender.invoke("<success>")
    }
}