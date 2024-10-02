package com.app.orynarud_testtask_videolist.model.local

import com.app.orynarud_testtask_videolist.model.local.room.Video
import com.app.orynarud_testtask_videolist.model.local.room.VideoDao
import kotlinx.coroutines.flow.Flow

class VideoListLocalSourceImpl(private val videoDao: VideoDao) : VideoListLocalSource {
    override fun getVideoList(): Flow<List<Video>> {
        return videoDao.getAll()
    }

    override suspend fun getVideoByUid(uid: Int): Video {
        return videoDao.getVideoById(uid)
    }

    override suspend fun saveVideoList(videoList: List<Video>) {
        videoDao.insert(videoList)
    }

    override suspend fun clearDatabase() {
        videoDao.deleteAllVideos()
    }
}