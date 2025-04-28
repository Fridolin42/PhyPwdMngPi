package de.fridolin1

import com.fazecast.jSerialComm.SerialPort
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter


fun main(args: Array<String>) {
    println(args.joinToString(prefix = "Arguments: ", separator = ", ") + "\n")
    if (args[0] == "0") receiver()
    else if (args[0] == "1") sender()
    else println("no valid argument: ${args[0]}")
}

fun sender() {
    println("starting sender")
    val comPort = SerialPort.getCommPorts()[0]
    if (!comPort.openPort()) {
        println("Failed to open port!")
        return
    }
    println("Opened port: ${comPort.descriptivePortName}")
    comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0)

    val reader = BufferedReader(InputStreamReader(comPort.inputStream))
    val consoleReader = BufferedReader(InputStreamReader(System.`in`))
    val writer = BufferedWriter(OutputStreamWriter(comPort.outputStream))
    try {
        var ask = true
        while (true) {
            if (ask) {
                print("Eingabe: ")
                ask = false
            }
            val input = consoleReader.readLine()
            if (input == null) continue
            ask = true
            println(input)
            writer.write(input + "\n")
            writer.flush()
            if (input == "exit")
                break
            val line = reader.readLine()
            if (line != null) {
                println("Received: $line")
            } else {
                println("Stream closed")
                break
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        println("closing...")
        reader.close()
        comPort.closePort()
        consoleReader.close()
        writer.close()
        println("Port closed.")
    }
}

fun receiver() {
    println("starting receiver")
    val comPort = SerialPort.getCommPorts()[0]
    if (!comPort.openPort()) {
        println("Failed to open port!")
        return
    }
    println("Opened port: ${comPort.descriptivePortName}")
    comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0)

    val reader = BufferedReader(InputStreamReader(comPort.inputStream))
    val writer = BufferedWriter(OutputStreamWriter(comPort.outputStream))
    try {
        while (true) {
            val line = reader.readLine()
            if (line != null) {
                if (line != "exit") {
                    println("Received: $line")
                    writer.write("msg received!\n")
                    writer.flush()
                } else break
            } else {
                println("Stream closed")
                break
            }
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