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

@Immutable
data class Billionaire(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val Rank: String,
    val Country: String,
    val NetWorth: String,
    val Age: String,
    val Source: String
    )

/**
 * Static data
 */

val billionaires = listOf(
    Billionaire(
        id = 1L,
        name = "Elon",
        imageUrl = "https://source.unsplash.com/pGM4sjt_BdQ",
        Rank = "1",
        Country = "USA",
        NetWorth = "1",
        Age = "1",
        Source = "1",
    ),
    Billionaire(
        id = 2L,
        name = "zecker",
        imageUrl = "https://source.unsplash.com/Yc5sL-ejk6U",
        Rank = "2",
        Country = "USA",
        NetWorth = "2",
        Age = "2",
        Source = "2",
    ),
    Billionaire(
        id = 3L,
        name = "Mark curt",
        imageUrl = "https://source.unsplash.com/-LojFX9NfPY",
        Rank = "3",
        Country = "USA",
        NetWorth = "3",
        Age = "3",
        Source = "3",
    )

)
