package fr.lbc.test.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.lbc.test.data.Album
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface AlbumDao {

    @Query("SELECT * FROM AlbumEntity WHERE id BETWEEN :from AND :to ORDER BY albumId ASC ")
    fun getAlbums(from: Int, to: Int): Single<List<AlbumEntity>>

    @Query("SELECT * FROM AlbumEntity WHERE id = :id")
    fun getAlbum(id: Int): Single<AlbumEntity>

    //Count Can Be Asynchronous if Huge table
    @Query("SELECT COUNT(id) FROM AlbumEntity")
    fun getAlbumCount(): Single<Int>

    //Insertion Can Be Asynchronous if Heavy Object
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(albumEntity: AlbumEntity)

    @Insert
    fun insertAll(albumEntity: List<AlbumEntity>): Completable

    @Query("SELECT * FROM AlbumEntity ORDER BY albumId ASC ")
    fun getAllAlbums(): Single<List<AlbumEntity>>

    @Query("DELETE FROM AlbumEntity")
    fun clearAlbums()
}