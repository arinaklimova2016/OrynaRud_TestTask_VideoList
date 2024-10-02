package com.app.orynarud_testtask_videolist.di

import androidx.room.Room
import com.app.orynarud_testtask_videolist.constants.Constants
import com.app.orynarud_testtask_videolist.constants.Constants.DATABASE_NAME
import com.app.orynarud_testtask_videolist.model.MainRepository
import com.app.orynarud_testtask_videolist.model.local.VideoListLocalSource
import com.app.orynarud_testtask_videolist.model.local.VideoListLocalSourceImpl
import com.app.orynarud_testtask_videolist.model.local.room.VideoDao
import com.app.orynarud_testtask_videolist.model.local.room.VideoDatabase
import com.app.orynarud_testtask_videolist.model.remote.VideoListRemoteSource
import com.app.orynarud_testtask_videolist.model.remote.VideoListRemoteSourceImpl
import com.app.orynarud_testtask_videolist.model.remote.retrofit.VideoApi
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

val dataModule = module {

    single {
        MainRepository(get(), get())
    }

    factory<VideoListLocalSource> {
        VideoListLocalSourceImpl(get())
    }

    factory<VideoListRemoteSource> {
        VideoListRemoteSourceImpl(get())
    }

    factory {
        Room.databaseBuilder(
            androidContext(),
            VideoDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    factory {
        provideTaskDao(get())
    }

    factory {
        provideVideoApi(get())
    }

    factory {
        createRetrofit()
    }
}

private fun provideTaskDao(database: VideoDatabase): VideoDao {
    return database.videoDao()
}

private fun provideVideoApi(retrofit: Retrofit): VideoApi {
    return retrofit.create(VideoApi::class.java)
}

private fun createRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
}
