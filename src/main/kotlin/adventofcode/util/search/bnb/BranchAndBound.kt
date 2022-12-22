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

package adventofcode.util.search.bnb

object BranchAndBound {
    fun <V : Comparable<V>> maximumSearch(root: MaxSearchNode<V>, initialLowerBound: V): MaxSearchNode<V> {
        val queue = mutableListOf<MaxSearchNode<V>>()
        queue.add(root)
        var lowerBound = initialLowerBound
        var maximumNode: MaxSearchNode<V>? = null
        var leavesVisited = 0
        while (queue.isNotEmpty()) {
            val candidate = queue.removeLast()
            if (candidate.upperBound() > lowerBound) {
                if (candidate.isLeaf()) {
                    val nodeValue = candidate.value()
                    if (nodeValue > lowerBound) {
                        lowerBound = nodeValue
                        maximumNode = candidate
                    }
                    leavesVisited++
                } else {
                    queue.addAll(candidate
                        .branches()
                        .filter { it.upperBound() > lowerBound }
                        .sortedBy { it.upperBound() })
                }
            }
        }
        println("Found maximum after visiting $leavesVisited leaves.")
        return maximumNode!!
    }
}