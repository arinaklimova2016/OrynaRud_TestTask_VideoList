package com.app.orynarud_testtask_videolist.model.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.orynarud_testtask_videolist.utils.ListStringConverter

@Database(entities = [Video::class], version = 1)
@TypeConverters(ListStringConverter::class)
abstract class VideoDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDao
}