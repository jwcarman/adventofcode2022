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

import adventofcode.util.removeAll
import adventofcode.util.search.bnb.BranchAndBound
import kotlin.math.max

data class Blueprint(val id: Int, val catalog: Map<Currency, Currency>) {

    val maxCosts = catalog.values.fold(Currency()) { acc, cost ->
        Currency(
            max(acc.ore, cost.ore),
            max(acc.clay, cost.clay),
            max(acc.obsidian, cost.obsidian)
        )
    }

    fun calculateMaximum(minutes: Int): Int {
        val answer = BranchAndBound.maximumSearch(
            OreCollectionSearchNode(
                minutes,
                Currency(),
                Currency(ore = 1),
                this
            ), -1
        )
        return answer.value()
    }

    fun affordable(balance: Currency): List<Pair<Currency, Currency>> {
        val affordable = catalog
            .filter { (_, cost) -> balance.exceeds(cost) }
            .map { (robots, cost) -> Pair(cost, robots) }
        return affordable
    }

    fun qualityLevel(minutes: Int): Int {
        val maximum = calculateMaximum(minutes)
        val answer = id * maximum
        return answer
    }

    companion object {
        fun parseBlueprint(input: String): Blueprint {
            val splits = input.removeAll(
                "Blueprint ",
                ": Each ore robot costs",
                " ore. Each clay robot costs",
                " ore. Each obsidian robot costs",
                " ore and",
                " clay. Each geode robot costs",
                " obsidian."
            ).split(' ')

            return Blueprint(
                splits[0].toInt(), mapOf(
                    Currency(ore = 1) to Currency(ore = splits[1].toInt()),
                    Currency(clay = 1) to Currency(ore = splits[2].toInt()),
                    Currency(obsidian = 1) to Currency(ore = splits[3].toInt(), clay = splits[4].toInt()),
                    Currency(geode = 1) to Currency(ore = splits[5].toInt(), obsidian = splits[6].toInt())
                )
            )
        }
    }
}