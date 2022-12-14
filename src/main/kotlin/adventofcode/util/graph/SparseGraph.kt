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

class SparseGraph<V : Any, E : Any> : Graph<V, E> {

    private val adjacency = mutableMapOf<V, MutableMap<V,Edge<E>>>()

    override fun addVertex(value: V) {
        adjacency[value] = mutableMapOf()
    }

    override fun addEdge(from: V, to: V, value: E, weight: Double) {
        adjacency[vertex(from)]!![vertex(to)] = Edge(value, weight)
    }

    override fun neighbors(from: V): List<V> {
        return adjacency[vertex(from)]!!.keys.toList()
    }

    private fun vertex(value: V): V {
        if (value in adjacency) {
            return value
        }
        throw IllegalArgumentException("Vertex $value does not exist.")
    }

    override fun edge(from: V, to: V): Edge<E>? = adjacency[vertex(from)]!![vertex(to)]

    override fun vertices(): Set<V> = adjacency.keys
}