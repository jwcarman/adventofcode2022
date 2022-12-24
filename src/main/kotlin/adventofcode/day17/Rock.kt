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

package adventofcode.day17

typealias Rock = List<Int>

val cross: Rock = listOf(
    0b0001000,
    0b0011100,
    0b0001000,
)

val corner: Rock = listOf(
    0b0000100,
    0b0000100,
    0b0011100,
)

val hline: Rock = listOf(
    0b0011110
)

val vline: Rock = listOf(
    0b0010000,
    0b0010000,
    0b0010000,
    0b0010000,
)

val square: Rock = listOf(
    0b0011000,
    0b0011000
)

val rocks: List<Rock> = listOf(hline, cross, corner, vline, square)

fun Rock.push(jet:Char): Rock {
    return if(jet == '>') {
        pushRight()
    } else {
        pushLeft()
    }
}
fun Rock.pushRight(): List<Int> {
    if (canPushRight()) {
        return map { it ushr 1 }
    }
    return this
}

fun Rock.pushLeft(): List<Int> {
    if (canPushLeft()) {
        return map { it shl 1 }
    }
    return this
}
fun Rock.canPushRight() = none { it.isAgainstRightWall() }

fun Rock.canPushLeft() = none { it.isAgainstLeftWall() }

fun Int.isAgainstLeftWall() = (this and 64) != 0

fun Int.isAgainstRightWall() = (this and 1) != 0

fun Int.collidesWith(that: Int) = (this and that) != 0

