package de.fridolin1.io.file

import data.SerializableFolder
import de.fridolin1.crypto.AES
import de.fridolin1.rootFolder
import kotlinx.serialization.json.Json
import java.io.File

object PwdFileManager {
    private val file = File("PhysicalPasswordManager.txt")
    private lateinit var aesModule: AES

    init {
        if (!file.exists()) file.createNewFile()
    }

    private fun getRootFolder(): SerializableFolder {
        val reader = file.bufferedReader()
        val encryptedText = reader.readLine()
        if (encryptedText.isNullOrEmpty()) return SerializableFolder("/", mutableListOf(), mutableListOf())
        val text = aesModule.decrypt(encryptedText)
        reader.close()
        return Json.decodeFromString<SerializableFolder>(text)
    }

    fun saveRootFolder() {
        println("starting to save...")
        val writer = file.bufferedWriter()
        val encrypted = aesModule.encrypt(Json.encodeToString(rootFolder))
        writer.write(encrypted)
        writer.close()
        println("successfully saved root folder")
    }

    fun startSession(password: String): Boolean {
        aesModule = AES(password)
        println("Starting session...")
        rootFolder = getRootFolder()
        return true
    }
}