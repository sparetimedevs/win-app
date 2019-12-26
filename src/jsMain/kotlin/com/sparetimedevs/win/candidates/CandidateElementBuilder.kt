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

package com.sparetimedevs.win.candidates

import com.sparetimedevs.win.CandidateViewModel
import org.w3c.dom.Element
import org.w3c.dom.HTMLDivElement
import kotlin.browser.document
import kotlin.dom.addClass

fun CandidateViewModel.toElement(index: Int): Element {
    return build(index, this)
}

private fun build(index: Int, candidateViewModel: CandidateViewModel): Element {
    val containerElement = document.createElement("div") as HTMLDivElement
    val innerContainerElementLeft = document.createElement("div") as HTMLDivElement
    val innerContainerElementMiddle = document.createElement("div") as HTMLDivElement
    val innerContainerElementRight = document.createElement("div") as HTMLDivElement
    val innerContainerElementRightRight = document.createElement("div") as HTMLDivElement
    val indexElement = document.createElement("div") as HTMLDivElement
    val nameElement = document.createElement("div") as HTMLDivElement
    val turnsElement = document.createElement("div") as HTMLDivElement
    val firstAttendanceElement = document.createElement("div") as HTMLDivElement
    // bind data
    bind(
        index = index,
        candidateViewModel = candidateViewModel,
        indexElement = indexElement,
        nameElement = nameElement,
        turnsElement = turnsElement,
        firstAttendanceElement = firstAttendanceElement
    )
    // apply styles
    applyStyle(
        containerElement = containerElement,
        innerContainerElementLeft = innerContainerElementLeft,
        innerContainerElementMiddle = innerContainerElementMiddle,
        innerContainerElementRight = innerContainerElementRight,
        innerContainerElementRightRight = innerContainerElementRightRight,
        indexElement = indexElement,
        nameElement = nameElement,
        turnsElement = turnsElement,
        firstAttendanceElement = firstAttendanceElement
    )
    innerContainerElementLeft.appendChild(indexElement)
    innerContainerElementMiddle.appendChild(nameElement)
    innerContainerElementRight.appendChild(turnsElement)
    innerContainerElementRightRight.appendChild(firstAttendanceElement)
    containerElement
        .appendChild(
            innerContainerElementLeft,
            innerContainerElementMiddle,
            innerContainerElementRight,
            innerContainerElementRightRight
        )
    return containerElement
}

// Apply CSS Classes
private fun applyStyle(
    containerElement: HTMLDivElement,
    innerContainerElementLeft: HTMLDivElement,
    innerContainerElementMiddle: HTMLDivElement,
    innerContainerElementRight: HTMLDivElement,
    innerContainerElementRightRight: HTMLDivElement,
    indexElement: HTMLDivElement,
    nameElement: HTMLDivElement,
    turnsElement: HTMLDivElement,
    firstAttendanceElement: HTMLDivElement
) {
    containerElement.addClass("candidates-element", "candidates-element-shadow")
    innerContainerElementLeft.addClass("candidates-element-inner-left")
    innerContainerElementMiddle.addClass("candidates-element-inner-middle")
    innerContainerElementRight.addClass("candidates-element-inner-right")
    innerContainerElementRightRight.addClass("candidates-element-inner-right-right")
    indexElement.addClass("text-index", "float-right")
    nameElement.addClass("text-name", "float-left")
    turnsElement.addClass("text-dates", "float-left")
    firstAttendanceElement.addClass("text-dates", "float-left")
}

// Bind data to the view
private fun bind(
    index: Int,
    candidateViewModel: CandidateViewModel,
    indexElement: HTMLDivElement,
    nameElement: HTMLDivElement,
    turnsElement: HTMLDivElement,
    firstAttendanceElement: HTMLDivElement
) {
    indexElement.innerHTML = (index + 1).toString()
    nameElement.innerHTML = candidateViewModel.name
    turnsElement.innerHTML = candidateViewModel.turns.joinToString { it.toDateString() }
    firstAttendanceElement.innerHTML = candidateViewModel.firstAttendance.toDateString()
}

private fun Element.appendChild(vararg elements: Element) {
    elements.forEach {
        this.appendChild(it)
    }
}
