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

package com.example.jetsnack.ui.home

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetsnack.domain.model.request.Billionaire
import com.example.jetsnack.ui.components.FilterBar
import com.example.jetsnack.ui.components.JetsnackSurface
import com.example.jetsnack.ui.components.SnackCollection


@Composable
fun Feed(
    onSnackClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    val billionaireList by viewModel.billionaireState.collectAsState()

    Feed(
        billionaireList,
        onSnackClick,
        modifier,
        viewModel
    )
}

@Composable
private fun Feed(
    billionaireList: List<Billionaire>,
    onSnackClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel
) {
    JetsnackSurface(modifier = modifier.fillMaxSize()) {
        Box {
            SnackCollectionList(billionaireList, onSnackClick, homeViewModel)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SnackCollectionList(
    billionaireList: List<Billionaire>,
    onSnackClick: (Long) -> Unit,
    homeViewModel: HomeViewModel,
    modifier: Modifier = Modifier,

    ) {
    var filtersVisible by rememberSaveable { mutableStateOf(false) }
    Box(modifier) {

        Column(modifier = Modifier.align(Alignment.TopStart)) {
            Spacer(
                Modifier.windowInsetsTopHeight(
                    WindowInsets.statusBars.add(WindowInsets(top = 10.dp))
                )
            )
            FilterBar(onShowFilters = { filtersVisible = true }, homeViewModel = homeViewModel)

            SnackCollection(
                billionaireList = billionaireList,
                onSnackClick = onSnackClick,
                index = 0
            )
        }


    }
    AnimatedVisibility(
        visible = filtersVisible,
        enter = slideInVertically() + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        FilterScreen(
            onDismiss = { filtersVisible = false },
            homeViewModel = homeViewModel
        )
    }
}

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
fun HomePreview() {

}
