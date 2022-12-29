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

package adventofcode.day20

import adventofcode.util.readAsString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day20 {
    @Test
    fun example1() {
        assertThat(calculatePart1(readAsString("day20-example.txt"))).isEqualTo(3L)
    }

    @Test
    fun part1() {
        assertThat(calculatePart1(readAsString("day20.txt"))).isEqualTo(19070L)
    }

    @Test
    fun example2() {
        assertThat(calculatePart2(readAsString("day20-example.txt"))).isEqualTo(1623178306L)
    }

    @Test
    fun part2() {
        assertThat(calculatePart2(readAsString("day20.txt"))).isEqualTo(14773357352059L)
    }

    fun calculatePart1(input: String): Long {
        val original = input.lines().map { it.toLong() }
        val encryptedFile = EncryptedFile(original)
        encryptedFile.mix()
        return encryptedFile.groveCoordinates().sum()
    }

    fun calculatePart2(input: String): Long {
        val original = input.lines().map { it.toLong() }
        val encryptedFile = EncryptedFile(original, 811589153)
        repeat(10) {
            encryptedFile.mix()
        }
        return encryptedFile.groveCoordinates().sum()
    }


}