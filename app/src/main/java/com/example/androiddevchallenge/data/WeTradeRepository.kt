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
package com.example.androiddevchallenge.data

import com.example.androiddevchallenge.R

data class Position(
    val name: String,
    val fullName: String,
    val balance: String,
    val profit: String,
    val chart: Int
)

object WeTradeRepository {

    private val alk = Position(
        name = "alk",
        fullName = "Alaska Air Group, Inc.",
        balance = "$7,918",
        profit = "-0.54%",
        chart = R.drawable.home_alk
    )

    private val ba = Position(
        name = "ba",
        fullName = "Boeing Co.",
        balance = "$1,293",
        profit = "+4.18%",
        chart = R.drawable.home_ba
    )

    private val ccl = Position(
        name = "ccl",
        fullName = "Carnival Corp",
        balance = "$5,481",
        profit = "+0.14%",
        chart = R.drawable.home_ccl
    )

    private val dal = Position(
        name = "dal",
        fullName = "Delta Airlines Inc.",
        balance = "$893.50",
        profit = "-0.54%",
        chart = R.drawable.home_dal
    )

    private val eadsy = Position(
        name = "eadsy",
        fullName = "Airbus SE",
        balance = "$12,301",
        profit = "+1.38%",
        chart = R.drawable.home_dal
    )

    private val expe = Position(
        name = "expe",
        fullName = "Expedia Group Inc.",
        balance = "$12,301",
        profit = "+2.51%",
        chart = R.drawable.home_exp
    )

    private val illos = Position(
        name = "illos",
        fullName = "We Share Inc.",
        balance = "$73,589.01",
        profit = "+412.35%",
        chart = R.drawable.home_illos
    )

    private val jblu = Position(
        name = "jblu",
        fullName = "Jetblue Airways Corp.",
        balance = "$8,521",
        profit = "+1.56%",
        chart = R.drawable.home_jblu
    )

    private val mar = Position(
        name = "mar",
        fullName = "Marriott International Inc.",
        balance = "$521",
        profit = "+2.75%",
        chart = R.drawable.home_mar
    )

    private val rcl = Position(
        name = "rcl",
        fullName = "Royal Caribbean Cruises",
        balance = "$9,184",
        profit = "+1.69%",
        chart = R.drawable.home_rcl
    )

    private val trvl = Position(
        name = "trvl",
        fullName = "Travelocity Inc",
        balance = "$654",
        profit = "+3.23%",
        chart = R.drawable.home_trvl
    )

    private val highlight = illos
    private val positions = listOf(illos, alk, ba, dal, expe, eadsy, jblu, mar, ccl, rcl, trvl)

    fun getHighLightPosition() = highlight

    fun getPositions() = positions.filter { it != highlight }
}
