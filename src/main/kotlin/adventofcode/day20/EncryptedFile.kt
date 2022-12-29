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

package adventofcode.day20

class EncryptedFile(numbers: List<Long>, decryptionKey: Int = 1) {

    private val nodes: List<EncryptedFileNode>
    private val zeroNode: EncryptedFileNode

    init {
        nodes = numbers.map { EncryptedFileNode(it * decryptionKey) }
        nodes.zipWithNext().forEach { it.first.link(it.second) }
        nodes.last().link(nodes.first())
        this.zeroNode = nodes.first { it.value == 0L }
    }

    fun groveCoordinates() = valuesAfterZero().take(3).toList()

    private fun valuesAfterZero(step: Int = 1000) = sequence {
        var curr = zeroNode
        var count = 0
        while (true) {
            count++
            curr = curr.next!!
            if (count % step == 0) {
                yield(curr.value)
            }
        }
    }

    fun mix() {
        nodes.forEach { mix(it) }
    }

    private fun mix(node: EncryptedFileNode) {
        if (node == zeroNode) {
            return
        }
        node.prev!!.link(node.next!!)
        val hops = node.value.mod(nodes.size - 1)
        var curr = node
        repeat(hops) {
            curr = curr.next!!
        }
        node.link(curr.next!!)
        curr.link(node)
    }

    class EncryptedFileNode(val value: Long) {
        var next: EncryptedFileNode? = null
        var prev: EncryptedFileNode? = null

        fun link(next: EncryptedFileNode) {
            this.next = next
            next.prev = this
        }

        override fun toString(): String {
            return "$value"
        }
    }
}