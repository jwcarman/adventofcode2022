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

import java.util.*

interface Graph<V : Any, E : Any> {
    fun addVertex(value: V)
    fun addEdge(from: V, to: V, value: E, weight: Double = 0.0)

    fun vertices(): Set<V>

    fun edge(from: V, to: V): Edge<E>?

    fun neighbors(from: V): List<V>

    private fun compareDoubles(left: Double, right: Double): Int {
        if (left < right) {
            return -1
        }
        if (right < left) {
            return 1
        }
        return 0
    }

    fun shortestPaths(start: V): ShortestPaths<V> {
        val pred = mutableMapOf<V, V>()
        val dist = mutableMapOf<V, Double>()
        val visited = mutableSetOf<V>()
        vertices().forEach { vertex -> dist[vertex] = Double.POSITIVE_INFINITY }
        dist[start] = 0.0
        val queue = PriorityQueue { l: V, r: V -> compareDoubles(dist[l]!!, dist[r]!!) }
        queue.add(start)
        while (queue.isNotEmpty()) {
            val vertex = queue.poll()
            visited.add(vertex)
            val distanceToVertex = dist[vertex]!!
            neighbors(vertex).filter { it !in visited }.forEach { neighbor ->
                val distanceThroughVertex = distanceToVertex + edge(vertex, neighbor)!!.weight
                if (distanceThroughVertex < dist[neighbor]!!) {
                    pred[neighbor] = vertex
                    dist[neighbor] = distanceThroughVertex
                    queue.add(neighbor)
                }
            }
        }
        return ShortestPaths(start, dist, pred)
    }

    fun dfs(start: V, end: V): List<V> {
        return search(start, end) { list, element -> list.add(0, element) }
    }
    fun bfs(start: V, end: V): List<V> {
        return search(start, end) { list, element -> list.add(element) }
    }

    private fun search(start: V, end: V, add: (MutableList<List<V>>, List<V>) -> Unit): List<V> {
        val visited = mutableSetOf<V>()
        visited += start
        val list = mutableListOf<List<V>>()
        add(list, listOf(start))

        while (list.isNotEmpty()) {
            val current = list.removeFirst()
            val terminus = current.last()
            if (terminus == end) {
                return current
            }
            neighbors(terminus).filter { it !in visited }.forEach { n ->
                visited += n
                add(list, current + n)
            }
        }
        return listOf()
    }

}