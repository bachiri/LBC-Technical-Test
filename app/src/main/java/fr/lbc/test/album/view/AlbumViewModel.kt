package fr.lbc.test.album.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.lbc.test.album.domain.AlbumUseCase
import fr.lbc.test.data.Album
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AlbumViewModel @Inject constructor(private val albumUseCase: AlbumUseCase) : ViewModel() {

    private val NO_ALBUM: Int = -1

    private val _album: MutableLiveData<AlbumViewState> = MutableLiveData()
    val album: LiveData<AlbumViewState>
        get() = _album

    private val _errorVisibility: MutableLiveData<Boolean> = MutableLiveData()
    val errorVisibility: LiveData<Boolean>
        get() = _errorVisibility

    private var currentLoadedAlbum: Int = NO_ALBUM

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun start(id: Int) {
        if (id == currentLoadedAlbum) {
            return
        }
        val getAlbum = albumUseCase.getAlbumById(id)
            .subscribeOn(Schedulers.io())
            .subscribe({
                _album.postValue(AlbumOnSuccess(it))
                currentLoadedAlbum = it.id
                _errorVisibility.postValue(false)

            }, {
                _album.postValue(AlbumOnError("Internal 404, Album Not Found "))
                _errorVisibility.postValue(true)
                currentLoadedAlbum = NO_ALBUM
            })
        compositeDisposable.add(getAlbum)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}

sealed class AlbumViewState
data class AlbumOnSuccess(var data: Album) : AlbumViewState()
data class AlbumOnError(var error: String) : AlbumViewState()