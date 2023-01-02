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

package adventofcode.util.collection

class MutableBag<E> : AbstractMutableCollection<E>() {

    var currSize = 0
    val map = mutableMapOf<E, Int>()

    fun count(element:E) = map[element] ?: 0
    override fun add(element: E): Boolean {
        map.merge(element, 1) { prev, inc -> prev + inc }
        currSize++
        return true
    }

    override val size: Int
        get() = currSize

    override fun iterator(): MutableIterator<E> {
        return MutableBagIterator(map.iterator())
    }

    private inner class MutableBagIterator(val iterator:MutableIterator<MutableMap.MutableEntry<E,Int>>): MutableIterator<E> {
        var current: MutableMap.MutableEntry<E,Int>? = null
        var currentRepeats: Int = 0


        override fun hasNext(): Boolean {
            return currentRepeats > 0 || iterator.hasNext()
        }

        override fun next(): E {
            if(currentRepeats == 0) {
                current = iterator.next()
                currentRepeats = current!!.value
            }
            currentRepeats--
            return current!!.key
        }

        override fun remove() {
            current!!.setValue(current!!.value-1)
        }
    }
}