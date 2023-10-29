package id.furqoncreative.jetstories.ui.pages.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.testing.asSnapshot
import androidx.recyclerview.widget.ListUpdateCallback
import com.dicoding.myunlimitedquotes.MainDispatcherRule
import id.furqoncreative.jetstories.DataDummy
import id.furqoncreative.jetstories.data.repository.NetworkGetAllStoriesWithPaginationRepository
import id.furqoncreative.jetstories.data.source.local.PreferencesManager
import id.furqoncreative.jetstories.data.source.local.StoryItem
import id.furqoncreative.jetstories.utils.Async
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var storyRepository: NetworkGetAllStoriesWithPaginationRepository

    @Mock
    private lateinit var preferencesManager: PreferencesManager

    @Test
    fun `when get story then should not null and return data`() = runTest {
        val dummyStory = DataDummy.generateDummyStory()

        val data: PagingData<StoryItem> = StoryPagingResource.snapshot(dummyStory)
        val expectedStory = flowOf(Async.Success(data))
        Mockito.`when`(storyRepository.getAllStoriesWithPagination()).thenReturn(expectedStory)

        val homeViewModel = HomeViewModel(storyRepository, preferencesManager)
        val actualStory: Flow<PagingData<StoryItem>> = homeViewModel.uiState.value.stories!!

        val itemsSnapshot: List<StoryItem> = actualStory.asSnapshot {
            scrollTo(index = 100)
        }

        assertNotNull(itemsSnapshot)
        assertEquals(dummyStory.size, itemsSnapshot.size)
        assertEquals(dummyStory[0], itemsSnapshot[0])
    }

    @Test
    fun `when get empty story then should return no data`() = runTest {
        val data: PagingData<StoryItem> = StoryPagingResource.snapshot(emptyList())
        val expectedStory = flowOf(Async.Success(data))
        Mockito.`when`(storyRepository.getAllStoriesWithPagination()).thenReturn(expectedStory)

        val homeViewModel = HomeViewModel(storyRepository, preferencesManager)
        val actualStory: Flow<PagingData<StoryItem>> = homeViewModel.uiState.value.stories!!

        val itemsSnapshot: List<StoryItem> = actualStory.asSnapshot {
            scrollTo(index = 100)
        }

        assertNotNull(itemsSnapshot)
        assertEquals(0, itemsSnapshot.size)
    }
}

class StoryPagingResource : PagingSource<Int, LiveData<List<StoryItem>>>() {
    companion object {
        fun snapshot(items: List<StoryItem>): PagingData<StoryItem> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<StoryItem>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<StoryItem>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }

}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}