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

import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLDivElement
import kotlin.browser.document
import kotlin.browser.window
import kotlin.dom.addClass

class IndexPage(
    private val contentDiv: HTMLDivElement
) {
    init {
        show()
    }

    private fun show() {
        val viewCandidatesButtonElement: HTMLButtonElement = document.createElement("button") as HTMLButtonElement

        viewCandidatesButtonElement.innerHTML = "view candidates"
        viewCandidatesButtonElement.addEventListener("click", {
            window.location.href = "candidates.html"
        })
        viewCandidatesButtonElement.addClass("view-details", "ripple", "float-right")

        contentDiv.appendChild(viewCandidatesButtonElement)
    }
}
