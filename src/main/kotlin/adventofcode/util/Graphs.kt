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
        return search(start, end, neighbors, QueueBuffer())
    }

    fun <T> dfs(start: T, end: T, neighbors: (T) -> List<T>): List<T> {
        return search(start, end, neighbors, StackBuffer())
    }


    private fun <T> search(start: T, end: T, neighbors: (T) -> List<T>, buffer: Buffer<List<T>>): List<T> {
        val visited = mutableSetOf<T>()
        visited += start
        buffer.add(listOf(start))
        while (buffer.isNotEmpty()) {
            val current = buffer.remove()
            val terminus = current.last()
            if (terminus == end) {
                return current
            }
            neighbors(terminus).filter { it !in visited }.forEach { n ->
                visited += n
                buffer.add(current + n)
            }
        }
        return listOf()
    }

    private interface Buffer<T> {
        fun remove(): T
        fun add(element: T)

        fun isNotEmpty(): Boolean
    }

    class StackBuffer<T>: Buffer<T> {
        private val stack = mutableListOf<T>()
        override fun remove(): T {
            return stack.removeFirst()
        }

        override fun add(element: T) {
            stack.add(0, element)
        }

        override fun isNotEmpty(): Boolean {
            return stack.isNotEmpty()
        }
    }
    class QueueBuffer<T>: Buffer<T> {
        private val queue = mutableListOf<T>()
        override fun remove(): T {
            return queue.removeFirst()
        }

        override fun add(element: T) {
            queue.add(element)
        }

        override fun isNotEmpty(): Boolean {
            return queue.isNotEmpty()
        }
    }

}