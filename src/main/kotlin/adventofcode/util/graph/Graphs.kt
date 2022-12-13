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

import java.util.PriorityQueue

interface ShortestPaths<T> {
    fun pathTo(sink: T): List<T>
    fun distanceTo(sink: T): Int

}

object Graphs {


    fun <T> shortestPaths(vertices: Set<T>, neighbors: (T) -> List<T>, weight: (T, T) -> Int, start: T): ShortestPaths<T> {
        val pred = mutableMapOf<T,T>()
        val dist = mutableMapOf<T, Int>()
        val visited = mutableSetOf<T>()
        vertices.forEach { vertex -> dist[vertex] = Int.MAX_VALUE }
        dist[start] = 0
        val queue = PriorityQueue { l: T, r: T -> dist[l]!! - dist[r]!! }
        queue.add(start)
        while(queue.isNotEmpty()) {
            val vertex = queue.poll()
            val distanceToVertex = dist[vertex]!!
            if(distanceToVertex < Int.MAX_VALUE) {
                neighbors(vertex).filter { it !in visited }.forEach { neighbor ->
                    val distanceThroughVertex = distanceToVertex + weight(vertex, neighbor)
                    if(distanceThroughVertex < dist[neighbor]!!) {
                        pred[neighbor] = vertex
                        dist[neighbor] = distanceThroughVertex
                        queue.add(neighbor)
                    }
                }
            }
            visited.add(vertex)
        }
        return DefaultShortestPath(dist, pred)
    }

    private class DefaultShortestPath<T>(private val dist:Map<T,Int>, private val pred: Map<T,T>): ShortestPaths<T> {

        override fun pathTo(sink: T): List<T> {
            val path = mutableListOf(sink)
            while(pred.containsKey(path.first())) {
                path.add(0, pred[path.first()]!!)
            }
            return path
        }

        override fun distanceTo(sink: T): Int {
            return dist[sink]!!
        }
    }

    fun <T> bfs(start: T, end: T, neighbors: (T) -> List<T>): List<T> {
        return search(start, end, neighbors) { list, element -> list.add(element) }
    }

    fun <T> dfs(start: T, end: T, neighbors: (T) -> List<T>): List<T> {
        return search(start, end, neighbors) { list, element -> list.add(0, element) }
    }


    private fun <T> search(
        start: T,
        end: T,
        neighbors: (T) -> List<T>,
        add: (MutableList<List<T>>, List<T>) -> Unit
    ): List<T> {
        val visited = mutableSetOf<T>()
        visited += start
        val list = mutableListOf<List<T>>()
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