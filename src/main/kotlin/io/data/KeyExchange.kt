package de.fridolin1.io.data

import de.fridolin1.crypto.RSA
import de.fridolin1.io.serial.SerialListener

object KeyExchange : SerialListener {
    override val path = "/keyExchange"
    override val rawBody = true
    val rsaModule = RSA()
    lateinit var pcPublicKey: String
        private set

    override fun receive(path: String, message: String, sender: (String) -> Unit) {
        pcPublicKey = message
        sender.invoke(rsaModule.encrypt(rsaModule.getPublicKey(), pcPublicKey))
    }
}