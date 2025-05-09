package de.fridolin1.io.serial

import com.fazecast.jSerialComm.SerialPort
import de.fridolin1.crypto.AES
import de.fridolin1.io.file.PwdFileManager
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import kotlin.concurrent.thread

object SerialPortIO {
    private val listeners = mutableListOf<SerialListener>()
    private val comPort: SerialPort = SerialPort.getCommPorts()[0]
    private val reader = BufferedReader(InputStreamReader(comPort.inputStream))
    private val writer = BufferedWriter(OutputStreamWriter(comPort.outputStream))
    private lateinit var aesModule: AES

    init {
        if (!comPort.openPort())
            throw error("failed to open port ${comPort.descriptivePortName}")
        else
            println("Opened port: ${comPort.descriptivePortName}")
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0)
        thread {
            try {
                while (true) {
                    val line = reader.readLine()
                    if (line == null) {
                        println("Stream closed")
                        break
                    }
                    if (line == "exit") {
                        println("exit")
                        break
                    }
                    println("Received: $line")
                    receive(line)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                reader.close()
                comPort.closePort()
                writer.close()
                println("Port closed.")
            }
        }
    }

    fun setPassword(password: String) {
        aesModule = AES(password)
    }

    fun addListener(listener: SerialListener) {
        listeners.add(listener)
    }

    private fun receive(body: String) {
        val path = body.substringBefore(" ")

        listeners.filter { path.startsWith(it.path) }.forEach {
            try {
                val message = if (it.rawBody) body.substringAfter(" ")
                else if (body.substringAfter(" ").isEmpty()) ""
                else aesModule.decrypt(body.substringAfter(" "))
                it.receive(path, message) { msg -> send(msg, it.rawBody) }
                if (it.saveDataAfterCall) PwdFileManager.saveRootFolder()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun send(message: String, raw: Boolean) {
        println("Message sent: $message")
        if (raw) writer.write("$message\n")
        else writer.write("${aesModule.encrypt(message)}\n")
        writer.flush()
    }
}