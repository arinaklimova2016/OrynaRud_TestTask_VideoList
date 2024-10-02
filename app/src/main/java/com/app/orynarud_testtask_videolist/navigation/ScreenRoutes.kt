package com.app.orynarud_testtask_videolist.navigation

import com.app.orynarud_testtask_videolist.constants.Constants.FULL_SCREEN
import com.app.orynarud_testtask_videolist.constants.Constants.MAIN_SCREEN

sealed class ScreenRoutes(val route: String) {
    data object MainScreen : ScreenRoutes(MAIN_SCREEN)
    data object FullScreen : ScreenRoutes(FULL_SCREEN)
}