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

package adventofcode.day22

enum class Facing {
    UP {
        override fun value() = 3L
        override fun dx() = 0
        override fun dy() = -1
        override fun turnRight() = RIGHT
        override fun turnLeft() = LEFT
    },
    DOWN {
        override fun value() = 1L
        override fun dx() = 0
        override fun dy() = 1
        override fun turnRight() = LEFT
        override fun turnLeft() = RIGHT
    },
    LEFT {
        override fun value() = 2L
        override fun dx() = -1
        override fun dy() = 0
        override fun turnRight() = UP
        override fun turnLeft() = DOWN
    },
    RIGHT {
        override fun value() = 0L
        override fun dx() = 1
        override fun dy() = 0
        override fun turnRight() = DOWN
        override fun turnLeft() = UP
    };

    abstract fun value(): Long

    abstract fun dx(): Int
    abstract fun dy(): Int

    abstract fun turnRight(): Facing
    abstract fun turnLeft(): Facing
}