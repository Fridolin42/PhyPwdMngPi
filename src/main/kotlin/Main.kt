package de.fridolin1

import com.fazecast.jSerialComm.SerialPort

fun main() {
    println("Hello World!")
    SerialPort.getCommPorts().forEach { println(it) }
}