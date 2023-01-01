/*
 * Copyright (c) 2023 James Carman
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

package adventofcode.day15

import adventofcode.util.geom.plane.Point2D
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SensorTest {
    @Test
    fun horizontalScanRangeReturnsEmptyRange() {
        val sensor = Sensor(Point2D(0, 0), Point2D(0, 1))
        assertEquals(IntRange.EMPTY, sensor.horizontalScanRange(5))
    }

    @Test
    fun horizontalScanRangeReturnsSingle() {
        val sensor = Sensor(Point2D(0, 0), Point2D(0, 1))
        assertEquals(0..0, sensor.horizontalScanRange(1))
    }

    @Test
    fun horizontalScanRangeReturnsRange() {
        val sensor = Sensor(Point2D(0, 0), Point2D(0, 4))
        assertEquals(-1..1, sensor.horizontalScanRange(3))
        assertEquals(-2..2, sensor.horizontalScanRange(2))
        assertEquals(-3..3, sensor.horizontalScanRange(1))
        assertEquals(-4..4, sensor.horizontalScanRange(0))
    }

}