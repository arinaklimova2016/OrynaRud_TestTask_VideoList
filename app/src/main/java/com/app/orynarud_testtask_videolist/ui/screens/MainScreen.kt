package com.app.orynarud_testtask_videolist.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.app.orynarud_testtask_videolist.constants.Constants.IMAGE_URL
import com.app.orynarud_testtask_videolist.navigation.ScreenRoutes
import com.app.orynarud_testtask_videolist.view_models.MainViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen(
    paddingValues: PaddingValues,
    navController: NavHostController,
    viewModel: MainViewModel = koinViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.downloadVideoList()
    }

    val videoList by viewModel.getVideoList().collectAsState(initial = emptyList())

    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = videoList, itemContent = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .background(Color.Gray, RoundedCornerShape(6.dp))
                        .clickable {
                            navController.navigate(
                                buildString {
                                    append(ScreenRoutes.FullScreen.route)
                                    append("/${it.uid}")
                                }
                            )
                        }
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(
                                buildString {
                                    append(IMAGE_URL)
                                    append(it.thumb)
                                }
                            )
                            .crossfade(true)
                            .build(),
                        contentDescription = it.thumb,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = it.title,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            })
        }
    }
}