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


/**
 * A state model for dynamic programming
 */
interface DynamicProgrammingState<S:DynamicProgrammingState<S,V>,V : Comparable<V>> {
    /**
     * Is this state a terminal state (has no children)?
     */
    fun isTerminal(): Boolean

    /**
     * Generate child states for this state.
     */
    fun children(): List<S>

    /**
     * Calculate the value of this state (not including children)
     */
    fun value(): V

}

fun <S:DynamicProgrammingState<S,V>,V : Comparable<V>> maximum(
    state: S,
    cache: MutableMap<S, V>,
    add: (V, V) -> V
): V {
    return optimum(state, add, cache) { it.max() }
}

fun <S:DynamicProgrammingState<S,V>,V : Comparable<V>> maximum(state: S, add: (V, V) -> V): V {
    return optimum(state, add) { it.max() }
}

fun <S:DynamicProgrammingState<S,Int>> maximum(state: S): Int = maximum(state) { l, r -> l + r }
fun <S:DynamicProgrammingState<S,Int>> maximum(state: S, cache: MutableMap<S, Int>): Int = maximum(state, cache) { l, r -> l + r }
fun <S:DynamicProgrammingState<S,Long>> maximum(state: S): Long = maximum(state) { l, r -> l + r }
fun <S:DynamicProgrammingState<S,Long>> maximum(state: S, cache: MutableMap<S, Long>): Long = maximum(state, cache) { l, r -> l + r }
fun <S:DynamicProgrammingState<S,Double>> maximum(state: S): Double = maximum(state) { l, r -> l + r }
fun <S:DynamicProgrammingState<S,Double>> maximum(state: S, cache: MutableMap<S, Double>): Double = maximum(state, cache) { l, r -> l + r }

fun <S:DynamicProgrammingState<S,V>,V : Comparable<V>> minimum(state: S, add: (V, V) -> V): V {
    return optimum(state, add) { it.min() }
}

fun <S:DynamicProgrammingState<S,V>,V : Comparable<V>> minimum(
    state: S,
    cache: MutableMap<S, V>,
    add: (V, V) -> V
): V {
    return optimum(state, add, cache) { it.min() }
}

fun <S:DynamicProgrammingState<S,Int>> minimum(state: S): Int = minimum(state) { l, r -> l + r }

fun <S:DynamicProgrammingState<S,Long>> minimum(state: S): Long = minimum(state) { l, r -> l + r }

fun <S:DynamicProgrammingState<S,Double>> minimum(state: S): Double = minimum(state) { l, r -> l + r }


private fun <S:DynamicProgrammingState<S,V>,V : Comparable<V>> optimum(
    state: S,
    add: (V, V) -> V,
    optimumValueOf: (List<V>) -> V
): V {
    return optimum(state, add, mutableMapOf(), optimumValueOf)
}

private fun <S:DynamicProgrammingState<S,V>,V : Comparable<V>> optimum(
    state: S,
    add: (V, V) -> V,
    cache: MutableMap<S, V>,
    optimumValueOf: (List<V>) -> V
): V {
    if (state.isTerminal()) {
        return state.value()
    }
    when (val v = cache[state]) {
        null -> {}
        else -> return v
    }
    val optimum =
        add(state.value(), optimumValueOf(state.children().map { optimum(it, add, cache, optimumValueOf) }))
    cache[state] = optimum
    return optimum
}