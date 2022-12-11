/*
 * Copyright (c) 2022 James Carman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package adventofcode

import adventofcode.util.readAsLines
import org.junit.Test
import kotlin.test.assertEquals

const val TOTAL_SPACE = 70000000
const val REQUIRED_SPACE = 30000000

class Day07 {
    private fun String.isNumeric(): Boolean = all { char -> char.isDigit() }

    @Test
    fun example1() {
        assertEquals(95437, calculatePart1(readAsLines("day07-example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(1306611, calculatePart1(readAsLines("day07.txt")))
    }

    @Test
    fun example2() {
        assertEquals(24933642, calculatePart2(readAsLines("day07-example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(13210366, calculatePart2(readAsLines("day07.txt")))
    }


    private fun calculatePart1(lines: List<String>): Int {
        val fs = buildFilesystem(lines)
        return fs.findFiles { (_, size) -> size <= 100000 }.values.sum()
    }

    private fun calculatePart2(lines: List<String>): Int {
        val fs = buildFilesystem(lines)
        val used = fs.sizeOf("/")
        val unused = TOTAL_SPACE - used
        val needed = REQUIRED_SPACE - unused
        return fs.findFiles { (_, size) -> size >= needed }.values.minOf { it }
    }

    private fun buildFilesystem(lines: List<String>): Filesystem {
        val fs = Filesystem()
        lines.forEach { line ->
            val splits = line.split(" ")
            when {
                line == "$ cd .." -> fs.cdup()
                line.startsWith("$ cd ") -> fs.cd(splits.last())
                splits[0].isNumeric() -> fs.addFile(splits[0].toInt())
            }
        }
        return fs
    }

    class Filesystem {
        val dirSizes = mutableMapOf<String, Int>()
        var pwd = mutableListOf<String>()

        fun cd(dir: String) {
            if (pwd.isEmpty()) {
                pwd.add(dir)
            } else {
                pwd.add(0, pwd.first() + dir + "/")
            }
        }

        fun sizeOf(dir: String): Int = dirSizes.getOrDefault(dir, -1)

        fun cdup() {
            pwd.removeFirst()
        }

        fun addFile(size: Int) {
            pwd.forEach { dir ->
                dirSizes[dir] = dirSizes.getOrDefault(dir, 0) + size
            }
        }

        fun findFiles(predicate: (Map.Entry<String, Int>) -> Boolean) = dirSizes.filter(predicate)
    }
}