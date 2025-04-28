package de.fridolin1

import com.fazecast.jSerialComm.SerialPort


fun main() {
    println("starting")
    val comPort = SerialPort.getCommPorts()[0]
    comPort.openPort()
    println(comPort)
    comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0)
    val inputStream = comPort.inputStream
    try {
        repeat(100000) {
            print(inputStream.read().toChar())
            Thread.sleep(10)
        }
        inputStream.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    comPort.closePort()
}