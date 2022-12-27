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

import adventofcode.util.search.bnb.MaxSearchNode

class OreCollectionSearchNode(
    private val timeRemaining: Int,
    private val balance: Currency,
    private val production: Currency,
    private val blueprint: Blueprint,
    private val ignored: Currency = Currency(),
    private val parent: OreCollectionSearchNode? = null,
    private val description: String = ""
) : MaxSearchNode<Int> {

    private val upperBound =
        balance.geode + (production.geode * timeRemaining) + (((timeRemaining - 1) * timeRemaining) / 2)

    override fun isLeaf() = timeRemaining == 0
    override fun upperBound() = upperBound
    override fun value() = balance.geode
    override fun branches(): List<MaxSearchNode<Int>> {
        val affordable = blueprint.affordable(balance)
            .map { (cost, purchasedRobots) -> purchaseRobots(cost, purchasedRobots) }

        val purchaseGeode = affordable.filter(::hasMoreGeodeProduction)
        if (purchaseGeode.isNotEmpty()) {
            return purchaseGeode
        }

        val filtered = affordable
            .filter(::doesNotExceedMaxCosts)
            .filter(::shouldNotIgnore)
            .filter(::canProduceAnything)

        val ignoreAll = ignoreAll(affordable)
        val buyNothing = OreCollectionSearchNode(
            timeRemaining - 1,
            balance + production,
            production,
            blueprint,
            ignoreAll,
            this,
            description="Buy nothing, ignoring $ignoreAll"
        )

        return filtered + buyNothing
    }

    private fun ignoreAll(affordable: List<OreCollectionSearchNode>) =
        affordable.fold(Currency()) { acc, node -> acc + (node.production - production) }

    private fun hasMoreGeodeProduction(node: OreCollectionSearchNode) = node.production.geode > production.geode

    private fun canProduceAnything(node: OreCollectionSearchNode): Boolean {
        val diff = (node.production - production)
        return when (node.timeRemaining) {
            1 -> diff.geode > 0
            2 -> diff.geode > 0 || diff.obsidian > 0
            else -> true
        }
    }

    private fun doesNotExceedMaxCosts(node: OreCollectionSearchNode) =
        node.production.ore <= blueprint.maxCosts.ore &&
                node.production.clay <= blueprint.maxCosts.clay &&
                node.production.obsidian <= blueprint.maxCosts.obsidian


    private fun shouldNotIgnore(node: OreCollectionSearchNode) =
        node.production.ore - production.ore > ignored.ore ||
                node.production.clay - production.clay > ignored.clay ||
                node.production.obsidian - production.obsidian > ignored.obsidian

    private fun purchaseRobots(cost: Currency, additionalProduction: Currency) =
        OreCollectionSearchNode(
            timeRemaining - 1,
            balance + production - cost,
            production + additionalProduction,
            blueprint,
            parent=this,
            description="Spend $cost to purchase $additionalProduction"
        )

}