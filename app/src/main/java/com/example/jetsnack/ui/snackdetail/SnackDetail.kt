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

package com.example.jetsnack.ui.snackdetail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.example.jetsnack.R
import com.example.jetsnack.domain.model.SnackCollection
import com.example.jetsnack.domain.model.BillionaireRepo
import com.example.jetsnack.domain.model.request.FinancialAsset
import com.example.jetsnack.ui.components.JetsnackButton
import com.example.jetsnack.ui.components.JetsnackDivider
import com.example.jetsnack.ui.components.JetsnackSurface
import com.example.jetsnack.ui.components.SnackImage
import com.example.jetsnack.ui.home.HomeViewModel
import com.example.jetsnack.ui.theme.JetsnackTheme
import com.example.jetsnack.ui.theme.Neutral8
import com.example.jetsnack.ui.utils.mirroringBackIcon
import java.util.*
import kotlin.math.max
import kotlin.math.min

private val BottomBarHeight = 56.dp
private val TitleHeight = 128.dp
private val GradientScroll = 180.dp
private val ImageOverlap = 115.dp
private val MinTitleOffset = 56.dp
private val MinImageOffset = 12.dp
private val MaxTitleOffset = ImageOverlap + MinTitleOffset + GradientScroll
private val ExpandedImageSize = 300.dp
private val CollapsedImageSize = 150.dp
private val HzPadding = Modifier.padding(horizontal = 24.dp)

@Composable
fun SnackDetail(
    snackId: Long,
    viewModel: HomeViewModel,
    upPress: () -> Unit,
) {
    val billionaireList by viewModel.billionaireState.collectAsState()
    val billionaire = billionaireList.first { it.rank.toLong() == snackId }
    val related = remember(snackId) { BillionaireRepo.getRelated(snackId) }
    var squareImage = if (billionaire.squareImage.startsWith("http"))
    {
        billionaire.squareImage
    }
    else
    {
        "https:${billionaire.squareImage}"
    }
    var link = "https://www.forbes.com/profile/${billionaire.uri}"

    Box(Modifier.fillMaxSize()) {
        val scroll = rememberScrollState(0)
        Header()
        Body(billionaire, scroll)
        Title(billionaire) { scroll.value }
        Image(squareImage) { scroll.value }
        Up(upPress)
        CartBottomBar(modifier = Modifier.align(Alignment.BottomCenter), link)
    }
}

@Composable
private fun Header() {
    Spacer(
        modifier = Modifier
            .height(280.dp)
            .fillMaxWidth()
            .background(Brush.horizontalGradient(JetsnackTheme.colors.tornado1))
    )
}

@Composable
private fun Up(upPress: () -> Unit) {
    IconButton(
        onClick = upPress,
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .size(36.dp)
            .background(
                color = Neutral8.copy(alpha = 0.32f),
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = mirroringBackIcon(),
            tint = JetsnackTheme.colors.iconInteractive,
            contentDescription = stringResource(R.string.label_back)
        )
    }
}

