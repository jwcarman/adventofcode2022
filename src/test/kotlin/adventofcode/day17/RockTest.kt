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

package adventofcode.day17

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RockTest {

    @Test
    fun pushLeftShouldReturnOriginalIfAgainstLeftWall() {
        val rock = listOf(64)
        assertThat(rock.push('<')).isSameAs(rock)
    }

    @Test
    fun pushLeftShouldShiftValueLeftOneBitIfValid() {
        val rock = listOf(32)
        assertThat(rock.push('<')).containsExactly(64)
    }

    @Test
    fun pushRightShouldReturnOriginalIfAgainstRightWall() {
        val rock = listOf(1)
        assertThat(rock.push('>')).isSameAs(rock)
    }

    @Test
    fun pushRightShouldShiftValueRightOneBitIfValid() {
        val rock = listOf(2)
        assertThat(rock.push('>')).containsExactly(1)
    }
    
    @Test
    fun checkCollidesWith() {
        Assertions.assertThat(1.collidesWith(0)).isFalse
        Assertions.assertThat(0b1111000.collidesWith(0b0000111)).isFalse
        Assertions.assertThat(0b1111100.collidesWith(0b0000111)).isTrue
        Assertions.assertThat(0b1111110.collidesWith(0b0000111)).isTrue
    }

    @Test
    fun checkCanPushRight() {
        Assertions.assertThat(listOf(1, 2, 4, 8).canPushRight()).isFalse
        Assertions.assertThat(listOf(3, 2, 4, 8).canPushRight()).isFalse
        Assertions.assertThat(listOf(5, 2, 4, 8).canPushRight()).isFalse
        Assertions.assertThat(listOf(9, 2, 4, 8).canPushRight()).isFalse
        Assertions.assertThat(listOf(17, 2, 4, 8).canPushRight()).isFalse
        Assertions.assertThat(listOf(33, 2, 4, 8).canPushRight()).isFalse
        Assertions.assertThat(listOf(65, 2, 4, 8).canPushRight()).isFalse

        Assertions.assertThat(listOf(2, 4, 8).canPushRight()).isTrue
    }

    @Test
    fun checkCanPushLeft() {
        Assertions.assertThat(listOf(64, 2, 4, 8).canPushLeft()).isFalse
        Assertions.assertThat(listOf(96, 2, 8, 8).canPushLeft()).isFalse
        Assertions.assertThat(listOf(80, 2, 8, 8).canPushLeft()).isFalse
        Assertions.assertThat(listOf(72, 2, 8, 8).canPushLeft()).isFalse
        Assertions.assertThat(listOf(68, 2, 8, 8).canPushLeft()).isFalse
        Assertions.assertThat(listOf(66, 2, 8, 8).canPushLeft()).isFalse
        Assertions.assertThat(listOf(65, 2, 8, 8).canPushLeft()).isFalse

        Assertions.assertThat(listOf(2, 4, 8).canPushLeft()).isTrue
    }

    @Test
    fun checkIsAgainstLeftWall() {
        Assertions.assertThat(1.isAgainstLeftWall()).isFalse
        Assertions.assertThat(2.isAgainstLeftWall()).isFalse
        Assertions.assertThat(4.isAgainstLeftWall()).isFalse
        Assertions.assertThat(8.isAgainstLeftWall()).isFalse
        Assertions.assertThat(16.isAgainstLeftWall()).isFalse
        Assertions.assertThat(32.isAgainstLeftWall()).isFalse
        Assertions.assertThat(64.isAgainstLeftWall()).isTrue

        Assertions.assertThat(96.isAgainstLeftWall()).isTrue
        Assertions.assertThat(48.isAgainstLeftWall()).isFalse

    }

    @Test
    fun checkIsAgainstRightWall() {
        Assertions.assertThat(1.isAgainstRightWall()).isTrue
        Assertions.assertThat(2.isAgainstRightWall()).isFalse
        Assertions.assertThat(4.isAgainstRightWall()).isFalse
        Assertions.assertThat(8.isAgainstRightWall()).isFalse
        Assertions.assertThat(16.isAgainstRightWall()).isFalse
        Assertions.assertThat(32.isAgainstRightWall()).isFalse
        Assertions.assertThat(64.isAgainstRightWall()).isFalse

        Assertions.assertThat(3.isAgainstRightWall()).isTrue
        Assertions.assertThat(6.isAgainstRightWall()).isFalse
    }
}