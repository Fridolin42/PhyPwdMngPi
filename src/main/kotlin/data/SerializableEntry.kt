package data

import kotlinx.serialization.Serializable

@Serializable
data class SerializableEntry(var website: String, var username: String, var password: String, val id: Long = System.nanoTime()) {
    override fun toString(): String {
        return "{\"website\":\"$website\",\"username\":\"$username\",\"id\": $id}"
    }
}
