package com.example.coroutines.usecases.coroutines.usecase1

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.coroutines.mock.mockAndroidVersions
import com.example.coroutines.usecases.usecase1.PerformSingleNetworkRequestViewModel
import com.example.coroutines.usecases.usecase1.UiState
import com.lukaslechner.coroutineusecasesonandroid.utils.ReplaceMainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class PerformSingleNetworkRequestViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get: Rule
    val replaceMainDispatcherRule = ReplaceMainDispatcherRule()

    private val receivedUiStates = mutableListOf<UiState>()

    @Test
    fun `should return Success when network request is successful`() = runTest {
        val fakeApi = FakeSuccessApi()
        val viewModel = PerformSingleNetworkRequestViewModel(fakeApi)
        observeViewModel(viewModel)

        assertTrue(receivedUiStates.isEmpty())

        viewModel.performSingleNetworkRequest()

        assertEquals(
            listOf(
                UiState.Loading,
                UiState.Success(mockAndroidVersions)
            ),
            receivedUiStates
        )
    }

    @Test
    fun `should return Error when network request fails`() = runTest {
        val fakeApi = FakeErrorApi()
        val viewModel = PerformSingleNetworkRequestViewModel(fakeApi)
        observeViewModel(viewModel)

        assertTrue(receivedUiStates.isEmpty())

        viewModel.performSingleNetworkRequest()

        assertEquals(
            listOf(
                UiState.Loading,
                UiState.Error("Network Request failed!")
            ),
            receivedUiStates
        )
    }

    private fun observeViewModel(viewModel: PerformSingleNetworkRequestViewModel) {
        viewModel.uiState().observeForever { uiState ->
            if (uiState != null) {
                receivedUiStates.add(uiState)
            }
        }
    }
}