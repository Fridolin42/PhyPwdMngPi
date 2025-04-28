package de.fridolin1

import de.fridolin1.io.data.ExampleData
import de.fridolin1.io.serial.SerialPortIO


fun main() {
    SerialPortIO.addListener(ExampleData)
}

//fun sender() {
//    println("starting sender")
//    val comPort = SerialPort.getCommPorts()[0]
//    if (!comPort.openPort()) {
//        println("Failed to open port!")
//        return
//    }
//    println("Opened port: ${comPort.descriptivePortName}")
//    comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0)
//
//    val reader = BufferedReader(InputStreamReader(comPort.inputStream))
//    val consoleReader = BufferedReader(InputStreamReader(System.`in`))
//    val writer = BufferedWriter(OutputStreamWriter(comPort.outputStream))
//    try {
//        var ask = true
//        while (true) {
//            if (ask) {
//                print("Eingabe: ")
//                ask = false
//            }
//            val input = consoleReader.readLine()
//            if (input == null) continue
//            ask = true
//            println(input)
//            writer.write(input + "\n")
//            writer.flush()
//            if (input == "exit")
//                break
//            val line = reader.readLine()
//            if (line != null) {
//                println("Received: $line")
//            } else {
//                println("Stream closed")
//                break
//            }
//        }
//    } catch (e: Exception) {
//        e.printStackTrace()
//    } finally {
//        println("closing...")
//        reader.close()
//        comPort.closePort()
//        consoleReader.close()
//        writer.close()
//        println("Port closed.")
//    }
//}