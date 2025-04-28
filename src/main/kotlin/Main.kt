package de.fridolin1

import com.fazecast.jSerialComm.SerialPort
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.util.*

fun main() {
    val port = SerialPort.getCommPorts()[0]
    println(port)
    port.openPort()
    val scanner = Scanner(port.inputStream)
    val writer = BufferedWriter(OutputStreamWriter(port.outputStream))
    while (true) {
        if (scanner.hasNextLine()) {
            println(scanner.nextLine())
            writer.write("Message received")
        }
    }
}