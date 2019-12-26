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

package com.sparetimedevs.win.loader

import org.w3c.dom.HTMLDivElement
import kotlin.browser.document

/**
 * To use this abstract class, the loader div needs to be present in the HMTL file.
 * <div id="loader" class="loader">
 *     <svg class="spinner">
 *         <circle cx="33" cy="33" r="25"></circle>
 *     </svg>
 * </div>
 */
abstract class Loader {

    private val loaderDiv: HTMLDivElement = document.getElementById("loader") as HTMLDivElement

    fun showLoader() {
        loaderDiv.style.visibility = "visible"
    }

    fun hideLoader() {
        loaderDiv.style.visibility = "hidden"
    }
}
