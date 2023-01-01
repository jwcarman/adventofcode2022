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

class FlatWrappingRule : WrappingRule {
    override fun wrap(pose: Pose): Pose {
        val position = when(pose.facing) {
            UP -> Point2D(pose.position.x, pose.face.grid.height() - 1)
            DOWN -> Point2D(pose.position.x, 0)
            LEFT -> Point2D(pose.face.grid.width() - 1, pose.position.y)
            RIGHT -> Point2D(0, pose.position.y)
        }
        return Pose(pose.face,position,pose.facing)
    }
}