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

private fun compareDoubles(left: Double, right: Double): Int {
    if (left < right) {
        return -1
    }
    if (right < left) {
        return 1
    }
    return 0
}

object Graphs {
    fun <V> shortestPaths(
        start: V,
        vertices: Set<V>,
        neighbors: (V) -> List<V>,
        weight: (V, V) -> Double
    ): ShortestPaths<V> {
        val pred = mutableMapOf<V, V>()
        val dist = mutableMapOf<V, Double>()
        val visited = mutableSetOf<V>()
        vertices.forEach { vertex -> dist[vertex] = Double.POSITIVE_INFINITY }
        dist[start] = 0.0
        val queue = PriorityQueue { l: V, r: V -> compareDoubles(dist[l]!!, dist[r]!!) }
        queue.add(start)
        while (queue.isNotEmpty()) {
            val vertex = queue.poll()
            visited.add(vertex)
            val distanceToVertex = dist[vertex]!!
            neighbors(vertex).filter { it !in visited }.forEach { neighbor ->
                val distanceThroughVertex = distanceToVertex + weight(vertex, neighbor)
                if (distanceThroughVertex < dist[neighbor]!!) {
                    pred[neighbor] = vertex
                    dist[neighbor] = distanceThroughVertex
                    queue.add(neighbor)
                }
            }
        }
        return ShortestPaths(start, dist, pred)
    }

    private data class Reachable<V>(val steps: Int, val vertex: V)

    fun <V> reachable(start: V, maxSteps: Int = Int.MAX_VALUE, neighbors: (V) -> List<V>): Set<V> {
        val visited = mutableSetOf<V>()
        val queue = mutableListOf(Reachable(0, start))
        while (queue.isNotEmpty()) {
            val reachable = queue.removeLast()
            visited += reachable.vertex
            if (reachable.steps < maxSteps) {
                val ns = neighbors(reachable.vertex)
                    .filter { it !in visited }
                    .map { Reachable(reachable.steps + 1, it) }
                queue.addAll(ns)
            }
        }
        return visited
    }

    fun <V> dfs(start: V, end: V, neighbors: (V) -> List<V>): List<V> {
        return search(start, end, neighbors) { list, element -> list.add(0, element) }
    }

    fun <V> bfs(start: V, end: V, neighbors: (V) -> List<V>): List<V> {
        return search(start, end, neighbors) { list, element -> list.add(element) }
    }

    private fun <V> search(
        start: V,
        end: V,
        neighbors: (V) -> List<V>,
        add: (MutableList<List<V>>, List<V>) -> Unit
    ): List<V> {
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
