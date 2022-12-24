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

import kotlin.math.max
import kotlin.math.min

const val THUMBPRINT_SIZE = 50

class Cavern(private val maximumLayers: Int = 100) {
    private val layers = mutableListOf<Int>()
    private val thumbprints = mutableMapOf<Int, ThumbprintStats>()
    private var placedRocks = 0
    private var maxDepth = 0
    var height = 0L
        private set
    var cyclePeriod = 0
        private set
    var cycleDifferential = 0L
        private set

    var cycleFirstDetected = Int.MAX_VALUE
        private set

    fun isUnimpeded(rock: Rock, depth: Int): Boolean {
        if (depth <= 0) {
            return true
        }
        if (depth > layers.size) {
            return false
        }
        val overlap = min(rock.size, depth)
        val rockSegment = rock.takeLast(overlap)
        val layerSegment = layers.subList(depth - overlap, depth)
        return rockSegment.zip(layerSegment).none { it.first.collidesWith(it.second) }
    }

    fun placeRock(rock: Rock, depth: Int) {
        placedRocks++
        maxDepth = max(maxDepth, depth)
        val overlap = min(rock.size, depth)
        val protrusion = rock.size - overlap
        val rockIndices = protrusion until rock.size
        val layerIndices = (depth - overlap) until depth

        rockIndices.zip(layerIndices).forEach { (rockIndex, layerIndex) ->
            layers[layerIndex] = layers[layerIndex] or rock[rockIndex]
        }
        layers.addAll(0, rock.take(protrusion))
        height += protrusion

        checkCycle()
        trimLayers()
    }

    private fun trimLayers() {
        if (layers.size > maximumLayers) {
            layers.dropLast(layers.size - maximumLayers)
        }
    }

    private fun checkCycle() {
        val thumbprint = layers.take(THUMBPRINT_SIZE).hashCode()
        val stats = ThumbprintStats(placedRocks, height)
        val previousStats = thumbprints.put(thumbprint, stats)
        if (previousStats != null) {
            cyclePeriod = stats.placedRocks - previousStats.placedRocks
            cycleDifferential = stats.height - previousStats.height
            cycleFirstDetected = min(previousStats.placedRocks, cycleFirstDetected)
        }
    }

    data class ThumbprintStats(val placedRocks: Int, val height: Long)
}