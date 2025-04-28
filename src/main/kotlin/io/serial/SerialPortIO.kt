package de.fridolin1.io.serial

import com.fazecast.jSerialComm.SerialPort
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

    init {
        if (!comPort.openPort())
            throw error("failed to open port ${comPort.descriptivePortName}")
        else
            println("Opened port ${comPort.descriptivePortName}")
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

    fun addListener(listener: SerialListener) {
        listeners.add(listener)
    }

    private fun receive(message: String) {
        listeners.filter { message.startsWith(it.path) }.forEach { it.receive(message) { msg -> send(msg) } }
    }

    fun send(message: String) {
        println("Message sent: $message")
        writer.write("message\n")
        writer.flush()
    }
}