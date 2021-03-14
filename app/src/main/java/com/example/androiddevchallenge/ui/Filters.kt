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

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

val filters = listOf(
    DropDownFilter("Week", listOf()),
    SimpleFilter("ETFs"),
    SimpleFilter("Stocks"),
    SimpleFilter("Funds"),
    DropDownFilter("Month", listOf()),
    DropDownFilter("Year", listOf())
)

@Composable
fun Filters(modifier: Modifier = Modifier) {
    LazyRow(modifier = modifier) {
        items(filters) { filter ->
            filter.FilterContent()
        }
    }
}

@Composable
fun Chip(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier
            .height(40.dp)
            .border(
                1.dp,
                color = MaterialTheme.colors.onBackground,
                shape = MaterialTheme.shapes.small
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center

    ) {
        content()
    }
}

interface Filter {
    @Composable
    fun FilterContent()
}

data class DropDownFilter(val name: String, val items: List<String>) : Filter {
    @Composable
    override fun FilterContent() {
        Chip {
            Row {
                Text(name)
                Icon(
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = "more",
                    modifier = Modifier
                        .padding(top = 2.dp)
                        .size(16.dp)
                )
            }
        }
    }
}

data class SimpleFilter(val name: String) : Filter {
    @Composable
    override fun FilterContent() {
        Chip(modifier = Modifier.padding(start = 8.dp)) {
            Text(name)
        }
    }
}
