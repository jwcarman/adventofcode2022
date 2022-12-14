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

import adventofcode.util.readAsString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DayTemplate {
    @Test
    fun example1() {
        assertThat(calculatePart1(readAsString("dayXX-example.txt"))).isEqualTo(0)
    }

    @Test
    fun part1() {
        assertThat(calculatePart1(readAsString("dayXX.txt"))).isEqualTo(0)
    }

    @Test
    fun example2() {
        assertThat(calculatePart2(readAsString("dayXX-example.txt"))).isEqualTo(0)
    }

    @Test
    fun part2() {
        assertThat(calculatePart2(readAsString("dayXX.txt"))).isEqualTo(0)
    }

    fun calculatePart1(input:String): Int {
        return 0
    }

    fun calculatePart2(input:String): Int {
        return 0
    }
}