package fr.lbc.test.di

import dagger.Binds
import dagger.Module
import fr.lbc.test.albums.domain.AlbumPaginatorUseCase
import fr.lbc.test.albums.domain.AlbumPaginatorUseCaseImpl
import fr.lbc.test.albums.domain.AlbumSaverUseCase
import fr.lbc.test.albums.domain.AlbumSaverUseCaseImpl

@Module
interface DomainModule {

    @Binds
    fun provideAlbumSaverUseCase(albumSaverUseCase: AlbumSaverUseCaseImpl): AlbumSaverUseCase

    @Binds
    fun provideAlbumPaginatorUseCase(albumPaginatorUseCase: AlbumPaginatorUseCaseImpl): AlbumPaginatorUseCase

}