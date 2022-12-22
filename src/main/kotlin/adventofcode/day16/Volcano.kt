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

package adventofcode.day16

import adventofcode.day16.Valve.Companion.parseGraph
import adventofcode.util.graph.Graph

class Volcano {

    private val tunnels: Map<Valve, List<Tunnel>>
    private val numberOfFlowValves: Int

    private val valves: List<Valve>
    private val indexOf: Map<Valve, Int>

    private constructor(graph: Graph<Valve, Unit>) {
        val tmpTunnels = mutableMapOf<Valve, List<Tunnel>>()
        val start = Valve.start()
        val flowValves = graph.flowValves()
        populateTunnels(graph, start, flowValves, tmpTunnels)
        flowValves.forEach { from -> populateTunnels(graph, from, flowValves, tmpTunnels) }
        this.tunnels = tmpTunnels
        this.numberOfFlowValves = tunnels.size - 1
        this.valves = tunnels.keys.sortedByDescending { it.flowRate }
        this.indexOf = valves.withIndex().associate { it.value to it.index }
    }

    fun flowValveSubsetPairs() = sequence {
        (0 until (1 shl valves.size - 1)).forEach { mask ->
            val inverse = mask.inv() and (1 shl valves.size-1).inv()
            yield(Pair(
                valves
                    .filterIndexed { index, _ -> mask and (1 shl index) != 0 }
                    .toSet(),
                valves
                    .filterIndexed { index, _ -> inverse and (1 shl index) != 0 }
                    .toSet()
            ))
        }
    }

    fun indexOf(valve: Valve): Int = indexOf[valve]!!

    fun valveAtIndex(index: Int): Valve = valves[index]!!

    fun valves() = tunnels.keys.sortedBy { it.flowRate }

    fun tunnelsFrom(from: Valve) = tunnels.getOrDefault(from, listOf())

    fun tunnelFrom(from: Valve, to: Valve): Tunnel = tunnelsFrom(from).first { it.to == to }

    private fun populateTunnels(
        graph: Graph<Valve, Unit>,
        from: Valve,
        flowValves: List<Valve>,
        tunnels: MutableMap<Valve, List<Tunnel>>
    ) {
        val shortestPaths = graph.shortestPaths(from)
        tunnels[from] = flowValves
            .filter { it != from }
            .map { Tunnel(from, it, shortestPaths.distanceTo(it).toInt()) }
    }


    companion object {

        fun parse(input: String): Volcano {
            return Volcano(parseGraph(input))
        }
    }
}