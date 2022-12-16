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

package adventofcode.day15

import adventofcode.util.geom.Point2D
import kotlin.math.abs

data class Sensor(val sensorPoint: Point2D, val beaconPoint: Point2D) {

    private val sensorRange = sensorPoint.manhattanDistance(beaconPoint)
    fun horizontalScanRange(y: Int): IntRange {
        val dist = abs(sensorPoint.y - y)
        return if (dist > sensorRange) {
            IntRange.EMPTY
        } else {
            val diff = sensorRange - dist
            sensorPoint.x - diff..sensorPoint.x + diff
        }
    }
}