@Composable
private fun Body(
    billionaire: com.example.jetsnack.domain.model.request.Billionaire,
    scroll: ScrollState
) {
    var bio = ""
    billionaire.bios.forEach {
        bio+=("$it\n")
    }
    var about = ""
    billionaire.abouts.forEach {
        about+=("$it\n")
    }
    var industries = ""
    billionaire.industries.forEach {
        industries+=("$it, ")
    }
    var ageYear = ""
    ageYear =  (Date(billionaire.timestamp).year - Date(billionaire.birthDate).year).toString()

    var country = ""
    country +=if(billionaire.countryOfCitizenship !="") billionaire.countryOfCitizenship else
    country += if (billionaire.state !="") ", ${billionaire.state}" else ""
    country += if (billionaire.city !="") ", ${billionaire.city}" else ""
    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(MinTitleOffset)
        )
        Column(
            modifier = Modifier.verticalScroll(scroll)
        ) {
            Spacer(Modifier.height(GradientScroll))
            JetsnackSurface(Modifier.fillMaxWidth()) {
                Column {
                    Spacer(Modifier.height(ImageOverlap))
                    Spacer(Modifier.height(TitleHeight))

                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.detail_bio),
                        style = MaterialTheme.typography.overline,
                        color = JetsnackTheme.colors.textHelp,
                        modifier = HzPadding
                    )
                    Spacer(Modifier.height(16.dp))

                    var seeMore by remember { mutableStateOf(true) }
                    Text(
                        text = bio,
                        style = MaterialTheme.typography.body1,
                        color = JetsnackTheme.colors.textHelp,
                        maxLines = if (seeMore) 5 else Int.MAX_VALUE,
                        overflow = TextOverflow.Ellipsis,
                        modifier = HzPadding
                    )

                    val textButton = if (seeMore) {
                        stringResource(id = R.string.see_more)
                    } else {
                        stringResource(id = R.string.see_less)
                    }
                    Text(
                        text = textButton,
                        style = MaterialTheme.typography.button,
                        textAlign = TextAlign.Center,
                        color = JetsnackTheme.colors.textLink,
                        modifier = Modifier
                            .heightIn(20.dp)
                            .fillMaxWidth()
                            .padding(top = 15.dp)
                            .clickable {
                                seeMore = !seeMore
                            }
                    )
                    Spacer(Modifier.height(40.dp))
                    Text(
                        text = stringResource(R.string.about),
                        style = MaterialTheme.typography.overline,
                        color = JetsnackTheme.colors.textHelp,
                        modifier = HzPadding
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = about,
                        style = MaterialTheme.typography.body1,
                        color = JetsnackTheme.colors.textHelp,
                        modifier = HzPadding
                    )

                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.detail_industries),
                        style = MaterialTheme.typography.overline,
                        color = JetsnackTheme.colors.textHelp,
                        modifier = HzPadding
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = industries,
                        style = MaterialTheme.typography.body1,
                        color = JetsnackTheme.colors.textHelp,
                        modifier = HzPadding
                    )

                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.detail_citizenship),
                        style = MaterialTheme.typography.overline,
                        color = JetsnackTheme.colors.textHelp,
                        modifier = HzPadding
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = country,
                        style = MaterialTheme.typography.body1,
                        color = JetsnackTheme.colors.textHelp,
                        modifier = HzPadding
                    )

                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.billionaires_age),
                        style = MaterialTheme.typography.overline,
                        color = JetsnackTheme.colors.textHelp,
                        modifier = HzPadding
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = ageYear,
                        style = MaterialTheme.typography.body1,
                        color = JetsnackTheme.colors.textHelp,
                        modifier = HzPadding
                    )

                    Spacer(Modifier.height(16.dp))
                    JetsnackDivider()

//                    related.forEach { snackCollection ->
//                        key(snackCollection.id) {
//                            SnackCollection(
//                                snackCollection = snackCollection,
//                                onSnackClick = { }
//                            )
//                        }
//                    }

                    TableScreen(billionaire.financialAssets)
                    Spacer(
                        modifier = Modifier
                            .padding(bottom = BottomBarHeight)
                            .navigationBarsPadding()
                            .height(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun Title(billionaire: com.example.jetsnack.domain.model.request.Billionaire, scrollProvider: () -> Int) {
    val maxOffset = with(LocalDensity.current) { MaxTitleOffset.toPx() }
    val minOffset = with(LocalDensity.current) { MinTitleOffset.toPx() }
    var netWorth: String = ""
    if (billionaire.finalWorth >1000) {
        netWorth= String.format("%.2f",billionaire.finalWorth / 1000) + " B"
    }
    else
        netWorth = String.format("%.2f",billionaire.finalWorth) + " M"
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .heightIn(min = TitleHeight)
            .statusBarsPadding()
            .offset {
                val scroll = scrollProvider()
                val offset = (maxOffset - scroll).coerceAtLeast(minOffset)
                IntOffset(x = 0, y = offset.toInt())
            }
            .background(color = JetsnackTheme.colors.uiBackground)
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            text = billionaire.personName + ", ${billionaire.rank}",
            style = MaterialTheme.typography.h4,
            color = JetsnackTheme.colors.textSecondary,
            modifier = HzPadding
        )
        Text(
            text = netWorth,
            style = MaterialTheme.typography.subtitle2,
            fontSize = 20.sp,
            color = JetsnackTheme.colors.textHelp,
            modifier = HzPadding
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = billionaire.source,
            style = MaterialTheme.typography.h6,
            color = JetsnackTheme.colors.textPrimary,
            modifier = HzPadding
        )

        Spacer(Modifier.height(8.dp))
        JetsnackDivider()
    }
}

