package data

import kotlinx.serialization.Serializable

@Serializable
data class SerializableFolder(
    var name: String,
    var children: MutableList<SerializableFolder>,
    var entries: MutableList<SerializableEntry>
) {
    override fun toString(): String {
        return "{\"name\":\"$name\",\"children\":$children,\"entries\":$entries}"
    }

    fun searchNodeWithEntry(entry: SerializableEntry): SerializableFolder? {
        if (entries.contains(entry)) return this
        children.forEach { it.searchNodeWithEntry(entry)?.let { f -> return f } }
        return null
    }
}
