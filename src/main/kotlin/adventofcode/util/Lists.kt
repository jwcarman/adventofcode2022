/*
 * Copyright (c) 2023 James Carman
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

fun <T> List<T>.tail() = drop(1)

fun <T> List<T>.head() = first()

inline fun <T> List<T>.takeWhileInclusive(predicate: (T) -> Boolean): List<T> {
    var shouldContinue = true
    return takeWhile {
        val result = shouldContinue
        shouldContinue = predicate(it)
        result
    }
}

operator fun <T> List<T>.times(other:List<T>) = sequence {
    forEach { left->
        other.forEach { right -> yield(Pair(left,right)) }
    }
}

fun <T> List<T>.repeat() = sequence {
    var index = 0
    while(true) {
        yield(get(index))
        index = (index + 1) % size
    }
}

class Repeater<T>(private val values:List<T>) {
    private var ndx = 0
    fun next(): T {
        val value = values[ndx]
        ndx = (ndx + 1) % values.size
        return value
    }
}