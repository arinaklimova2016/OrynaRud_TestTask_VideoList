package com.app.orynarud_testtask_videolist.di

import com.app.orynarud_testtask_videolist.view_models.MainViewModel
import com.app.orynarud_testtask_videolist.view_models.PlayerViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        MainViewModel(get())
    }

    viewModel {
        PlayerViewModel()
    }
}
