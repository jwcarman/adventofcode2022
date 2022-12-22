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

typealias SearchState = Int

fun SearchState.turnOnValve(valveIndex:Int) = this or (1 shl (16 + valveIndex))

fun SearchState.turnOffValve(valveIndex:Int) = this and (1 shl (16 + valveIndex)).inv()

fun SearchState.setLocation(location:Int) = ((this shr 8) shl 8) or location

fun SearchState.getLocation() = (this shl 24) ushr 24

fun SearchState.getTime() = ((this shl 16) ushr 24)
fun SearchState.setTime(time:Int) = (this and (255 shl 8).inv()) or (time shl 8)
