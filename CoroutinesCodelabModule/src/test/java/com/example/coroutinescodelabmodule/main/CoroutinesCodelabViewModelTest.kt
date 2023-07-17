/*
 * Copyright (C) 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.coroutinescodelabmodule.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.coroutinescodelabmodule.fakes.MainNetworkFake
import com.example.coroutinescodelabmodule.fakes.TitleDaoFake
import com.example.coroutinescodelabmodule.main.utils.CoroutinesCodelabScopeRule
import com.example.coroutinescodelabmodule.main.utils.getValueForTest
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CoroutinesCodelabViewModelTest {
    @get:Rule
    val coroutineScope = CoroutinesCodelabScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var viewmodel: CoroutinesCodelabViewModel

    @Before
    fun setup() {
        viewmodel = CoroutinesCodelabViewModel(
            TitleRepository(
                MainNetworkFake("OK"),
                TitleDaoFake("initial")
            )
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun whenMainClicked_updatesTaps() = runTest {
        viewmodel.onMainViewClicked()
        Truth.assertThat(viewmodel.taps.getValueForTest()).isEqualTo("0 taps")
        println("1 - ${viewmodel.taps.value}")
        advanceTimeBy(1000)
        println("2 - ${viewmodel.taps.value}")
        Truth.assertThat(viewmodel.taps.getValueForTest()).isEqualTo("1 taps")
    }
}