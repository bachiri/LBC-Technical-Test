package fr.lbc.test.di

import dagger.Module
import dagger.Provides
import fr.lbc.test.data.AlbumsDataSource
import fr.lbc.test.data.local.LocalAlbumsRepository
import fr.lbc.test.data.remote.RemoteAlbumsRepository
import fr.lbc.test.data.local.Local
import fr.lbc.test.data.remote.Remote
import fr.lbc.test.data.local.AlbumDao
import fr.lbc.test.data.remote.AlbumApi
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    @Remote
    internal fun provideRemoteDataSource(albumApi: AlbumApi): AlbumsDataSource {
        return RemoteAlbumsRepository(albumApi)
    }

    @Singleton
    @Provides
    @Local
    internal fun provideLocalDataSource(albumDao: AlbumDao): AlbumsDataSource {
        return LocalAlbumsRepository(albumDao)
    }

}