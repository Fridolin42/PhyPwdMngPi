package de.fridolin1.io.file

import data.SerializableFolder
import kotlinx.serialization.json.Json
import java.io.File

object PwdFileManager {
    val file = File("PhysicalPasswordManager.txt")
    val reader = file.bufferedReader()
    val writer = file.bufferedWriter()

    fun getRootFolder(): SerializableFolder {
        return Json.decodeFromString<SerializableFolder>(reader.readText())
    }

    fun saveRootFolder(folder: SerializableFolder) {
        writer.write(Json.encodeToString(folder))
    }
}