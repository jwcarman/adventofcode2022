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

package adventofcode.day19

data class Currency(val ore: Int = 0, val clay: Int = 0, val obsidian: Int = 0, val geode: Int = 0) {
    operator fun plus(other: Currency) = Currency(
        ore + other.ore,
        clay + other.clay,
        obsidian + other.obsidian,
        geode + other.geode
    )
    operator fun minus(other: Currency) = Currency(
        ore - other.ore,
        clay - other.clay,
        obsidian - other.obsidian,
        geode - other.geode
    )
    fun exceeds(other: Currency): Boolean {
        return ore - other.ore >= 0 &&
                clay - other.clay >= 0 &&
                obsidian - other.obsidian >= 0 &&
                geode - other.geode >= 0
    }
}