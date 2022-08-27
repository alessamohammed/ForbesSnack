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

import androidx.compose.runtime.Immutable
import com.example.jetsnack.domain.model.request.Billionaire

@Immutable
data class SnackCollection(
    val id: Long,
    val name: String,
    val billionaires: List<Billionaire>,
    val type: CollectionType = CollectionType.Normal
)

enum class CollectionType { Normal, Highlight }

/**
 * A fake repo
 */
object BillionaireRepo {
    fun getRelated(@Suppress("UNUSED_PARAMETER") snackId: Long) = ""
    fun getFilters() = filters
    fun getPriceFilters() = priceFilters
    fun getSortFilters() = sortFilters
    fun getCategoryFilters() = categoryFilters
    fun getSortDefault() = sortDefault
    fun getLifeStyleFilters() = lifeStyleFilters
}

/**
 * Static data
 */