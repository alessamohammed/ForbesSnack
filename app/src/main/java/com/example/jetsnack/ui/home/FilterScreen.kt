/*
 * Copyright 2021 The Android Open Source Project
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

package com.example.jetsnack.ui.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.jetsnack.R
import com.example.jetsnack.domain.model.BillionaireRepo
import com.example.jetsnack.domain.model.Filter
import com.example.jetsnack.ui.components.JetsnackScaffold
import com.example.jetsnack.ui.theme.JetsnackTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FilterScreen(
    onDismiss: () -> Unit,
    homeViewModel: HomeViewModel
) {
    //var sortState by remember { mutableStateOf(BillionaireRepo.getSortDefault()) }
    val sortState = homeViewModel.sortState.collectAsState()
    val defaultFilter = BillionaireRepo.getSortDefault()

    Dialog(onDismissRequest = onDismiss) {


        JetsnackScaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = stringResource(id = R.string.close)
                            )
                        }
                    },
                    title = {
                        Text(
                            text = stringResource(id = R.string.label_filters),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h6
                        )
                    },
                    actions = {
                        val resetEnabled = sortState.value != defaultFilter
                        IconButton(
                            onClick = { homeViewModel.changeSortState("Default") },
                            enabled = resetEnabled
                        ) {
                            val alpha = if (resetEnabled) {
                                ContentAlpha.high
                            } else {
                                ContentAlpha.disabled
                            }
                            CompositionLocalProvider(LocalContentAlpha provides alpha) {
                                Text(
                                    text = stringResource(id = R.string.reset),
                                    style = MaterialTheme.typography.body2
                                )
                            }
                        }
                    },
                    backgroundColor = JetsnackTheme.colors.uiBackground
                )
            }
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 16.dp),
            ) {
                SortFiltersSection(
                    sortState = sortState.value,
                    onFilterChange = { filter ->
                        homeViewModel.changeSortState(filter.name)
                    }
                )
            }
        }
    }
}

@Composable
fun SortFiltersSection(
    sortState: String,
    onFilterChange: (Filter) -> Unit
) {
    FilterTitle(text = stringResource(id = R.string.sort))
    Column(Modifier.padding(bottom = 24.dp)) {
        SortFilters(
            sortState = sortState,
            onChanged = onFilterChange
        )
    }
}

@Composable
fun SortFilters(
    sortFilters: List<Filter> = BillionaireRepo.getSortFilters(),
    sortState: String,
    onChanged: (Filter) -> Unit
) {

    sortFilters.forEach { filter ->
        SortOption(
            text = filter.name,
            icon = filter.icon,
            selected = sortState == filter.name,
            onClickOption = {
                onChanged(filter)
            }
        )
    }
}

@Composable
fun FilterTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.h6,
        color = JetsnackTheme.colors.brand,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun SortOption(
    text: String,
    icon: ImageVector?,
    onClickOption: () -> Unit,
    selected: Boolean
) {
    Row(
        modifier = Modifier
            .padding(top = 14.dp)
            .selectable(selected) { onClickOption() }
    ) {
        if (icon != null) {
            Icon(imageVector = icon, contentDescription = null)
        }
        Text(
            text = text,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .padding(start = 10.dp)
                .weight(1f)
        )
        if (selected) {
            Icon(
                imageVector = Icons.Filled.Done,
                contentDescription = null,
                tint = JetsnackTheme.colors.brand
            )
        }
    }
}
