package fr.lbc.test.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import fr.lbc.test.AlbumApplication
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import utils.AlbumFactory


@RunWith(AndroidJUnit4::class)
class LocalAlbumsRepositoryTest {
    private lateinit var albumDatabase: AlbumDatabase

    @Before
    fun initDb() {
        albumDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<AlbumApplication>(),
            AlbumDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() {
        albumDatabase.close()
    }

    @Test
    fun when_Album_Inserted_Db_Should_Return_It() {
        val albumDao = albumDatabase.albumDao()
        albumDao.insert(AlbumFactory.getAlbum(1).toAlbumEntity())
        val album = albumDao.getAlbum(AlbumFactory.getAlbum(1).toAlbumEntity().id).blockingGet()
        assertNotNull(album)
    }

    @Test
    fun when_Album_Inserted_Count_Should_Be_Positive() {
        val albumDao = albumDatabase.albumDao()
        albumDao.insert(AlbumFactory.getAlbum(1).toAlbumEntity())
        val count = albumDao.getAlbumCount().blockingGet()
        assert(count > 0)
    }

    @Test
    fun when_Two_Albums_Inserted_And_Get_Albums_Range_Called_Should_Return_Albums() {
        val albumDao = albumDatabase.albumDao()
        albumDao.insert(AlbumFactory.getAlbum(1).toAlbumEntity())
        albumDao.insert(AlbumFactory.getAlbum(2).toAlbumEntity())
        val count = albumDao.getAlbums(1,2).blockingGet().size
        assert(count == 2)
    }

    @Test
    fun when_Albums_Inserted_Should_Return_All_Albums() {
        val albumDao = albumDatabase.albumDao()
        albumDao.insertAll(AlbumFactory.getAlbumsEntityList())
        val count = albumDao.getAllAlbums().blockingGet().size
        assert(count == 3)
    }

    @Test
    fun when_Delete_Albums_DB_Should_Be_Empty() {
        val albumDao = albumDatabase.albumDao()
        albumDao.insertAll(AlbumFactory.getAlbumsEntityList())
        albumDao.clearAlbums()
        val count = albumDao.getAlbumCount().blockingGet()
        assert(count == 0)
    }

}