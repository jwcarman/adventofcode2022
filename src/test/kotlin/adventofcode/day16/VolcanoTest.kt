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

import adventofcode.util.readAsString
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class VolcanoTest {

    private fun Volcano.assertTravelTime(start: Valve, end: Valve, travelTime: Int) {
        assertThat(tunnelFrom(start, end)).isEqualTo(Tunnel(start, end, travelTime))
    }

    @Test
    fun testParsing() {
        val volcano = Volcano.parse(readAsString("day16-example.txt"))
        assertThat(volcano).isNotNull
        assertThat(volcano.valves()).hasSize(7)
        val aa = Valve.start()
        val bb = Valve("BB", 13)
        val cc = Valve("CC", 2)
        val dd = Valve("DD", 20)
        val ee = Valve("EE", 3)
        val hh = Valve("HH", 22)
        val jj = Valve("JJ", 21)

        volcano.assertTravelTime(aa, bb, 2)
        volcano.assertTravelTime(aa, cc, 3)
        volcano.assertTravelTime(aa, dd, 2)
        volcano.assertTravelTime(aa, ee, 3)
        volcano.assertTravelTime(aa, hh, 6)
        volcano.assertTravelTime(aa, jj, 3)
        assertThat(volcano.tunnelsFrom(aa)).hasSize(6)

        volcano.assertTravelTime(bb, cc, 2)
        volcano.assertTravelTime(bb, dd, 3)
        volcano.assertTravelTime(bb, ee, 4)
        volcano.assertTravelTime(bb, hh, 7)
        volcano.assertTravelTime(bb, jj, 4)
        assertThat(volcano.tunnelsFrom(bb)).hasSize(5)

        volcano.assertTravelTime(cc, bb, 2)
        volcano.assertTravelTime(cc, dd, 2)
        volcano.assertTravelTime(cc, ee, 3)
        volcano.assertTravelTime(cc, hh, 6)
        volcano.assertTravelTime(cc, jj, 5)
        assertThat(volcano.tunnelsFrom(cc)).hasSize(5)

        volcano.assertTravelTime(dd, bb, 3)
        volcano.assertTravelTime(dd, cc, 2)
        volcano.assertTravelTime(dd, ee, 2)
        volcano.assertTravelTime(dd, hh, 5)
        volcano.assertTravelTime(dd, jj, 4)
        assertThat(volcano.tunnelsFrom(dd)).hasSize(5)

        volcano.assertTravelTime(ee, bb, 4)
        volcano.assertTravelTime(ee, cc, 3)
        volcano.assertTravelTime(ee, dd, 2)
        volcano.assertTravelTime(ee, hh, 4)
        volcano.assertTravelTime(ee, jj, 5)
        assertThat(volcano.tunnelsFrom(ee)).hasSize(5)

        volcano.assertTravelTime(hh, bb, 7)
        volcano.assertTravelTime(hh, cc, 6)
        volcano.assertTravelTime(hh, dd, 5)
        volcano.assertTravelTime(hh, ee, 4)
        volcano.assertTravelTime(hh, jj, 8)
        assertThat(volcano.tunnelsFrom(hh)).hasSize(5)

        volcano.assertTravelTime(jj, bb, 4)
        volcano.assertTravelTime(jj, cc, 5)
        volcano.assertTravelTime(jj, dd, 4)
        volcano.assertTravelTime(jj, ee, 5)
        volcano.assertTravelTime(jj, hh, 8)
        assertThat(volcano.tunnelsFrom(jj)).hasSize(5)



    }
}