package com.app.orynarud_testtask_videolist.model.remote

import com.app.orynarud_testtask_videolist.constants.Constants.DELETE_EXTRA_LINE
import com.app.orynarud_testtask_videolist.constants.Constants.EMPTY_STRING
import com.app.orynarud_testtask_videolist.model.dto.VideoDto
import com.app.orynarud_testtask_videolist.model.remote.retrofit.VideoApi
import com.google.gson.GsonBuilder
import com.google.gson.Strictness

class VideoListRemoteSourceImpl(private val videoApi: VideoApi) : VideoListRemoteSource {
    private val gson = GsonBuilder().setStrictness(Strictness.LENIENT).create()

    override suspend fun getVideoList(): VideoDto {
        val response = videoApi.getVideoList()
        val videoList = response
            .replace(Regex(DELETE_EXTRA_LINE), EMPTY_STRING)
            .trim()
            .dropLast(1)
        return gson.fromJson(videoList, VideoDto::class.java)
    }
}