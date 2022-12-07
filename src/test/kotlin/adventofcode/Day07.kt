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

import org.junit.Test
import kotlin.test.assertEquals

const val totalFileSystemSize = 70000000
const val requiredSpace = 30000000

class Day07 {

    @Test
    fun example1() {
        assertEquals(95437, calculatePart1(readAsLines("day07-example.txt")))
    }

    @Test
    fun part1() {
        println(calculatePart1(readAsLines("day07.txt")))
    }

    @Test
    fun example2() {
        assertEquals(24933642, calculatePart2(readAsLines("day07-example.txt")))
    }

    @Test
    fun part2() {
        println(calculatePart2(readAsLines("day07.txt")))
    }

    private fun String.isNumeric(): Boolean = all { char -> char.isDigit() }

    private fun calculatePart1(lines: List<String>): Int {
        val dirSizes = calculateDirectorySizes(lines)
        return dirSizes.values.filter { it <= 100000 }.sum()
    }

    private fun calculateDirectorySizes(lines: List<String>): MutableMap<String, Int> {
        val filesystem = Filesystem()

        lines.forEach { line ->
            val splits = line.split(" ")
            when {
                line == "$ cd .." -> filesystem.cdup()
                line.startsWith("$ cd ") -> filesystem.cd(splits.last())
                splits[0].isNumeric() -> filesystem.addFile(splits[0].toInt())
            }
        }

        return filesystem.dirSizes
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

        fun cdup() {
            pwd.removeFirst()
        }

        fun addFile(size: Int) {
            pwd.forEach { dir ->
                dirSizes[dir] = dirSizes.getOrDefault(dir, 0) + size
            }
        }
    }

    private fun calculatePart2(lines: List<String>): Int {
        val directorySizes = calculateDirectorySizes(lines)
        val usedSpace = directorySizes.getOrDefault("/", 0)
        val unUsedSpace = totalFileSystemSize - usedSpace
        val requiredToDelete = requiredSpace - unUsedSpace
        return directorySizes.values.filter { it >= requiredToDelete }.sorted().first()
    }

}