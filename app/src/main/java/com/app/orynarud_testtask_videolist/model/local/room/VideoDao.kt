package com.app.orynarud_testtask_videolist.model.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoDao {
    @Query("SELECT * FROM video")
    fun getAll(): Flow<List<Video>>

    @Query("SELECT * FROM video WHERE uid = :uid")
    suspend fun getVideoById(uid: Int): Video

    @Insert
    suspend fun insert(listVideo: List<Video>)

    @Query("DELETE FROM video")
    fun deleteAllVideos()
}