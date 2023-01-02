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

package adventofcode.day23

enum class ElfMoveRule {
    NORTH {
        override fun propose(otherElves: Set<Elf>, elf: Elf): Elf {
            val options = listOf(elf.translate(-1, -1), elf.translate(0, -1), elf.translate(1, -1))
            if (options.any { it in otherElves }) {
                return elf
            }
            return elf.translate(0, -1)
        }
    },
    SOUTH {
        override fun propose(otherElves: Set<Elf>, elf: Elf): Elf {
            val options = listOf(elf.translate(-1, 1), elf.translate(0, 1), elf.translate(1, 1))
            if (options.any { it in otherElves }) {
                return elf
            }
            return elf.translate(0, 1)
        }
    },
    WEST {
        override fun propose(otherElves: Set<Elf>, elf: Elf): Elf {
            val options = listOf(elf.translate(-1, -1), elf.translate(-1, 0), elf.translate(-1, 1))
            if (options.any { it in otherElves }) {
                return elf
            }
            return elf.translate(-1, 0)
        }
    },
    EAST{
        override fun propose(otherElves: Set<Elf>, elf: Elf): Elf {
            val options = listOf(elf.translate(1, -1), elf.translate(1, 0), elf.translate(1, 1))
            if(options.any { it in otherElves }) {
                return elf
            }
            return elf.translate(1, 0)
        }
    };

    abstract fun propose(otherElves: Set<Elf>, elf: Elf): Elf
}