package com.example.kotlinprinterversion

class Formatter {
    /** The format that is being build on  */
    private val mFormat: ByteArray = byteArrayOf(27, 33, 0)

    fun bold(): Byte {
        // Apply bold:
        return (0x8 or mFormat[2].toInt()).toByte()
    }

    fun small(): Byte {
        return (0x1 or mFormat[2].toInt()).toByte()
    }

    fun height(): Byte {
        return (0x10 or mFormat[2].toInt()).toByte()
    }

    fun width(): Byte {
        return (0x20 or mFormat[2].toInt()).toByte()
    }

    fun underlined(): Byte {
        return (0x80 or mFormat[2].toInt()).toByte()
    }

    fun rightAlign(): ByteArray {
        return byteArrayOf(0x1B, 'a'.code.toByte(), 0x02)
    }

    fun leftAlign(): ByteArray {
        return byteArrayOf(0x1B, 'a'.code.toByte(), 0x00)
    }

    fun centerAlign(): ByteArray {
        return byteArrayOf(0x1B, 'a'.code.toByte(), 0x01)
    }
}