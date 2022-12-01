/*
 * Copyright (c) ${date.year} James Carman
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