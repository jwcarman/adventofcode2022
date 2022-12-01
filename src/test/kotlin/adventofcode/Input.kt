package adventofcode

import java.io.File

object Input {
    fun readAsLines(name: String) = File("src/test/resources/${name}").readLines()
}