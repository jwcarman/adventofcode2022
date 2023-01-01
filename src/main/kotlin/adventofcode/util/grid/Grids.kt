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

package adventofcode.util.grid

import adventofcode.util.geom.plane.Point2D

fun <T : Any> Grid<T>.transpose(): GridView<T> = TransposedGrid(this)
private class TransposedGrid<T : Any>(original: Grid<T>) : FlippedGrid<T>(original) {
    override fun underlyingPoint(localX: Int, localY: Int) = Point2D(localY, localX)
    override fun rowAt(y: Int) = delegate.columnAt(y)
    override fun columnAt(x: Int) = delegate.rowAt(x)
}

fun <T : Any> Grid<T>.rotateRight(): GridView<T> = RotateRightGrid(this)
private class RotateRightGrid<T : Any>(original: Grid<T>) : FlippedGrid<T>(original) {
    override fun underlyingPoint(localX: Int, localY: Int) = Point2D(localY, width() - 1 - localX)
}

fun <T : Any> Grid<T>.rotateLeft(): GridView<T> = RotateLeftGrid(this)
private class RotateLeftGrid<T : Any>(original: Grid<T>) : FlippedGrid<T>(original) {
    override fun underlyingPoint(localX: Int, localY: Int) = Point2D(height() - 1 - localY, localX)
}

private abstract class DelegatedGrid<T : Any>(protected val delegate: Grid<T>) : AbstractGrid<T>(), GridView<T> {
    override fun getImpl(x: Int, y: Int): T = delegate[underlyingPoint(x, y)]
}

private abstract class FlippedGrid<T : Any>(original: Grid<T>) : DelegatedGrid<T>(original) {
    override fun width() = delegate.height()
    override fun height() = delegate.width()
}

fun <T : Any> Grid<T>.subGrid(origin: Point2D, width: Int, height: Int): GridView<T> =
    subGrid(origin.x, origin.y, width, height)

fun <T : Any> Grid<T>.subGrid(xOffset: Int, yOffset: Int, width: Int, height: Int): GridView<T> =
    SubGrid(this, xOffset, yOffset, width, height)

interface GridView<T : Any> : Grid<T> {
    fun underlyingPoint(localX: Int, localY: Int): Point2D
}

fun <T : Any> Grid<T>.nullView(): GridView<T> = NullView(this)

private class NullView<T : Any>(delegate: Grid<T>) : DelegatedGrid<T>(delegate) {
    override fun width() = delegate.width()
    override fun height() = delegate.height()
    override fun underlyingPoint(localX: Int, localY: Int) = Point2D(localX, localY)
}

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

    override fun underlyingPoint(localX: Int, localY: Int) = Point2D(localX + xOffset, localY + yOffset)

    override fun width() = width

    override fun height() = height

    override fun toString(): String {
        return "SubGrid($xOffset,$yOffset,$width,$height)"
    }
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