/*
 * Copyright (c) 2019 sparetimedevs and respective authors and developers.
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

package com.sparetimedevs.win

import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialDescriptor
import kotlinx.serialization.Serializer
import kotlinx.serialization.internal.SerialClassDescImpl
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.modules.serializersModuleOf
import kotlin.test.Test
import kotlin.test.assertEquals

class TestSerialization {

    @Test
    fun testCandidateViewModelSerialization() {
        val candidateViewModel = CandidateViewModel("test", Date(2020, 1, 10), listOf(Date(2020, 2, 20), Date(2020, 3, 30)))
        val returnedCandidateViewModel = testImplementationCandidateViewModelSerialization(candidateViewModel)
        assertEquals(candidateViewModel.name, returnedCandidateViewModel.name)
        assertEquals(candidateViewModel.firstAttendance.getTime(), returnedCandidateViewModel.firstAttendance.getTime())
        assertEquals(candidateViewModel.turns.map { it.getTime() }, returnedCandidateViewModel.turns.map { it.getTime() })
    }

    private fun testImplementationCandidateViewModelSerialization(candidateViewModel: CandidateViewModel): CandidateViewModel {
        val serializer =  CandidateViewModel.serializer()
        val json = Json(JsonConfiguration.Stable, serializersModuleOf(Date::class, DateSerializer))

        val jsonData = json.stringify(serializer, candidateViewModel)
        println(jsonData)
        console.info(jsonData)

        return json.parse(serializer, jsonData)
    }
}

@Serializer(forClass = Date::class)
object DateSerializer : KSerializer<Date> {
    override val descriptor: SerialDescriptor = SerialClassDescImpl("Date")

    override fun serialize(encoder: Encoder, obj: Date) {
        encoder.encodeString(obj.toDateString())
    }

    override fun deserialize(decoder: Decoder): Date {
        return Date(decoder.decodeString())
    }
}
