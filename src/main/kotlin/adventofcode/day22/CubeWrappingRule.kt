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

import adventofcode.day22.EdgeType.*
import adventofcode.util.geom.plane.Point2D
import adventofcode.util.grid.Grid
import adventofcode.util.grid.GridView
import adventofcode.util.grid.ListGrid
import adventofcode.util.grid.subGrid

class CubeWrappingRule(map: Grid<Char>, sideLength: Int) : WrappingRule {
    private val faces = mutableMapOf<FaceType, Face>()
    private val destinations = mutableMapOf<Pair<FaceType, EdgeType>, FaceType>()
    private val borderTypes = mutableMapOf<Pair<FaceType, FaceType>, EdgeType>()
    private val max = sideLength - 1
    init {
        val multiGrid = createMultiGrid(map, sideLength)
        val (frontFaceLoc, frontFaceGrid) = multiGrid.coordinatesWithValues().first { it.second.isNotEmpty() }
        val frontFace = Face(frontFaceGrid, FaceType.FRONT)
        collectFaces(multiGrid, frontFaceLoc, frontFace, FaceType.TOP, UP)
    }

    override fun wrap(src: Pose): Pose {
        val destFace = destinations[Pair(src.face.type, src.facing)]!!
        val destEdge = borderTypes[Pair(destFace, src.face.type)]!!
        val dest = faces[destFace]!!
        val position = translatePoint(src.facing, destEdge, src.position)
        return Pose(dest, position, destEdge.opposite())
    }

    private fun addFace(face: Face) {
        faces[face.type] = face
    }

    operator fun get(faceType: FaceType): Face = faces[faceType]!!

    private fun addFaceTransition(srcFaceType: FaceType, borderType: EdgeType, destFaceType: FaceType) {
        destinations[Pair(srcFaceType, borderType)] = destFaceType
        borderTypes[Pair(srcFaceType, destFaceType)] = borderType
    }

    private fun translatePoint(srcEdge: EdgeType, destEdge: EdgeType, p: Point2D): Point2D {
        return when (srcEdge) {
            UP -> when (destEdge) {
                UP -> Point2D(invert(p.x), 0)
                DOWN -> Point2D(p.x, max)
                LEFT -> Point2D(0, p.x)
                RIGHT -> Point2D(max, invert(p.x))
            }

            DOWN -> when (destEdge) {
                UP -> Point2D(p.x, 0)
                DOWN -> Point2D(invert(p.x), max)
                LEFT -> Point2D(0, invert(p.x))
                RIGHT -> Point2D(max, p.x)
            }

            LEFT -> when (destEdge) {
                UP -> Point2D(p.y, 0)
                DOWN -> Point2D(invert(p.y), max)
                LEFT -> Point2D(0, invert(p.y))
                RIGHT -> Point2D(max, p.y)
            }

            RIGHT -> when (destEdge) {
                UP -> Point2D(invert(p.y), 0)
                DOWN -> Point2D(p.y, max)
                LEFT -> Point2D(0, p.y)
                RIGHT -> Point2D(max, invert(p.y))
            }
        }
    }

    private fun invert(coord:Int): Int = max - coord


    private fun Grid<Char>.isNotEmpty() = get(0, 0) != ' '

    private fun Grid<GridView<Char>>.doWithNeighbor(neighborCoordinate: Point2D, consumer: (GridView<Char>) -> Unit) {
        if (contains(neighborCoordinate)) {
            val neighbor = get(neighborCoordinate)
            if (neighbor.isNotEmpty()) {
                consumer(neighbor)
            }
        }
    }

    private fun collectFaces(
        multiGrid: Grid<GridView<Char>>,
        loc: Point2D,
        srcFace: Face,
        anchorFaceType: FaceType,
        anchorBorderType: EdgeType,
    ) {
        addFace(srcFace)
        val neighborFaceTypes = srcFace.type.clockwiseNeighbors()
        var currentBorderType = anchorBorderType
        var currentIndex = neighborFaceTypes.indexOf(anchorFaceType)

        repeat(4) {
            val neighborLoc = loc + currentBorderType
            addFaceTransition(srcFace.type, currentBorderType, neighborFaceTypes[currentIndex])
            multiGrid.doWithNeighbor(neighborLoc) { grid ->
                val destFaceType = neighborFaceTypes[currentIndex]
                if (destFaceType !in faces) {
                    val destFace = Face(grid, neighborFaceTypes[currentIndex])
                    collectFaces(multiGrid, neighborLoc, destFace, srcFace.type, currentBorderType.opposite())
                }
            }
            currentIndex = (currentIndex + 1) % neighborFaceTypes.size
            currentBorderType++
        }
    }

    private fun createMultiGrid(map: Grid<Char>, sideLength: Int): Grid<GridView<Char>> {
        val xGrids = map.width() / sideLength
        val yGrids = map.height() / sideLength
        return ListGrid(
            xGrids,
            (0 until yGrids).map { y ->
                (0 until xGrids).map { x ->
                    map.subGrid(
                        x * sideLength,
                        y * sideLength,
                        sideLength,
                        sideLength
                    )
                }
            }
                .flatten().toList()
        )

    }
}