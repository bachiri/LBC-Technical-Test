package fr.lbc.test.albums.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.lbc.test.albums.domain.AlbumPaginatorUseCase

import fr.lbc.test.albums.domain.AlbumSaverUseCase
import fr.lbc.test.data.Album
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class AlbumsViewModel @Inject constructor(
    private val albumSaverUseCase: AlbumSaverUseCase,
    private val albumPaginatorUseCase: AlbumPaginatorUseCase
) : ViewModel() {

    private val _albumList: MutableLiveData<AlbumsViewState> = MutableLiveData()
    val albumList: LiveData<AlbumsViewState>
        get() = _albumList

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean>
        get() = _loading

    private val albums: MutableList<Album> = mutableListOf()

    private var lastElement: Int = 0
    private var isEndReached: Boolean = false


    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        initViewModel()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun nextPage() {
        getAlbums(lastElement, lastElement + ALBUMS_PER_PAGE)
    }

    private fun getAlbums(from: Int, to: Int) {
        compositeDisposable.clear()
        _loading.postValue(true)
        val albumsPaginatorSingle = albumPaginatorUseCase.getNextAlbums(from, to)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { albumList ->
                    if (albumList.isEmpty()) {
                        isEndReached = true
                        _albumList.postValue(AlbumsOnError("End Reached"))
                    } else {
                        lastElement += albumList.size
                        albums.addAll(albumList)
                        _albumList.postValue(AlbumsOnSuccess(albums))
                    }
                    _loading.postValue(false)
                }, {
                    _loading.postValue(false)
                    _albumList.postValue(AlbumsOnError("Error Loading Albums"))
                }
            )

        compositeDisposable.add(albumsPaginatorSingle)
    }

    private fun initViewModel() {
        _loading.postValue(true)
        val checkAlbumSavedCompletable = albumSaverUseCase
            .getAlbumsCount()
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    if (it > 0) getAlbums(lastElement, lastElement + ALBUMS_PER_PAGE)
                    else downloadAndSaveAlbums()

                },
                {
                    downloadAndSaveAlbums()//Download And Save Albums If Error
                }
            )
        compositeDisposable.add(checkAlbumSavedCompletable)
    }

    private fun downloadAndSaveAlbums() {
        val albumSaverCompletable = albumSaverUseCase
            .downloadAndSaveAlbums()
            .subscribe({
                getAlbums(lastElement, lastElement + ALBUMS_PER_PAGE)
            },
                {
                    _albumList.postValue(AlbumsOnError("Error Loading Albums"))
                    _loading.postValue(false)
                })
        compositeDisposable.add(albumSaverCompletable)
    }

    //For Testing
    internal fun setLastElement(lastElement: Int) {
        this.lastElement = lastElement
    }
    internal fun getAlbumsList(): MutableList<Album> = albums

    companion object{
        val ALBUMS_PER_PAGE = 20
    }
}