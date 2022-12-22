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

package adventofcode.util.search.dp


interface DynamicProgrammingState<V : Comparable<V>> {
    fun isTerminal(): Boolean
    fun subProblems(): List<DynamicProgrammingState<V>>
    fun value(): V

}

fun <V : Comparable<V>> maximum(
    state: DynamicProgrammingState<V>,
    cache: MutableMap<DynamicProgrammingState<V>, V>,
    add: (V, V) -> V
): V {
    return optimum(state, add, cache) { it.max() }
}

fun <V : Comparable<V>> maximum(state: DynamicProgrammingState<V>, add: (V, V) -> V): V {
    return optimum(state, add) { it.max() }
}

fun maximum(state: DynamicProgrammingState<Int>): Int = maximum(state) { l, r -> l + r }
fun maximum(state: DynamicProgrammingState<Int>, cache: MutableMap<DynamicProgrammingState<Int>, Int>): Int = maximum(state, cache) { l, r -> l + r }
fun maximum(state: DynamicProgrammingState<Long>): Long = maximum(state) { l, r -> l + r }
fun maximum(state: DynamicProgrammingState<Long>, cache: MutableMap<DynamicProgrammingState<Long>, Long>): Long = maximum(state, cache) { l, r -> l + r }
fun maximum(state: DynamicProgrammingState<Double>): Double = maximum(state) { l, r -> l + r }
fun maximum(state: DynamicProgrammingState<Double>, cache: MutableMap<DynamicProgrammingState<Double>, Double>): Double = maximum(state, cache) { l, r -> l + r }

fun <V : Comparable<V>> minimum(state: DynamicProgrammingState<V>, add: (V, V) -> V): V {
    return optimum(state, add) { it.min() }
}

fun <V : Comparable<V>> minimum(
    state: DynamicProgrammingState<V>,
    cache: MutableMap<DynamicProgrammingState<V>, V>,
    add: (V, V) -> V
): V {
    return optimum(state, add, cache) { it.min() }
}

fun minimum(state: DynamicProgrammingState<Int>): Int = minimum(state) { l, r -> l + r }

fun minimum(state: DynamicProgrammingState<Long>): Long = minimum(state) { l, r -> l + r }

fun minimum(state: DynamicProgrammingState<Double>): Double = minimum(state) { l, r -> l + r }


private fun <V : Comparable<V>> optimum(
    state: DynamicProgrammingState<V>,
    add: (V, V) -> V,
    optimumValueOf: (List<V>) -> V
): V {
    return optimum(state, add, mutableMapOf(), optimumValueOf)
}

private fun <V : Comparable<V>> optimum(
    state: DynamicProgrammingState<V>,
    add: (V, V) -> V,
    cache: MutableMap<DynamicProgrammingState<V>, V>,
    optimumValueOf: (List<V>) -> V
): V {
    if (state.isTerminal()) {
        return state.value()
    }
    when (val v = cache[state]) {
        null -> {}
        else -> return v
    }
    val maximum =
        add(state.value(), optimumValueOf(state.subProblems().map { optimum(it, add, cache, optimumValueOf) }))
    cache[state] = maximum
    return maximum
}