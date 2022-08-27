/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.jetsnack.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.SortByAlpha
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource

@Stable
class Filter(
    val name: String,
    enabled: Boolean = false,
    val icon: ImageVector? = null,
    var id: Int ? = 0
) {
    val enabled = mutableStateOf(enabled)
}
val industryFilters = listOf(
    Filter(name = "Technology", id=1),
    Filter(name = "Automotive", id=2),
    Filter(name = "Fashion", id=3),
    Filter(name = "Diversified", id=4),
    Filter(name = "Finance", id=5),
    Filter(name = "Telecom", id=6),
    Filter(name = "Media & Entertainment", id=7),
    Filter(name = "Food & Beverage", id=8),
    Filter(name = "Automotive", id=9),
    Filter(name = "Logistics", id=10),
    Filter(name = "Retail", id=11),
    Filter(name = "Investments", id=12),
    Filter(name = "Retail", id=13),
    Filter(name = "Healthcare", id=14),
    Filter(name = "Entertainment", id=15)
)

val sortFilters = listOf(
    Filter(name = "Default", id=16),
    Filter(name = "Youngest", id=17),
    Filter(name = "Oldest", id=18),
    Filter(name = "Women", id=19),
    Filter(name = "Men", id=20)
)

var sortDefault = sortFilters.get(0).name
