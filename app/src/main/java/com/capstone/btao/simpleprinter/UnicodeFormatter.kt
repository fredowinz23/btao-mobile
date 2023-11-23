package com.example.kotlinprinterversion

object UnicodeFormatter {
    fun byteToHex(b: Byte): String {
        // Returns hex String representation of byte b
        val hexDigit = charArrayOf(
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'
        )
        val array = charArrayOf(
            hexDigit[b.toInt() shr 4 and 0x0f],
            hexDigit[b.toInt() and 0x0f]
        )
        return String(array)
    }

    fun charToHex(c: Char): String {
        // Returns hex String representation of char c
        val hi = (c.code ushr 8).toByte()
        val lo = (c.code and 0xff).toByte()
        return byteToHex(hi) + byteToHex(lo)
    }
} // class
