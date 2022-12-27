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

package adventofcode.day19

import adventofcode.day19.Blueprint.Companion.parseBlueprint
import adventofcode.util.readAsString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day19 {
    @Test
    fun example1() {
        assertThat(calculatePart1(readAsString("day19-example.txt"))).isEqualTo(33)
    }

    @Test
    fun part1() {
        assertThat(calculatePart1(readAsString("day19.txt"))).isEqualTo(994)
    }

    @Test
    fun example2() {
        assertThat(calculatePart2(readAsString("day19-example.txt"))).isEqualTo(3472)
    }

    @Test
    fun part2() {
        assertThat(calculatePart2(readAsString("day19.txt"))).isEqualTo(15960)
    }

    fun calculatePart1(input: String): Int {
        val blueprints = parseBlueprints(input)
        return blueprints.sumOf { it.qualityLevel(24) }
    }

    fun calculatePart2(input: String): Int {
        val blueprints = parseBlueprints(input)
        return blueprints.take(3).map { it.calculateMaximum(32) }.reduce(Int::times)
    }

    private fun parseBlueprints(input: String) = input.lines().map { parseBlueprint(it) }
}