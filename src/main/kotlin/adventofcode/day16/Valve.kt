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

import adventofcode.util.graph.Graph
import adventofcode.util.graph.SparseGraph
import adventofcode.util.removeAll

fun Graph<Valve, Unit>.flowValves() = vertices().filter { it.flowRate > 0 }
data class Valve(val label: String, val flowRate: Int) {

    companion object {
        fun start() = Valve("AA", 0)

        fun parseGraph(input: String): Graph<Valve, Unit> {
            val graph = SparseGraph<Valve, Unit>()
            val valvesMap = mutableMapOf<String, Valve>()
            val successorsMap = mutableMapOf<Valve, List<String>>()

            input.removeAll("Valve ", "has flow rate=", "; tunnels lead to valves", "; tunnel leads to valve")
                .replace(", ", ",")
                .lines()
                .forEach { line ->
                    val splits = line.split(' ')
                    val label = splits[0]
                    val flowRate = splits[1].toInt()
                    val successorLabels = splits[2].split(',').toList()
                    if (flowRate > 0) {
                        val passThrough = Valve(label, 0)
                        valvesMap[label] = passThrough
                        graph.addVertex(passThrough)
                        successorsMap[passThrough] = successorLabels
                        val openValve = Valve(label, flowRate)
                        successorsMap[openValve] = successorLabels
                        graph.addVertex(openValve)
                        graph.addEdge(passThrough, openValve, Unit, 1.0)
                    } else {
                        val valve = Valve(label, 0)
                        valvesMap[label] = valve
                        successorsMap[valve] = successorLabels
                        graph.addVertex(valve)
                    }
                }

            successorsMap.forEach { (valve, successorLabels) ->
                successorLabels.forEach { successorLabel ->
                    val successor = valvesMap[successorLabel]!!
                    graph.addEdge(valve, successor, Unit, 1.0)
                }
            }
            return graph
        }
    }
}

fun List<Valve>.removeFlowValves(vararg valves: Valve): List<Valve> {
    return valves.filter { it.flowRate > 0 }.fold(this) { list, v -> list - v }
}
