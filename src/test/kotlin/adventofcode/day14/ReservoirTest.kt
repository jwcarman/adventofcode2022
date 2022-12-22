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

package adventofcode.day14

import adventofcode.util.geom.Point2D
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ReservoirTest {
    @Test
    fun addRockStructureWithNoPoints() {
        val reservoir = Reservoir()
        reservoir.addRockStructure(listOf())

        assertThat(reservoir.isEmpty()).isTrue
    }

    @Test
    fun addRockStructureWithSinglePoint() {
        val reservoir = Reservoir()
        reservoir.addRockStructure(listOf(Point2D.origin()))
        assertThat(reservoir.isBlocked(Point2D.origin())).isTrue
    }

    @Test
    fun addRockStructureWithMultiplePoints() {
        val reservoir = Reservoir()
        reservoir.addRockStructure(listOf(Point2D.origin(), Point2D(2, 0), Point2D(2,2)))
        assertThat(reservoir.isBlocked(Point2D.origin())).isTrue
        assertThat(reservoir.isBlocked(Point2D(1,0))).isTrue
        assertThat(reservoir.isBlocked(Point2D(2,0))).isTrue
        assertThat(reservoir.isBlocked(Point2D(2,1))).isTrue
        assertThat(reservoir.isBlocked(Point2D(2,2))).isTrue
    }
}