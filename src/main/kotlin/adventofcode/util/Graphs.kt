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

package adventofcode.util

import kotlin.math.min

object Graphs {

    fun <T> shortestPath(
        vertices: Set<T>,
        neighbors: (T) -> List<T>,
        distanceBetween: (T, T) -> Int,
        start: T,
        end: T
    ): Int {
        val visited = mutableSetOf<T>()
        val dist = mutableMapOf<T, Int>()
        dist[start] = 0
        val nVertices = vertices.size
        while (visited.size < nVertices) {
            val vertex = dist.keys.first { it !in visited }
            visited.add(vertex)
            val n = neighbors(vertex)
            val distanceToVertex = dist[vertex]!!
            n.forEach { neighbor ->
                dist[neighbor] = min(
                    dist.getOrDefault(neighbor, Int.MAX_VALUE),
                    distanceBetween(vertex, neighbor) + distanceToVertex
                )
            }
        }
        return dist[end]!!
    }

    fun <T> bfs(start: T, end: T, neighbors: (T) -> List<T>): List<T> {
        val queue = mutableListOf<List<T>>()
        val visited = mutableSetOf<T>()
        visited += start
        queue += listOf(start)
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            val terminus = current.last()
            if (terminus == end) {
                return current
            }
            neighbors(terminus).filter { it !in visited }.forEach { n ->
                visited += n
                queue += current + n
            }
        }
        return listOf()
    }

    fun <T> dfs(start: T, end: T, neighbors: (T) -> List<T>): List<T> {
        val stack = mutableListOf<List<T>>()
        val visited = mutableSetOf<T>()
        visited += start
        stack.add(0, listOf(start))
        while (stack.isNotEmpty()) {
            val current = stack.removeFirst()
            val terminus = current.last()
            if (terminus == end) {
                return current
            }
            neighbors(terminus).filter { it !in visited }.forEach { n ->
                visited += n
                stack.add(0, current + n)
            }
        }
        return listOf()
    }


}