@Composable
private fun Image(
    imageUrl: String,
    scrollProvider: () -> Int
) {
    val collapseRange = with(LocalDensity.current) { (MaxTitleOffset - MinTitleOffset).toPx() }
    val collapseFractionProvider = {
        (scrollProvider() / collapseRange).coerceIn(0f, 1f)
    }

    CollapsingImageLayout(
        collapseFractionProvider = collapseFractionProvider,
        modifier = HzPadding.then(Modifier.statusBarsPadding())
    ) {
        SnackImage(
            imageUrl = imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun CollapsingImageLayout(
    collapseFractionProvider: () -> Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        check(measurables.size == 1)

        val collapseFraction = collapseFractionProvider()

        val imageMaxSize = min(ExpandedImageSize.roundToPx(), constraints.maxWidth)
        val imageMinSize = max(CollapsedImageSize.roundToPx(), constraints.minWidth)
        val imageWidth = lerp(imageMaxSize, imageMinSize, collapseFraction)
        val imagePlaceable = measurables[0].measure(Constraints.fixed(imageWidth, imageWidth))

        val imageY = lerp(MinTitleOffset, MinImageOffset, collapseFraction).roundToPx()
        val imageX = lerp(
            (constraints.maxWidth - imageWidth) / 2, // centered when expanded
            constraints.maxWidth - imageWidth, // right aligned when collapsed
            collapseFraction
        )
        layout(
            width = constraints.maxWidth,
            height = imageY + imageWidth
        ) {
            imagePlaceable.placeRelative(imageX, imageY)
        }
    }
}

@Composable
private fun CartBottomBar(modifier: Modifier = Modifier, link: String) {
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(link)) }
    JetsnackSurface(modifier) {
        Column {
            JetsnackDivider()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .navigationBarsPadding()
                    .then(HzPadding)
                    .heightIn(min = BottomBarHeight)
            ) {
//                QuantitySelector(
//                    count = count,
//                    decreaseItemCount = { if (count > 0) updateCount(count - 1) },
//                    increaseItemCount = { updateCount(count + 1) }
//                )
//                Spacer(Modifier.width(16.dp))
                JetsnackButton(
                    onClick = { context.startActivity(intent) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.profile),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }
            }
        }
    }
}


@Composable
private fun TableScreen(financialAsset: List<FinancialAsset>) {
    // Each cell of a column must have the same weight.
    val column1Weight = .5f // 30%
    val column2Weight = .5f // 70%


            // Here is the header
            Row(Modifier
                .background(Color.Gray),
                horizontalArrangement = Arrangement.Center

            ) {
                TableCell(text = stringResource(R.string.asset_name),
                    weight = column1Weight)
                TableCell(text = stringResource(R.string.shares_worth_usd),
                    weight = column2Weight)
            }

            // Here are all the lines of your table.
            financialAsset.forEach {
                var price = (it.numberOfShares.toDouble() * it.sharePrice * it.exchangeRate)
                var showPrice =""
                if (price >= 1000000000)
                {
                    price = price / 1000000000
                    showPrice = String.format("%.2f",price) + " B"
                }
                else if (price >= 1000000)
                {
                    price = price / 1000000
                    showPrice = String.format("%.2f",price) + " M"
                }
                else if (price >= 1000)
                {
                    price = price / 1000
                    showPrice = String.format("%.2f",price) + " K"
                }
                else
                {
                    showPrice = String.format("%.2f",price)
                }
                Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
                    ) {
                    Row {
                        TableCell(text = it.companyName, weight = column1Weight)
                        TableCell(text = showPrice, weight = column2Weight)
                    }
                }
        }

}


@Composable
private fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp),
        maxLines = 1
    )
}
