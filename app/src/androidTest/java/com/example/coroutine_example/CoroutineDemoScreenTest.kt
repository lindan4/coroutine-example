import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.coroutine_example.CoroutineDemoScreen
import com.example.coroutine_example.viewmodel.MyViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CoroutineDemoScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Test coroutine dispatcher
    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup() {
        // Set the main dispatcher to the test dispatcher
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun testComposableCoroutineScope() = testScope.runBlockingTest {

        val myViewModel = MyViewModel()

        // Start testing the composable
        composeTestRule.setContent {
            CoroutineDemoScreen(myViewModel = myViewModel)
        }

        // Perform the button click and trigger the data fetch
        composeTestRule.onNodeWithText("Fetch Data from Composable scope")
            .performClick()


        testScope.advanceUntilIdle()


        composeTestRule.onNodeWithText("Loading...") // Check for intermediate loading state
        composeTestRule.onNodeWithText("Fetched Data Successfully from Composable scope") // Check for final state
    }

    @After
    fun tearDown() {
        // Reset the main dispatcher after the test
        Dispatchers.resetMain()
    }
}