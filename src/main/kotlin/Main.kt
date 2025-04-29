package de.fridolin1

import data.SerializableEntry
import data.SerializableFolder
import de.fridolin1.io.data.entry.CreateEntry
import de.fridolin1.io.data.entry.DeleteEntry
import de.fridolin1.io.data.entry.ReadPassword
import de.fridolin1.io.data.ReadStructure
import de.fridolin1.io.data.entry.UpdateEntry
import de.fridolin1.io.data.folder.CreateFolder
import de.fridolin1.io.data.folder.DeleteFolder
import de.fridolin1.io.data.folder.UpdateFolder
import de.fridolin1.io.file.PwdFileManager.getRootFolder
import de.fridolin1.io.serial.SerialPortIO

val rootFolder = getRootFolder()
val listOfAllEntries = mutableListOf<SerializableEntry>()

fun main() {
    //structure
    SerialPortIO.addListener(ReadStructure)
    //entries
    SerialPortIO.addListener(ReadPassword)
    SerialPortIO.addListener(UpdateEntry)
    SerialPortIO.addListener(CreateEntry)
    SerialPortIO.addListener(DeleteEntry)
    //folder
    SerialPortIO.addListener(CreateFolder)
    SerialPortIO.addListener(UpdateFolder)
    SerialPortIO.addListener(DeleteFolder)
    //setup done
    println("listening...")
}

fun getFolderFromPath(path: String): SerializableFolder? {
    var current = rootFolder
    for (segment in path.split("/")) {
        val new = current.children.firstOrNull { it.name == segment }
        if (new == null) return null
        current = new
    }
    return current
}