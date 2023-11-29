package id.furqoncreative.jetstories.data.source.remotemediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import id.furqoncreative.jetstories.data.source.local.StoryDatabase
import id.furqoncreative.jetstories.data.source.local.StoryItem
import id.furqoncreative.jetstories.data.source.network.JetstoriesApiService
import id.furqoncreative.jetstories.fake.service.FakeJetstoriesApiService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class StoryRemoteMediatorTest {
    private var mocKApi: JetstoriesApiService = FakeJetstoriesApiService()
    private var mockDb: StoryDatabase = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        StoryDatabase::class.java
    ).allowMainThreadQueries().build()

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        val remoteMediator = StoryRemoteMediator(
            mockDb,
            mocKApi,
        )
        val pagingState = PagingState<Int, StoryItem>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

}