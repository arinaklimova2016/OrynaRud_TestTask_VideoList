package com.app.orynarud_testtask_videolist.model.local

import com.app.orynarud_testtask_videolist.model.local.room.Video
import kotlinx.coroutines.flow.Flow

interface VideoListLocalSource {
    fun getVideoList(): Flow<List<Video>>
    suspend fun getVideoByUid(uid: Int): Video
    suspend fun saveVideoList(videoList: List<Video>)
    suspend fun clearDatabase()
}