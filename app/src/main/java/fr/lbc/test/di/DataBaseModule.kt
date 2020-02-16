package fr.lbc.test.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import fr.lbc.test.data.local.AlbumDao
import fr.lbc.test.data.local.AlbumDatabase
import javax.inject.Singleton

@Module
class DataBaseModule {


    private val DB_NAME = "fr.lbc.album"

    @Provides
    @Singleton
    fun provideAlbumDataBase(context: Context): AlbumDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AlbumDatabase::class.java, DB_NAME
        ).build()
    }


    @Provides
    @Singleton
    fun provideAlbumDao(albumDatabase: AlbumDatabase): AlbumDao {
        return albumDatabase.albumDao()
    }


}