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

package adventofcode.day17

import adventofcode.util.Repeater
import adventofcode.util.readAsString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


class Day17 {


    @Test
    fun example1() {
        assertThat(calculatePart1(readAsString("day17-example.txt"))).isEqualTo(3068)
    }

    @Test
    fun part1() {
        assertThat(calculatePart1(readAsString("day17.txt"))).isEqualTo(3235)
    }

    @Test
    fun example2() {
        assertThat(calculatePart2(readAsString("day17-example.txt"))).isEqualTo(1514285714288L)
    }

    @Test
    fun part2() {
        assertThat(calculatePart2(readAsString("day17.txt"))).isEqualTo(1591860465110L)
    }

    private fun runSimulation(input: String, n: Int): Cavern {
        val jets = Repeater(input.asSequence().toList())
        val rocks = Repeater(rocks)
        val cavern = Cavern()
        repeat(n) {
            var rock = rocks.next()
            var depth = -3
            var falling = true
            while (falling) {
                val jet = jets.next()
                val pushed = rock.push(jet)
                if (cavern.isUnimpeded(pushed, depth)) {
                    rock = pushed
                }
                if (cavern.isUnimpeded(rock, depth + 1)) {
                    depth++
                } else {
                    falling = false
                }
            }
            cavern.placeRock(rock, depth)
        }
        return cavern
    }

    fun calculatePart1(input: String): Long {
        val cavern = runSimulation(input, 2022)
        return cavern.height()
    }


    fun calculatePart2(input: String): Long {
        val cavern = runSimulation(input, 2000)
        val total = 1000000000000L
        val headLength = cavern.cycleFirstDetected()
        val headHeight = runSimulation(input, headLength).height()
        val tailLength = ((total - headLength) % cavern.cyclePeriod()).toInt()
        val tailHeight = runSimulation(input, headLength + tailLength).height() - headHeight
        val fullCycles = (total - headLength) / cavern.cyclePeriod()
        val fullCyclesHeight = fullCycles * cavern.cycleDifferential()
        return headHeight + fullCyclesHeight + tailHeight
    }
}