package com.example.flo

import androidx.room.*


@Dao
interface AlbumDao {
    @Insert
    fun insert(album: Album)

    @Update
    fun update(album: Album)

    @Delete
    fun delete(album: Album)

    @Query("SELECT * FROM AlbumTable") // 테이블의 모든 값을 가져와라
    fun getAlbums(): List<Album>

    @Query("SELECT * FROM AlbumTable WHERE id = :id")
    fun getAlbum(id: Int): Album

    @Insert
    fun likeAlbum(like: Like)

    @Query("select id from LikeTable where userId = :userId and albumId = :albumId")
    fun isLikeAlbum(userId : Int, albumId : Int): Int?

    @Query("Delete from LikeTable where userId = :userId and albumId = :albumId")
    fun disLikeAlbum(userId : Int, albumId : Int): Int

    @Query("select AT.* from LikeTable as LT left join AlbumTable as AT on LT.albumId = AT.id where LT.userId =:userId")
    fun getLikedAlbums(userId:Int) : List<Album>
}