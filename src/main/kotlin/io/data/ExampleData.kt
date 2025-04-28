package de.fridolin1.io.data

import de.fridolin1.io.serial.SerialListener

object ExampleData : SerialListener {
    override val path = "/get/structure"

    override fun receive(message: String, sender: (message: String) -> Unit) {
        println("sending example data...")
        val data =
            "{\"name\": \"/\",\"children\": [{\"name\": \"cloud\",\"children\": [],\"entries\": [{\"website\": \"mvnrepository.com\",\"username\": \"test@mail.com\"}]},{\"name\": \"programming\",\"children\": [],\"entries\": [{\"website\": \"jetbrains.com\",\"username\": \"test@mail.com\"}]}],\"entries\": [{\"website\": \"vplan.plus\", \"username\": \"Fridolin\"},{\"website\": \"chat.openai.com\",\"username\": \"test@mail.com\"},{\"website\": \"chefkoch.de\",\"username\": \"max musterman\"}]}"
        sender.invoke(data)
    }

}