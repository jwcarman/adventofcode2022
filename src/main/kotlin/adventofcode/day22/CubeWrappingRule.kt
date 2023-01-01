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

class CubeWrappingRule : WrappingRule {



    private val faces = mutableMapOf<FaceType,Face>()
    private val destinations = mutableMapOf<Pair<FaceType, EdgeType>, FaceType>()
    private val borderTypes = mutableMapOf<Pair<FaceType, FaceType>, EdgeType>()

    override fun wrap(src: Pose): Pose {
        val destFaceType = destinations[Pair(src.face.type, src.facing)]!!
        val destBorderType = borderTypes[Pair(destFaceType, src.face.type)]!!
        val dest = faces[destFaceType]!!
        val position = translatePoint(src.facing, destBorderType, dest, src.position)
        return Pose(dest, position, destBorderType.opposite())
    }

    fun addFace(face: Face) {
        faces[face.type] = face
    }

    fun addFaceTransition(srcFaceType: FaceType, borderType:EdgeType, destFaceType: FaceType) {
        destinations[Pair(srcFaceType,borderType)] = destFaceType
        borderTypes[Pair(srcFaceType,destFaceType)] = borderType
    }
    operator fun contains(faceType: FaceType) = faceType in faces

    private fun translatePoint(srcType: EdgeType, destType: EdgeType, dest: Face, srcPoint: Point2D): Point2D {

        return when (srcType) {
            UP -> when (destType) {
                UP -> Point2D(dest.grid.width() - 1 - srcPoint.x, 0)
                DOWN -> Point2D(srcPoint.x, dest.grid.height() - 1)
                LEFT -> Point2D(0, srcPoint.x)
                RIGHT -> Point2D(dest.grid.width() - 1, dest.grid.height() - 1 - srcPoint.x)
            }

            DOWN -> when (destType) {
                UP -> Point2D(srcPoint.x, 0)
                DOWN -> Point2D(dest.grid.width() - 1 - srcPoint.x, dest.grid.height() - 1)
                LEFT -> Point2D(0, dest.grid.height() - 1 - srcPoint.x)
                RIGHT -> Point2D(dest.grid.width() - 1, srcPoint.x)
            }

            LEFT -> when (destType) {
                UP -> Point2D(srcPoint.y, 0)
                DOWN -> Point2D(dest.grid.width() - 1 - srcPoint.y, dest.grid.height() - 1)
                LEFT -> Point2D(0, dest.grid.height() - 1 - srcPoint.y)
                RIGHT -> Point2D(dest.grid.width() - 1, srcPoint.y)
            }

            RIGHT -> when (destType) {
                UP -> Point2D(dest.grid.width() - 1 - srcPoint.y, 0)
                DOWN -> Point2D(srcPoint.y, dest.grid.height() - 1)
                LEFT -> Point2D(0, srcPoint.y)
                RIGHT -> Point2D(dest.grid.width() - 1, dest.grid.height() - 1 - srcPoint.y)
            }
        }
    }
}