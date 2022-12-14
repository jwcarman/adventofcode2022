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

package adventofcode.util.graph

class ShortestPaths<V>(
    private val start: V,
    private val distances: Map<V, Double>,
    private val predecessors: Map<V, V>
) {

    fun start() = start
    fun pathExists(end: V): Boolean = end in predecessors

    fun pathTo(end: V): List<V> {
        if (!pathExists(end)) {
            return listOf()
        }
        return pathTo(predecessors[end]!!) + end
    }

    fun distanceTo(end: V): Double = distances[end] ?: Double.POSITIVE_INFINITY
}