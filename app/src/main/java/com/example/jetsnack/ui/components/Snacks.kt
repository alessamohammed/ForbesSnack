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

package com.example.jetsnack.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.icu.util.Calendar
import android.os.Build
import android.text.format.DateFormat
import android.text.format.DateUtils
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetsnack.R
import com.example.jetsnack.domain.model.Billionaire
import com.example.jetsnack.domain.model.SnackCollection
import com.example.jetsnack.domain.model.billionaires
import com.example.jetsnack.ui.theme.JetsnackTheme
import com.example.jetsnack.ui.utils.mirroringIcon
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.system.measureTimeMillis

private val HighlightCardWidth = 170.dp
private val HighlightCardPadding = 16.dp


@Composable
fun SnackCollection(
    billionaireList: List<com.example.jetsnack.domain.model.request.Billionaire>,
    onSnackClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    index: Int = 0
) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .heightIn(min = 56.dp)
                .padding(start = 24.dp)
        ) {
            Text(
                text = "Billionaires",
                style = MaterialTheme.typography.h6,
                color = JetsnackTheme.colors.brand,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
            )
//            IconButton(
//                onClick = { /* todo */ }
//            ) {
//                Icon(
//                    imageVector = mirroringIcon(
//                        ltrIcon = Icons.Outlined.ArrowForward,
//                        rtlIcon = Icons.Outlined.ArrowBack
//                    ),
//                    tint = JetsnackTheme.colors.brand,
//                    contentDescription = null
//                )
//            }
        }
            HighlightedSnacks(index, billionaireList, onSnackClick)

    }


@Composable
private fun HighlightedSnacks(
    index: Int,
    billionaires: List<com.example.jetsnack.domain.model.request.Billionaire>,
    onSnackClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val scroll = rememberScrollState(0)
    val gradient = when ((index / 2) % 2) {
        0 -> JetsnackTheme.colors.gradient6_1
        else -> JetsnackTheme.colors.gradient6_2
    }
    // The Cards show a gradient which spans 3 cards and scrolls with parallax.
    val gradientWidth = with(LocalDensity.current) {
        (6 * (HighlightCardWidth + HighlightCardPadding).toPx())
    }
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(start = 24.dp, end = 24.dp)
    ) {
        itemsIndexed(billionaires) { index, billionaire ->
            billionaireItem(
                billionaire,
                onSnackClick,
                index,
                gradient,
                gradientWidth,
                scroll.value
            )
        }
    }
}

@Composable
private fun billionaireItem(
    billionaire: com.example.jetsnack.domain.model.request.Billionaire,
    onSnackClick: (Long) -> Unit,
    index: Int,
    gradient: List<Color>,
    gradientWidth: Float,
    scroll: Int,
    modifier: Modifier = Modifier
) {
    val left = index * with(LocalDensity.current) {
        (HighlightCardWidth + HighlightCardPadding).toPx()
    }
    var ageYear: String

    var netWorth: String
    if (billionaire.finalWorth >1000) {
       netWorth= String.format("%.2f",billionaire.finalWorth / 1000) + " B"
    }
    else
        netWorth = String.format("%.2f",billionaire.finalWorth) + " M"

    var squareImage = if (billionaire.squareImage.startsWith("http"))
    {
        billionaire.squareImage
    }
    else
    {
        "https:${billionaire.squareImage}"
    }
    JetsnackCard(
        modifier = modifier
            .size(
                width = 350.dp,
                height = 350.dp
            )
            .padding(bottom = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .clickable(onClick = { onSnackClick(billionaire.rank.toLong()) })
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth()
            ) {
                val gradientOffset = left - (scroll / 3f)
                Box(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .offsetGradientBackground(gradient, gradientWidth, gradientOffset)
                )
                    SnackImage(
                        imageUrl = squareImage,
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .align(Alignment.BottomCenter)
                    )

            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = billionaire.personName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.h5,
                color = JetsnackTheme.colors.textSecondary,
                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
            )
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start)
            {
                        Column (
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .fillMaxWidth(0.5f)
                            ,
                                ) {
                            Row {
                                Text(
                                    text = stringResource(R.string.billionaires_rank) + ": ",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.body1,
                                    color = JetsnackTheme.colors.textSecondary,
                                    textAlign = TextAlign.Start
                                )

                                Text(
                                    text = billionaire.rank.trim(),
                                    style = MaterialTheme.typography.body2,
                                    color = JetsnackTheme.colors.textHelp,
                                    textAlign = TextAlign.Start
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            }
                Column (modifier = Modifier
                    .padding(end =2.dp)
                    .fillMaxWidth(0.5f)

                ) {
                    Row {
                        Text(
                            text = stringResource(R.string.billionaires_age) + ": ",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.body1,
                            color = JetsnackTheme.colors.textSecondary,
                            textAlign = TextAlign.Start
                        )
                        ageYear =  (Date(billionaire.timestamp).year - Date(billionaire.birthDate).year).toString()

                        Text(
                            text = ageYear,
                            style = MaterialTheme.typography.body2,
                            color = JetsnackTheme.colors.textHelp,
                            textAlign = TextAlign.Start
                        )
                    }


            }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Row(modifier = Modifier,
                horizontalArrangement = Arrangement.Start)
            {
                        Column (
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .fillMaxWidth(0.5f)
                            ,
                                ) {

                            Row {
                                Text(
                                    text = stringResource(R.string.billionaires_net_worth) + ": ",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.body1,
                                    color = JetsnackTheme.colors.textSecondary,
                                    textAlign = TextAlign.Start
                                )

                                Text(
                                    text = netWorth,
                                    style = MaterialTheme.typography.body2,
                                    color = JetsnackTheme.colors.textHelp,
                                    textAlign = TextAlign.Start
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            }
                Column (modifier = Modifier
                    .padding(end =2.dp)
                ) {
                    Row {
                        Text(
                            text = stringResource(R.string.billionaires_country) + ": ",
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.body1,
                            color = JetsnackTheme.colors.textSecondary,
                            textAlign = TextAlign.Start
                        )

                        Text(
                            text = billionaire.countryOfCitizenship.trim(),
                            style = MaterialTheme.typography.body2,
                            color = JetsnackTheme.colors.textHelp,
                            textAlign = TextAlign.Start,
                            maxLines = 2
                        )
                    }
                            }

            }
            Row (Modifier
                .align(Alignment.Start)
                .padding(start = 8.dp),
                horizontalArrangement = Arrangement.Start){
                Text(
                    text = stringResource(R.string.billionaires_source) + ": ",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.body1,
                    color = JetsnackTheme.colors.textSecondary,
                    textAlign = TextAlign.Start
                )

                Text(
                    text = billionaire.source.trim(),
                    style = MaterialTheme.typography.body2,
                    color = JetsnackTheme.colors.textHelp,
                    textAlign = TextAlign.Start
                )
            }

        }
    }
}

@Composable
fun SnackImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp
) {
    JetsnackSurface(
        color = Color.LightGray,
        elevation = elevation,
        shape = CircleShape,
        modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = contentDescription,
            placeholder = painterResource(R.drawable.placeholder),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
        )
    }
}

@Preview("default")
@Preview("dark theme", uiMode = UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
fun SnackCardPreview() {
    JetsnackTheme {
//        val billionaire = billionaires.first()
//        billionaireItem(
//            billionaire = billionaire,
//            onSnackClick = { },
//            index = 0,
//            gradient = JetsnackTheme.colors.gradient6_1,
//            gradientWidth = gradientWidth,
//            scroll = 0
//        )
    }
}
