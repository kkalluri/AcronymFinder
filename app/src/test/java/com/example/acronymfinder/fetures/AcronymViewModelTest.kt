package com.example.acronymfinder.fetures

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.acronymfinder.data.model.AcronymResponse
import com.example.acronymfinder.data.remote.DataSource
import com.example.acronymfinder.eq
import com.example.acronymfinder.features.AcronymViewModel
import com.example.weatherapp.data.remote.NetworkResult
import com.example.weatherapptechscreen.TestCoroutineRule
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AcronymViewModelTest {

    // Executes each task synchronously using Architecture Components
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Mock DataSource
    @Mock
    lateinit var mockDataSource: DataSource

    // Mock LiveData observer
    @Mock
    lateinit var mockAcronymDataObserver: Observer<NetworkResult<List<AcronymResponse>>>

    // System under test
    private lateinit var viewModel: AcronymViewModel

    val dummyResult: NetworkResult<List<AcronymResponse>> = NetworkResult.Success(emptyList()) // Adjust this according to your sealed class


    // Test Coroutine Dispatcher
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    // Use the custom TestCoroutineRule
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()


    @Before
    fun setup() {
        viewModel = AcronymViewModel(mockDataSource)
        viewModel.acronymData.observeForever(mockAcronymDataObserver)
    }

    @Test
    fun `test updateString`() {
        val sf = "Test SF"
        viewModel.updateString(sf)
        verify(mockAcronymDataObserver, never()).onChanged(eq(dummyResult))
        // Assert that _sf LiveData value is updated
        assert(viewModel.sf.value == sf)
    }

    @Test
    fun `test getAcronymBySF success`() = testCoroutineDispatcher.runBlockingTest {
        val shortenedJson = """
    [
      {
        "sf": "API",
        "lfs": [
          {
            "lf": "active pharmaceutical ingredient",
            "freq": 139,
            "since": 2000
          },
          {
            "lf": "Pacific Islander",
            "freq": 66,
            "since": 1994
          },
          {
            "lf": "atmospheric pressure ionization",
            "freq": 63,
            "since": 1974
          }
        ]
      }
    ]
""".trimIndent()

        var sf = "API"

        val gson = Gson()
        val acronymResponse: List<AcronymResponse> =
            gson.fromJson(shortenedJson, Array<AcronymResponse>::class.java).toList()
        val mockResponse = acronymResponse

        // Mock behavior of getAcronymByString
        `when`(mockDataSource.getAcronymByString(sf)).thenReturn(NetworkResult.Success(mockResponse))

        viewModel.getAcronymBySF(sf)

        // Verify that loading state is emitted
        verify(mockAcronymDataObserver).onChanged(NetworkResult.Loading(true))

        // Verify that success response is emitted
        verify(mockAcronymDataObserver).onChanged(NetworkResult.Success(mockResponse))
    }

    @Test
    fun `test getAcronymBySF error`() = testCoroutineDispatcher.runBlockingTest {
        val sf = "Test SF"
        val errorMessage = "Error fetching data"

        // Mock behavior of getAcronymByString
        `when`(mockDataSource.getAcronymByString(sf)).thenReturn(NetworkResult.Failure(errorMessage))

        viewModel.getAcronymBySF(sf)

        // Verify that loading state is emitted
        verify(mockAcronymDataObserver).onChanged(NetworkResult.Loading(true))

        // Verify that error response is emitted
        verify(mockAcronymDataObserver).onChanged(NetworkResult.Failure(errorMessage))
    }
}
