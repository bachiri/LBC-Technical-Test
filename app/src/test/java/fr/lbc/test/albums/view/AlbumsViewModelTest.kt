package fr.lbc.test.albums.view


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import fr.lbc.test.albums.domain.AlbumPaginatorUseCase
import fr.lbc.test.albums.domain.AlbumSaverUseCase
import fr.lbc.test.albums.view.AlbumsViewModel.Companion.ALBUMS_PER_PAGE
import fr.lbc.test.data.Album
import fr.lbc.test.utils.TrampolineSchedulerRule
import io.reactivex.Completable
import io.reactivex.Single
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.Rule

import org.junit.Test
import utils.TestAlbumFactory
import java.lang.Exception


class AlbumsViewModelTest {

    lateinit var albumSaverUseCase: AlbumSaverUseCase
    lateinit var albumPaginatorUseCase: AlbumPaginatorUseCase
    lateinit var albumsViewModel: AlbumsViewModel
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var trampolineScheduler = TrampolineSchedulerRule() //Synchronous Schedulers



    @Test
    fun `When Creating ViewModel With Local Albums Already Saved Local Albums Should Return`() {

        initMockedViewModel(Single.just(2), TestAlbumFactory.getAlbumsListSingle())
        //Verify getAlbumsCount is called
        verify(albumSaverUseCase).getAlbumsCount()
        //Verify getNextAlbums is called
        verify(albumPaginatorUseCase).getNextAlbums(any(), any())

        //Verify albumList LiveData is notified
        assertThat(albumsViewModel.albumList.value, instanceOf(AlbumsOnSuccess::class.java))
        //Verify List Size is not empty
        assert(albumsViewModel.getAlbumsList().size > 0)
        //Verify Loading Live Data is notified
        assert(albumsViewModel.loading.value == false)
    }



    @Test
    fun `When Creating ViewModel With Empty Local Albums Album Saver Use Case To The Rescue`() {
        albumSaverUseCase = mock()
        albumPaginatorUseCase = mock()
        whenever(albumSaverUseCase.getAlbumsCount()).thenReturn(Single.just(0))
        whenever(albumPaginatorUseCase.getNextAlbums(any(), any())).thenReturn(TestAlbumFactory.getAlbumsListSingle())

        whenever(albumSaverUseCase.downloadAndSaveAlbums()).thenReturn(Completable.complete())

        albumsViewModel = AlbumsViewModel(albumSaverUseCase, albumPaginatorUseCase)
        //Verify getAlbumsCount is called
        verify(albumSaverUseCase).getAlbumsCount()
        //Verify getNextAlbums is called
        verify(albumSaverUseCase).downloadAndSaveAlbums()
        //Verify albumList LiveData is notified
        assertThat(albumsViewModel.albumList.value, instanceOf(AlbumsOnSuccess::class.java))
    }


    @Test
    fun `When Creating ViewModel And Returned List Is Empty AlbumsOnError Expected in albumList LiveData`() {

        initMockedViewModel(Single.just(2), Single.just(listOf()))

        verify(albumSaverUseCase).getAlbumsCount()
        verify(albumPaginatorUseCase).getNextAlbums(any(), any())
        assertThat(albumsViewModel.albumList.value, instanceOf(AlbumsOnError::class.java))
        assert(albumsViewModel.loading.value == false)
    }

    @Test
    fun `When Creating ViewModel And Returned Result is An Excption LiveData observer should receive AlbumsOnError`() {

        initMockedViewModel(Single.just(2), Single.error(Exception("Error Loading Albums")))

        verify(albumSaverUseCase).getAlbumsCount()
        verify(albumPaginatorUseCase).getNextAlbums(any(), any())
        assertThat(albumsViewModel.albumList.value, instanceOf(AlbumsOnError::class.java))
        assert(albumsViewModel.loading.value == false)
        val albumsOnError: AlbumsOnError = albumsViewModel.albumList.value as AlbumsOnError
        assert(albumsOnError.error == "Error Loading Albums")
    }


    @Test
    fun `When Next Page Is Called Get From Last Element Plus ALBUMS_PER_PAGE`() {

        initMockedViewModel(Single.just(100), Single.just(listOf()))

        albumsViewModel = AlbumsViewModel(albumSaverUseCase, albumPaginatorUseCase)
        val lastElement = 20
        albumsViewModel.setLastElement(lastElement)
        albumsViewModel.nextPage()
        verify(albumPaginatorUseCase).getNextAlbums(lastElement, lastElement + ALBUMS_PER_PAGE)
    }



    private fun initMockedViewModel(just: Single<Int>, albumsListSingle: Single<List<Album>>) {
        albumSaverUseCase = mock()
        albumPaginatorUseCase = mock()
        whenever(albumSaverUseCase.getAlbumsCount()).thenReturn(just)
        whenever(albumPaginatorUseCase.getNextAlbums(any(), any())).thenReturn(albumsListSingle)
        albumsViewModel = AlbumsViewModel(albumSaverUseCase, albumPaginatorUseCase)
    }

}