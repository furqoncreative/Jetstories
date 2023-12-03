package id.furqoncreative.jetstories.ui.screens.home

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import id.furqoncreative.jetstories.DataDummy
import id.furqoncreative.jetstories.MainDispatcherRule
import id.furqoncreative.jetstories.data.source.local.PreferencesManager
import id.furqoncreative.jetstories.data.source.local.StoryItem
import id.furqoncreative.jetstories.fake.FakeGetAllStoriesWithPaginationRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var preferencesManager: PreferencesManager

    private val storyRepository = FakeGetAllStoriesWithPaginationRepository()
    private lateinit var differ: AsyncPagingDataDiffer<StoryItem>
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        differ = AsyncPagingDataDiffer(
            diffCallback = DIFF_CALLBACK_TEST,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = mainDispatcherRule.testDispatcher
        )

        homeViewModel = HomeViewModel(storyRepository, preferencesManager)
    }

    @Test
    fun `when get story then should not null and return data`() = runTest {
        val dummyStory = DataDummy.generateDummyStory()
        val data: PagingData<StoryItem> = StoryPagingResource.snapshot(dummyStory)
        storyRepository.emit(data)

        val actualStory = homeViewModel.uiState.value.stories.first()
        differ.submitData(actualStory)

        assertNotNull(differ.snapshot())
        assertEquals(dummyStory.size, differ.snapshot().size)
        assertEquals(dummyStory, differ.snapshot())
    }

    @Test
    fun `when get empty story then should return no data`() = runTest {
        val data: PagingData<StoryItem> = PagingData.empty()
        storyRepository.emit(data)

        val actualStory = homeViewModel.uiState.value.stories.first()
        differ.submitData(actualStory)

        assertNotNull(differ.snapshot())
        assertEquals(0, differ.snapshot().size)
    }
}

class StoryPagingResource : PagingSource<Int, LiveData<List<StoryItem>>>() {
    companion object {
        fun snapshot(items: List<StoryItem>): PagingData<StoryItem> {
            return PagingData.from(items)
        }
    }

    @Suppress("SameReturnValue")
    override fun getRefreshKey(state: PagingState<Int, LiveData<List<StoryItem>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<StoryItem>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

private val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}

private val DIFF_CALLBACK_TEST = object : DiffUtil.ItemCallback<StoryItem>() {
    override fun areItemsTheSame(oldItem: StoryItem, newItem: StoryItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: StoryItem, newItem: StoryItem): Boolean {
        return oldItem.id == newItem.id
    }
}