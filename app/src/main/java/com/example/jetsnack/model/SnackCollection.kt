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

package com.example.jetsnack.model

import androidx.compose.runtime.Immutable

@Immutable
data class SnackCollection(
    val id: Long,
    val name: String,
    val snacks: List<Snack>,
    val type: CollectionType = CollectionType.Normal
)

enum class CollectionType { Normal, Highlight }

/**
 * A fake repo
 */
object SnackRepo {
    fun getSnacks(): List<SnackCollection> = snackCollections
    fun getSnack(snackId: Long) = snacks.find { it.id == snackId }!!
    fun getRelated(@Suppress("UNUSED_PARAMETER") snackId: Long) = related
    fun getInspiredByCart() = inspiredByCart
    fun getFilters() = filters
    fun getPriceFilters() = priceFilters
    fun getCart() = cart
    fun getSortFilters() = sortFilters
    fun getCategoryFilters() = categoryFilters
    fun getSortDefault() = sortDefault
    fun getLifeStyleFilters() = lifeStyleFilters
}

/**
 * Static data
 */

private val tastyTreats = SnackCollection(
    id = 1L,
    name = "Android's picks",
    type = CollectionType.Highlight,
    snacks = snacks.subList(0, 3)
)


private val also = tastyTreats.copy(
    id = 6L,
    name = "Customers also bought"
)

private val inspiredByCart = tastyTreats.copy(
    id = 7L,
    name = "Inspired by your cart"
)

private val snackCollections = listOf(
    tastyTreats
)

private val related = listOf(
    also
)

private val cart = listOf(
    OrderLine(snacks[2], 2),
    OrderLine(snacks[1], 3),
    OrderLine(snacks[0], 1)
)

@Immutable
data class OrderLine(
    val snack: Snack,
    val count: Int
)
