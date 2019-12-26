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

package com.sparetimedevs.win.client.fetch

import com.sparetimedevs.win.CandidateViewModel
import com.sparetimedevs.win.Date
import kotlinx.coroutines.await
import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialDescriptor
import kotlinx.serialization.Serializer
import kotlinx.serialization.internal.SerialClassDescImpl
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.list
import kotlinx.serialization.modules.serializersModuleOf
import org.w3c.fetch.RequestInit
import org.w3c.fetch.Response
import kotlin.browser.window
import kotlin.js.json

val serializer =  CandidateViewModel.serializer().list
val json = Json(JsonConfiguration.Stable, serializersModuleOf(Date::class, DateSerializer))

class FetchError(message: String, status: Number, response: dynamic) : Error(message)

suspend fun makeError(res: Response): FetchError {
    return try {
        val errorResponse: dynamic = res.json().await()
        FetchError("Request failed", res.status, errorResponse)
    } catch (e: Exception) {
        val errorResponse = res.text().await()
        FetchError("Request failed", res.status, errorResponse)
    }
}

suspend fun request(method: String = "GET", body: dynamic = null): List<CandidateViewModel> {
    val response = window.fetch(URL, object : RequestInit {
        override var method: String? = method
        override var body: dynamic = body
        override var headers: dynamic = json(
            "Host" to HOST,
            "Api-Version" to API_VERSION,
            "Ocp-Apim-Subscription-Key" to API_KEY,
            "Ocp-Apim-Trace" to API_TRACE
        )
    }).await()

    return when {
        response.ok -> {
            json.parse(serializer, response.text().await())
        }
        else -> throw makeError(response)
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
