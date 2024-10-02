package com.app.orynarud_testtask_videolist.model.remote

import com.app.orynarud_testtask_videolist.model.dto.VideoDto

interface VideoListRemoteSource {
    suspend fun getVideoList(): VideoDto
}