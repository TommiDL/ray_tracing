package org.example

import java.io.InputStream

/**
 * Class InputStream: uses a stream for reading from a file
 * This class implements a wrapper around a stream, with the following additional capabilities:
 *     - It tracks the line number and column number;
 *     - It permits to "un-read" characters and tokens.
 */
class InputStream {

    private var stream: java.io.InputStream
    private var file_name: String
    private var tabulations: Int

    constructor(stream: java.io.InputStream, file_name: String = "", tabulations: Int = 8) {
        this.stream = stream
        this.file_name = file_name
        this.tabulations = tabulations
        this.location = SourceLocation(file_name = file_name, line_num = 1, col_num = 1)
        this.saved_location = this.location
        this.saved_char = '\u0000'
    }

    var location: SourceLocation
    var saved_char: Char?
    var saved_location: SourceLocation

    /**
     *         """Read a new character from the stream with a check on the end of the stream (byte == -1)"""
     */
    fun read_char(): Char? {

        val ch: Char?

        if (saved_char != '\u0000') {
            ch = saved_char
            saved_char = '\u0000'
        } else {
            val byte = this.stream.read()
            ch = if (byte == -1) '\u0000' else byte.toChar()
        }

        saved_location = location.copy()
        update_pos(ch)
        return ch

    }

    /**
     *         """Push a character back to the stream"""
     */
    fun unread_char(ch: Char?) {
        require(this.saved_char == '\u0000')
        this.saved_char = ch
        this.location = this.saved_location.copy()
    }

    /**
     *         """Keep reading characters until a non-whitespace/non-comment character is found"""
     */
    fun skip_whitespaces_and_comments(){
        val WHITESPACE = listOf(' ', '\t', '\n', '\r')
        var ch = this.read_char()

        while (ch in WHITESPACE || ch == '#') {
            if (ch == '#') {
                //It's a comment! Keep reading until the end of the line (include the case "", the end-of-file)
                while (this.read_char() !in listOf('\r', '\n', '\u0000')){
                    //do nothing
                    continue
                }
            }
            ch = this.read_char()
            if (ch == '\u0000') return
        }

        // Put the non-whitespace character back
        this.unread_char(ch)
    }

    /**
     *         """Update `location` after having read `ch` from the stream"""
     */
    private fun update_pos(ch: Char?) {
        when (ch) {
            '\u0000' -> {
                return
            }
            '\n' -> {
                this.location.line_num += 1
                this.location.col_num = 1
            }
            '\t' -> {
                this.location.col_num += this.tabulations
            }
            else -> {
                this.location.col_num += 1
            }
        }
    }

}