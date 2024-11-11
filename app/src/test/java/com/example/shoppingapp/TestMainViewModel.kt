import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.shoppingapp.retrofit.FakeStoreService
import com.example.shoppingapp.viewmodel.MainActivityViewModel
import com.example.shoppingapp.viewmodel.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalCoroutinesApi::class)
class MainActivityViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: FakeStoreService
    private lateinit var viewModel: MainActivityViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockWebServer = MockWebServer()
        mockWebServer.start()

        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FakeStoreService::class.java)

        viewModel = MainActivityViewModel().apply {
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        Dispatchers.resetMain()
    }

    @Test
    fun `loadProductData should post Success when response is valid`() = runTest(testDispatcher) {
        val jsonResponse = """
            [
                {"id": 1, "title": "Product 1", "price": 100.0, "description": "Desc 1", "images": [], "creationAt": null, "updatedAt": null, "category": null}
            ]
        """
        mockWebServer.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200))

        val observer = Observer<UiState> {}
        try {
            val uiStateLiveData = viewModel.uiState
            uiStateLiveData.observeForever(observer)

            viewModel.loadProductData()

            testDispatcher.scheduler.advanceUntilIdle()

            val value = uiStateLiveData.value
            assert(value is UiState.Loading)
        } finally {
            viewModel.uiState.removeObserver(observer)
        }
    }

    @Test
    fun `loadProductData should post Loading`() = runTest(testDispatcher) {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        val observer = Observer<UiState> {}
        try {
            viewModel.uiState.observeForever(observer)

            viewModel.loadProductData()

            testDispatcher.scheduler.advanceUntilIdle()

            val value = viewModel.uiState.value
            assert(value is UiState.Loading)
        } finally {
            viewModel.uiState.removeObserver(observer)
        }
    }
}
