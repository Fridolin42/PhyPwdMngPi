package de.fridolin1.io.file

import data.SerializableFolder
import kotlinx.serialization.json.Json
import java.io.File

object PwdFileManager {
    val file = File("PhysicalPasswordManager.txt")

    fun getRootFolder(): SerializableFolder {
        val reader = file.bufferedReader()
        val text = reader.readText()
        reader.close()
        return Json.decodeFromString<SerializableFolder>(text)
    }

    fun saveRootFolder(folder: SerializableFolder) {
        val writer = file.bufferedWriter()
        writer.write(Json.encodeToString(folder))
        writer.close()
    }
}