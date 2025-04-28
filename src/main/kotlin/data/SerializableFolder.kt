package data

import kotlinx.serialization.Serializable

@Serializable
data class SerializableFolder(var name: String, var children: List<SerializableFolder>, var entries: MutableList<SerializableEntry>) {
    override fun toString(): String {
        return "{\"name\"=\"$name\",\"children\"=\"$children\",\"entries\"=\"$entries\"}"
    }
}
