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

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BlueprintTest {

    @Test
    fun parseBlueprintWithDuplicateCost() {
        val blueprint =
            Blueprint.parseBlueprint("Blueprint 4: Each ore robot costs 2 ore. Each clay robot costs 2 ore. Each obsidian robot costs 2 ore and 2 clay. Each geode robot costs 2 ore and 2 obsidian.")
        assertThat(blueprint.catalog).hasSize(4)
        assertThat(blueprint.catalog[Currency(ore = 1)]).isEqualTo(Currency(ore = 2))
        assertThat(blueprint.catalog[Currency(clay = 1)]).isEqualTo(Currency(ore = 2))
        assertThat(blueprint.catalog[Currency(obsidian = 1)]).isEqualTo(Currency(ore = 2, clay = 2))
        assertThat(blueprint.catalog[Currency(geode = 1)]).isEqualTo(Currency(ore = 2, obsidian = 2))

    }

    @Test
    fun example1Blueprint1() {
        val blueprint =
            Blueprint.parseBlueprint("Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.")
        assertThat(blueprint.calculateMaximum(24)).isEqualTo(9)
    }

    @Test
    fun example1Blueprint2() {
        val blueprint =
            Blueprint.parseBlueprint("Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.")
        assertThat(blueprint.calculateMaximum(24)).isEqualTo(12)
    }
}