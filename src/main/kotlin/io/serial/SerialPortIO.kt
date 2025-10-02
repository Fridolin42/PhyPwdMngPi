package de.fridolin1.io.serial

import com.fazecast.jSerialComm.SerialPort
import com.pi4j.io.gpio.digital.PullResistance
import com.pi4j.ktx.io.digital.digitalInput
import com.pi4j.ktx.io.digital.onLow
import com.pi4j.ktx.io.digital.piGpioProvider
import com.pi4j.ktx.pi4j
import de.fridolin1.PIN_BUTTON
import de.fridolin1.USER_INTERACTION
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
    private var button = false

    init {
        //serial communication
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
        //button
        pi4j {
            digitalInput(PIN_BUTTON) {
                id("button")
                name("Press button")
                pull(PullResistance.PULL_DOWN)
                debounce(50L)
                piGpioProvider()
            }.onLow {
                button = true
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
                if (USER_INTERACTION && it.requireUserInteraction && !waitForButton()) return
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

    fun waitForButton(): Boolean {
        button = false
        var timer = 0
        while (!button) {
            if (timer > 20000) return false //20 seconds for pressing the button
            Thread.sleep(10)
            timer += 10
        }
        return true
    }
}