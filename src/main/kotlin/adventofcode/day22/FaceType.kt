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

enum class FaceType {
    FRONT {
        override fun clockwiseNeighbors() = listOf(TOP, RIGHT, BOTTOM, LEFT)
    },
    BACK {
        override fun clockwiseNeighbors() = listOf(TOP, LEFT, BOTTOM, RIGHT)
    },
    LEFT {
        override fun clockwiseNeighbors() = listOf(TOP, FRONT, BOTTOM, BACK)
    },
    RIGHT {
        override fun clockwiseNeighbors() = listOf(TOP, BACK, BOTTOM, FRONT)
    },
    TOP {
        override fun clockwiseNeighbors() = listOf(BACK, RIGHT, FRONT, LEFT)
    },
    BOTTOM {
        override fun clockwiseNeighbors() = listOf(FRONT, RIGHT, BACK, LEFT)
    };

    abstract fun clockwiseNeighbors(): List<FaceType>
}