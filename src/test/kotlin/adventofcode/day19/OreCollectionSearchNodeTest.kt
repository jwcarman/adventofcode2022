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

import adventofcode.util.search.bnb.BranchAndBound.maximumSearch
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OreCollectionSearchNodeTest {
    @Test
    fun upperBoundWithOnlyFuture() {
        val blueprint = Blueprint(
            1, mapOf(
                Currency(geode = 1) to Currency()
            )
        )
        val node = OreCollectionSearchNode(5, Currency(), Currency(ore = 1), blueprint)
        assertThat(node.upperBound()).isEqualTo(maximumSearch(node, -1).value())
    }

    @Test
    fun upperBoundWithCurrentBalance() {
        val blueprint = Blueprint(
            1, mapOf(
                Currency(geode = 1) to Currency()
            )
        )
        val node = OreCollectionSearchNode(5, Currency(geode = 5), Currency(ore = 1), blueprint)
        assertThat(node.upperBound()).isEqualTo(maximumSearch(node, -1).value())
    }

    @Test
    fun upperBoundWithCurrentBalanceAndProduction() {
        val blueprint = Blueprint(
            1, mapOf(
                Currency(geode = 1) to Currency()
            )
        )
        val node = OreCollectionSearchNode(5, Currency(geode = 5), Currency(ore = 1, geode = 5), blueprint)
        assertThat(node.upperBound()).isEqualTo(maximumSearch(node, -1).value())
    }
}