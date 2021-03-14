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

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.WeTradeRepository
import com.example.androiddevchallenge.ui.theme.green
import com.example.androiddevchallenge.ui.theme.red
import java.util.Locale

@Composable
fun HomeTabs(
    selected: String,
    onSelectionChange: (value: String) -> Unit
) {
    Column {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center

        ) {
            TAB_CONTENT.forEach { tab ->
                TabItem(
                    text = tab.title(),
                    selected = selected == tab.route,
                    onClick = { onSelectionChange(tab.route) },
                    modifier = Modifier
                        .firstBaselineToTop(64.dp)
                        .weight(1f)
                )
            }
        }
        TAB_CONTENT_ROUTE[selected]!!.content()
    }
}

private val TAB_CONTENT =
    listOf(
        TabContent.Account,
        TabContent.Watchlist,
        TabContent.Profile
    )

private val TAB_CONTENT_ROUTE = TAB_CONTENT.associateBy { it.route }

sealed class TabContent(
    val route: String,
    val title: @Composable () -> String,
    val content: @Composable () -> Unit
) {
    object Account : TabContent(
        route = "account",
        title = { stringResource(id = R.string.section_account) },
        { Account() }
    )

    object Watchlist : TabContent(
        "watchlist",
        { stringResource(id = R.string.section_watchlist) },
        { NotReady() }
    )

    object Profile : TabContent(
        "profile",
        { stringResource(id = R.string.section_profile) },
        { NotReady() }
    )
}

@Composable
fun TabItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val alpha = if (selected) 1f else .5f

    Text(
        text = text.toUpperCase(Locale.getDefault()),
        maxLines = 1,
        color = MaterialTheme.colors.onBackground,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.button,
        modifier = modifier
            .alpha(alpha)
            .clickable { onClick() }
            .padding(8.dp)
    )
}

@Composable
fun Account() {
    val position = WeTradeRepository.getHighLightPosition()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Balance",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .firstBaselineToTop(32.dp)
                .align(Alignment.CenterHorizontally)

        )
        Text(
            text = position.balance,
            style = MaterialTheme.typography.h1,
            modifier = Modifier
                .firstBaselineToTop(48.dp)
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally)

        )
        val profitColor = if (position.profit.contains("+")) green else red
        Text(
            text = "${position.profit} today",
            style = MaterialTheme.typography.subtitle1.copy(color = profitColor),
            modifier = Modifier
                .padding(top = 8.dp)
                .firstBaselineToTop(40.dp)
                .align(Alignment.CenterHorizontally)

        )
        PrimaryButton(
            text = stringResource(id = R.string.btn_transact),
            onClick = { },
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth()
        )
        Filters(modifier = Modifier.padding(vertical = 16.dp))
        Image(
            painter = painterResource(id = position.chart),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
