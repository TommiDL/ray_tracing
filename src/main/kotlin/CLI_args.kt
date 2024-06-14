package org.example

import com.github.ajalt.clikt.core.CliktCommand


/**
 * Selection class:
 *      Use to inherit subcommands
 */
class Selection:CliktCommand(printHelpOnEmptyArgs = true, name="selection") {
    override fun run()=Unit
}

