package com.app.orynarud_testtask_videolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.app.orynarud_testtask_videolist.navigation.TaskNavigation
import com.app.orynarud_testtask_videolist.ui.theme.OrynaRud_TestTask_VideoListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OrynaRud_TestTask_VideoListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TaskNavigation(paddingValues = innerPadding)
                }
            }
        }
    }
}
