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

package adventofcode.day16

import adventofcode.util.readAsString
import adventofcode.util.search.dp.DynamicProgrammingState
import adventofcode.util.search.dp.maximum
import org.junit.jupiter.api.Test
import kotlin.math.max
import kotlin.test.assertEquals

class Day16 {
    @Test
    fun example1() {
        assertEquals(1651, calculatePart1(readAsString("day16-example.txt")))
    }

    @Test
    fun part1() {
        assertEquals(1580, calculatePart1(readAsString("day16.txt")))
    }

    @Test
    fun example2() {
        assertEquals(1707, calculatePart2(readAsString("day16-example.txt")))
    }

    @Test
    fun part2() {
        assertEquals(2213, calculatePart2(readAsString("day16.txt")))
    }

    private fun calculatePart1(input: String): Int {
        val volcano = Volcano.parse(input)
        return maximum(SoloSearchState(volcano, 30))
    }

    private fun calculatePart2(input: String): Int {
        val volcano = Volcano.parse(input)

        val cache = mutableMapOf<SoloSearchState, Int>()
        return volcano.flowValveSubsetPairs().map {
            val first = maximum(SoloSearchState(volcano, 26, it.first), cache)
            val second = maximum(SoloSearchState(volcano, 26, it.second), cache)
            first + second
        }.max()
    }


    private class SoloSearchState : DynamicProgrammingState<SoloSearchState,Int> {

        val volcano: Volcano
        val valvesRemaining: Set<Valve>
        val timeRemaining: Int
        val currentValve: Valve

        constructor(volcano: Volcano, timeRemaining: Int, valvesRemaining:Set<Valve>) {
            this.volcano = volcano
            this.timeRemaining = timeRemaining
            this.valvesRemaining = valvesRemaining
            this.currentValve = Valve.start()
        }
        constructor(volcano: Volcano, timeRemaining: Int) {
            this.volcano = volcano
            this.timeRemaining = timeRemaining
            this.valvesRemaining = volcano.valves().filter { it.flowRate > 0 }.toSet()
            this.currentValve = Valve.start()
        }

        constructor(parent: SoloSearchState, tunnel: Tunnel) {
            this.volcano = parent.volcano
            this.valvesRemaining = parent.valvesRemaining - tunnel.to
            this.currentValve = tunnel.to
            this.timeRemaining = parent.timeRemaining - tunnel.travelTime
        }

        override fun isTerminal(): Boolean {
            return timeRemaining <= 0 || valvesRemaining.isEmpty()
        }

        override fun children(): List<SoloSearchState> {
            return volcano.tunnelsFrom(currentValve)
                .filter { it.to in valvesRemaining }
                .map { SoloSearchState(this, it) }
        }

        override fun value(): Int {
            return max(0, (timeRemaining * currentValve.flowRate))
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as SoloSearchState

            if (valvesRemaining != other.valvesRemaining) return false
            if (timeRemaining != other.timeRemaining) return false
            if (currentValve != other.currentValve) return false

            return true
        }

        override fun hashCode(): Int {
            var result = valvesRemaining.hashCode()
            result = 31 * result + timeRemaining
            result = 31 * result + currentValve.hashCode()
            return result
        }
    }
}