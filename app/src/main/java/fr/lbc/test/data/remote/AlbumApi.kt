package fr.lbc.test.data.remote

import fr.lbc.test.data.Album
import io.reactivex.Single

import retrofit2.http.GET

interface AlbumApi {

    @GET("img/shared/technical-test.json")
    fun loadAlbums(): Single<List<Album>>
}