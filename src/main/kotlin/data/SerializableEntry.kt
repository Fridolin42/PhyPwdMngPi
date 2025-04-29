package data

import de.fridolin1.listOfAllEntries
import kotlinx.serialization.Serializable

@Serializable
data class SerializableEntry(var website: String, var username: String, var password: String, val id: Long = System.nanoTime()) {
    init {
        listOfAllEntries.add(this)
    }

    override fun toString(): String {
        return "{\"website\":\"$website\",\"username\":\"$username\",\"id\": $id}"
    }

    fun update(updatedEntry: SerializableEntry) {
        website = updatedEntry.website
        username = updatedEntry.username
        password = updatedEntry.password
    }
}
