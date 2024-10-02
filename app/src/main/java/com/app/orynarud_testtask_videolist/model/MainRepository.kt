package com.app.orynarud_testtask_videolist.model

import com.app.orynarud_testtask_videolist.model.local.VideoListLocalSource
import com.app.orynarud_testtask_videolist.model.local.room.Video
import com.app.orynarud_testtask_videolist.model.remote.VideoListRemoteSource
import kotlinx.coroutines.flow.Flow

class MainRepository(
    private val localSource: VideoListLocalSource,
    private val remoteSource: VideoListRemoteSource
) {
    suspend fun downloadVideoList() {
        clearDatabase()
        saveVideoDataToDatabase(getRemoteVideoList())
    }

    fun getVideoList(): Flow<List<Video>> {
        return localSource.getVideoList()
    }

    suspend fun getVideoByUid(uid: Int): Video {
        return localSource.getVideoByUid(uid)
    }

    private suspend fun getRemoteVideoList(): List<Video> {
        return remoteSource.getVideoList().categories.firstOrNull()?.videos ?: emptyList()
    }

    private suspend fun saveVideoDataToDatabase(videoList: List<Video>) {
        localSource.saveVideoList(videoList)
    }

    private suspend fun clearDatabase() {
        localSource.clearDatabase()
    }
}