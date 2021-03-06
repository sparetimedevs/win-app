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

import com.sparetimedevs.win.candidates.CandidatesPage
import org.w3c.dom.HTMLDivElement
import kotlin.browser.document
import kotlin.browser.window
import kotlin.dom.addClass

fun main() {
    val rootDiv: HTMLDivElement? = document.getElementById("root") as HTMLDivElement?

    rootDiv?.appendChild(createHeaderDiv()) ?: return

    val contentDiv: HTMLDivElement = document.createElement("div") as HTMLDivElement
    contentDiv.addClass("content", "content-children")
    rootDiv.appendChild(contentDiv)

    when (window.location.pathname) {
        "/index.html" -> IndexPage(contentDiv)
        "/candidates.html" -> CandidatesPage(contentDiv)
        else -> window.location.href = "index.html"
    }
}

private fun createHeaderDiv(): HTMLDivElement {
    val headerDiv: HTMLDivElement = document.createElement("div") as HTMLDivElement
    headerDiv.addClass("header")

    headerDiv.innerText = "Who is next?"

    return headerDiv
}
