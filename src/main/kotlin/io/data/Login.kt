package de.fridolin1.io.data

import de.fridolin1.io.file.PwdFileManager
import de.fridolin1.io.serial.SerialListener
import de.fridolin1.io.serial.SerialPortIO

object Login : SerialListener {
    override val path = "/login"
    override val rawBody = true
    override val requireUserInteraction = false

    override fun receive(path: String, message: String, sender: (String) -> Unit) {
        val passwordEncrypted = message.substringBefore(" ")
        val signature = message.substringAfter(" ")
        val signed = KeyExchange.rsaModule.verify(passwordEncrypted, signature, KeyExchange.pcPublicKey)
        println("Signature: $signed")
        if (!signed) {
            sender.invoke("<login> wrong signature")
            return
        }
        val password = KeyExchange.rsaModule.decrypt(passwordEncrypted)
        val pwdCorrect = PwdFileManager.startSession(password)
        println("Password: $password, correct: $pwdCorrect")
        if (!pwdCorrect) {
            sender.invoke("<login> wrong password")
            return
        }
        SerialPortIO.setPassword(password)
        sender.invoke("<login> okay")
    }
}