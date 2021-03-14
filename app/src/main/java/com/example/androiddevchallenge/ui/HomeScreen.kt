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
package com.example.androiddevchallenge.ui

import androidx.annotation.FloatRange
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.androiddevchallenge.data.Position
import com.example.androiddevchallenge.data.WeTradeRepository
import com.example.androiddevchallenge.ui.theme.WeTradeTheme
import com.example.androiddevchallenge.ui.theme.green
import com.example.androiddevchallenge.ui.theme.red
import dev.chrisbanes.accompanist.insets.LocalWindowInsets
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import dev.chrisbanes.accompanist.insets.toPaddingValues
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(navController: NavController) {

    val (selected, onSelectionChange) = remember { mutableStateOf(TabContent.Account.route) }
    val positions = WeTradeRepository.getPositions()

    BoxWithConstraints(modifier = Modifier.statusBarsPadding()) {

        val sheetState = rememberSwipeableState(SheetState.Closed)
        val peekSize = with(LocalDensity.current) { 64.dp.toPx() }
        val dragRange = constraints.maxHeight - peekSize
        val scope = rememberCoroutineScope()

        val showPositionsSheet = selected == TabContent.Account.route

        val boxModifier = if (showPositionsSheet) {
            Modifier.swipeable(
                state = sheetState,
                anchors = mapOf(
                    0f to SheetState.Closed,
                    -dragRange to SheetState.Open
                ),
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Vertical
            )
        } else {
            Modifier
        }

        Box(modifier = boxModifier) {

            HomeTabs(selected, onSelectionChange)

            // Only show positions sheet when account selected
            if (showPositionsSheet) {

                val openFraction = if (sheetState.offset.value.isNaN()) {
                    0f
                } else {
                    -sheetState.offset.value / dragRange
                }.coerceIn(0f, 1f)

                PositionsSheet(
                    positions = positions,
                    peekSize = peekSize,
                    openFraction = openFraction,
                    this@BoxWithConstraints.constraints.maxHeight.toFloat(),
                    sheetState.currentValue
                ) { state ->
                    scope.launch {
                        sheetState.animateTo(state)
                    }
                }
            }
        }
    }
}

enum class SheetState { Open, Closed }

@Composable
fun PositionsSheet(
    positions: List<Position>,
    peekSize: Float,
    openFraction: Float,
    height: Float,
    currentState: SheetState,
    updateSheet: (SheetState) -> Unit
) {

    val sheetHeight = peekSize + LocalWindowInsets.current.systemBars.bottom
    val offsetY = lerp(height - sheetHeight, 0f, openFraction)

    Surface(modifier = Modifier.graphicsLayer { translationY = offsetY }) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxSize()) {
                val scroll = rememberLazyListState()
                val positionsAlpha = lerp(0f, 1f, openFraction)
                Text(
                    text = "Positions",
                    modifier = Modifier
                        .firstBaselineToTop(40.dp)
                        .fillMaxWidth()
                        .clickable(
                            indication = null,
                            interactionSource = remember {
                                MutableInteractionSource()
                            }
                        ) {
                            when (currentState) {
                                SheetState.Open -> updateSheet(SheetState.Closed)
                                SheetState.Closed -> updateSheet(SheetState.Open)
                            }
                        }
                        .padding(bottom = 24.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.subtitle1
                )
                LazyColumn(
                    state = scroll,
                    modifier = Modifier.graphicsLayer { alpha = positionsAlpha },
                    contentPadding = LocalWindowInsets.current.systemBars.toPaddingValues(
                        top = false
                    )
                ) {
                    items(positions) { position ->
                        Divider(thickness = 1.dp)
                        PositionItem(position)
                    }
                }
            }
        }
    }
}

@Composable
fun PositionItem(position: Position) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, bottom = 16.dp, end = 16.dp)
    ) {
        Row {
            Column {
                Text(
                    text = position.balance,
                    modifier = Modifier
                        .firstBaselineToTop(24.dp)
                )
                val profitColor = if (position.profit.contains("+")) green else red
                Text(
                    text = position.profit,
                    modifier = Modifier
                        .firstBaselineToTop(16.dp),
                    style = MaterialTheme.typography.body1.copy(color = profitColor)
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = position.name.toUpperCase(Locale.getDefault()),
                    modifier = Modifier
                        .firstBaselineToTop(24.dp),
                    style = MaterialTheme.typography.h3
                )

                Text(
                    text = position.fullName,
                    modifier = Modifier
                        .firstBaselineToTop(16.dp)
                )
            }
        }

        Image(
            painterResource(
                id = position.chart
            ),
            contentDescription = "chart",
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}

@Preview(widthDp = 360, heightDp = 56)
@Composable
fun PositionItemPreview() {
    WeTradeTheme {
        PositionItem(WeTradeRepository.getPositions().first())
    }
}

@Composable
fun NotReady() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Not Ready")
    }
}

/**
 * Linearly interpolate between two values
 */
fun lerp(
    startValue: Float,
    endValue: Float,
    @FloatRange(from = 0.0, to = 1.0) fraction: Float
): Float {
    return startValue + fraction * (endValue - startValue)
}
