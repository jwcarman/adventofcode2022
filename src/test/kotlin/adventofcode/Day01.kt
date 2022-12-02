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
        println(readCalorieCounts().first())
    }

    @Test
    fun part2() {
        println(readCalorieCounts().take(3).sum())
    }

    private fun readCalorieCounts(): List<Int> {
        val lines = Input.readAsLines("day01.txt")
        val calorieCounts = mutableListOf<Int>()
        var calorieCount = 0
        lines.forEach { line ->
            if (line.isBlank()) {
                calorieCounts.add(calorieCount)
                calorieCount = 0
            } else {
                calorieCount += line.toInt()
            }
        }
        return calorieCounts.sortedDescending()
    }

}