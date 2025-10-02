package de.fridolin1.io.data

import de.fridolin1.crypto.RSA
import de.fridolin1.io.serial.SerialListener

object KeyExchange : SerialListener {
    override val path = "/keyExchange"
    override val rawBody = true
    val rsaModule = RSA()
    lateinit var pcPublicKey: String
        private set
    override val requireUserInteraction = false

    override fun receive(path: String, message: String, sender: (String) -> Unit) {
        pcPublicKey = message
        val ownPublicKey = rsaModule.getPublicKey()
        val parts = ownPublicKey.chunked(200).map { rsaModule.encrypt(it, pcPublicKey) }
        val encryptedKey = parts.joinToString(" ")
        sender.invoke(encryptedKey)
    }
}