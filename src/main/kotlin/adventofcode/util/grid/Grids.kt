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

package adventofcode.util.grid

import adventofcode.util.geom.Point2D

fun <T : Any> Grid<T>.transpose(): Grid<T> = TransposedGrid(this)
private class TransposedGrid<T : Any>(original: Grid<T>) : FlippedGrid<T>(original) {
    override fun getImpl(x: Int, y: Int) = original[y, x]
    override fun rowAt(y: Int) = original.columnAt(y)
    override fun columnAt(x: Int) = original.rowAt(x)
}

fun <T : Any> Grid<T>.rotateRight(): Grid<T> = RotateRightGrid(this)
private class RotateRightGrid<T : Any>(original: Grid<T>) : FlippedGrid<T>(original) {
    override fun getImpl(x: Int, y: Int) = original[y, width() - 1 - x]
}

fun <T : Any> Grid<T>.rotateLeft(): Grid<T> = RotateLeftGrid(this)
private class RotateLeftGrid<T : Any>(original: Grid<T>) : FlippedGrid<T>(original) {
    override fun getImpl(x: Int, y: Int) = original[height() - 1 - y, x]
}

private abstract class DelegatedGrid<T : Any>(protected val original: Grid<T>) : AbstractGrid<T>()
private abstract class FlippedGrid<T : Any>(original: Grid<T>) : DelegatedGrid<T>(original) {
    override fun width() = original.height()
    override fun height() = original.width()
}

fun <T : Any> Grid<T>.subGrid(origin: Point2D, width: Int, height: Int): Grid<T> =
    subGrid(origin.x, origin.y, width, height)

fun <T : Any> Grid<T>.subGrid(xOffset: Int, yOffset: Int, width: Int, height: Int): Grid<T> =
    SubGrid(this, xOffset, yOffset, width, height)

private class SubGrid<T : Any>(
    original: Grid<T>,
    private val xOffset: Int,
    private val yOffset: Int,
    private val width: Int,
    private val height: Int
) :
    DelegatedGrid<T>(original) {

    init {
        verifyPoint(original, xOffset, yOffset)
        verifyPoint(original, xOffset + width - 1, yOffset + height - 1)
    }

    override fun getImpl(x: Int, y: Int): T {
        return original[x + xOffset, y + yOffset]
    }

    override fun width() = width

    override fun height() = height
}

fun <T : Any> verifyPoint(grid: Grid<T>, x: Int, y: Int) {
    val xBounds = grid.xRange()
    if (x !in xBounds) {
        throw IllegalArgumentException("Values for x must be in $xBounds.")
    }
    val yBounds = grid.yRange()
    if (y !in yBounds) {
        throw IllegalArgumentException("Values for y must be in $yBounds.")
    }
}