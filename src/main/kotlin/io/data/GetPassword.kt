package de.fridolin1.io.data

import data.SerializableFolder
import de.fridolin1.io.serial.SerialListener
import de.fridolin1.rootFolder

object GetPassword : SerialListener {
    override val path = "/get/password"

    override fun receive(message: String, sender: (String) -> Unit) {
        try {
            val path = message.substringAfter("/get/password ").substringBefore(" ")
            val id = message.substringAfterLast(" ").toLong()
            val segments = path.split("/")
            var current: SerializableFolder? = rootFolder
            for (segment in segments) {
                if (current == null) return sender.invoke("could not find folder $path")
                current = current.children.firstOrNull { it.name == segment }
            }
            val entry = current?.entries?.firstOrNull { it.id == id }
            if (entry == null) return sender.invoke("could not find entry $id in path $path")
            sender.invoke(entry.password)
        } catch (e: Exception) {
            sender.invoke("error: ${e.stackTraceToString()}")
        }
    }

}