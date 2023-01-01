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

package adventofcode.day22

import adventofcode.util.geom.plane.Point2D

operator fun Point2D.plus(direction: EdgeType) = this.translate(direction.dx(), direction.dy())

enum class EdgeType {
    UP {
        override fun value() = 3
        override fun dx() = 0
        override fun dy() = -1
        override fun turnRight() = RIGHT
        override fun turnLeft() = LEFT
        override fun opposite() = DOWN
    },
    DOWN {
        override fun value() = 1
        override fun dx() = 0
        override fun dy() = 1
        override fun turnRight() = LEFT
        override fun turnLeft() = RIGHT
        override fun opposite() = UP
    },
    LEFT {
        override fun value() = 2
        override fun dx() = -1
        override fun dy() = 0
        override fun turnRight() = UP
        override fun turnLeft() = DOWN
        override fun opposite() = RIGHT
    },
    RIGHT {
        override fun value() = 0
        override fun dx() = 1
        override fun dy() = 0
        override fun turnRight() = DOWN
        override fun turnLeft() = UP
        override fun opposite() = LEFT
    };

    abstract fun value(): Int

    abstract fun dx(): Int
    abstract fun dy(): Int

    abstract fun turnRight(): EdgeType
    abstract fun turnLeft(): EdgeType

    abstract fun opposite(): EdgeType

    operator fun inc() = turnRight()
    operator fun dec() = turnLeft()
}