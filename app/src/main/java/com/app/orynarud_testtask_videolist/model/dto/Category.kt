package com.app.orynarud_testtask_videolist.model.dto

import com.app.orynarud_testtask_videolist.model.local.room.Video

data class Category(
    val name: String,
    val videos: List<Video>
)