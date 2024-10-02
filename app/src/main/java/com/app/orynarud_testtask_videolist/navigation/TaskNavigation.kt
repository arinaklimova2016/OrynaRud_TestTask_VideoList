package com.app.orynarud_testtask_videolist.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.orynarud_testtask_videolist.constants.Constants.KEY_VIDEO_UID
import com.app.orynarud_testtask_videolist.ui.screens.FullScreen
import com.app.orynarud_testtask_videolist.ui.screens.MainScreen

@Composable
fun TaskNavigation(paddingValues: PaddingValues) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.MainScreen.route
    ) {
        composable(ScreenRoutes.MainScreen.route) {
            MainScreen(paddingValues, navController)
        }

        composable(
            route = buildString {
                append(ScreenRoutes.FullScreen.route)
                append("/{$KEY_VIDEO_UID}")
            },
            arguments = listOf(navArgument(KEY_VIDEO_UID) { type = NavType.IntType })
        ) {
            FullScreen(
                videoUid = it.arguments?.getInt(KEY_VIDEO_UID) ?: 0,
                paddingValues
            )
        }
    }
}