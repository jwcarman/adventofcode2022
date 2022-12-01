package adventofcode

import org.junit.Test
import java.util.*
import kotlin.math.max

class Day01 {

    @Test
    fun part1() {
        val lines = Input.readAsLines("day01.txt")
        var max = 0
        var sum = 0
        lines.forEach { line ->
            if ("".equals(line)) {
                max = max(sum, max)
                sum = 0
            } else {
                sum += line.toInt()
            }
        }
        println(max)
    }

    @Test
    fun part2() {
        val lines = Input.readAsLines("day01.txt")
        val set = TreeSet<Int>()
        var sum = 0
        lines.forEach { line ->
            if ("".equals(line)) {
                set.add(sum)
                sum = 0
            } else {
                sum += line.toInt()
            }
        }
        println(set.reversed().take(3).sum())
    }
}