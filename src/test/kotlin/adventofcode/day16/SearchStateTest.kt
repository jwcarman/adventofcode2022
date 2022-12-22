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

package adventofcode.day16

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SearchStateTest {

    @Test
    fun turnOnValve() {
        val searchState: SearchState = 0
        assertThat(searchState.turnOnValve(3).toString(2)).isEqualTo("10000000000000000000")
    }

    @Test
    fun turnOffValve() {
        val searchState: SearchState = 0
        assertThat(searchState.turnOnValve(3).turnOffValve(3)).isZero
    }

    @Test
    fun setLocation() {
        val searchState: SearchState = 0
        assertThat(searchState.turnOnValve(5).setLocation(7).toString(2)).isEqualTo("1000000000000000000111")
    }

    @Test
    fun setTime() {
        val searchState: SearchState = 0
        assertThat(searchState.turnOnValve(5).setLocation(7).setTime(6).toString(2)).isEqualTo("1000000000011000000111")
    }
}