package com.app.orynarud_testtask_videolist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.app.orynarud_testtask_videolist.R
import com.app.orynarud_testtask_videolist.view_models.MainViewModel
import com.app.orynarud_testtask_videolist.view_models.PlayerViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FullScreen(
    videoUid: Int,
    paddingValues: PaddingValues,
    viewModel: MainViewModel = koinViewModel(),
    playerViewModel: PlayerViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val player by playerViewModel.playerState.collectAsState()
    val video by viewModel.videoByUid.observeAsState()
    val errorMessage by playerViewModel.errorMessage.collectAsState()
    val isBuffering by playerViewModel.isBuffering.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getVideoByUid(videoUid)
        playerViewModel.initializePlayer(context)
    }

    LaunchedEffect(video) {
        playerViewModel.setMediaItem(video?.sources?.firstOrNull() ?: "")
    }

    DisposableEffect(Unit) {
        onDispose {
            playerViewModel.savePlayerState()
            playerViewModel.releasePlayer()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Media3AndroidView(player, isBuffering)
        errorMessage?.let { Text(text = it) }
        PlayerButtons(
            onClickPrevious = {
                viewModel.setPreviousVideo(video?.uid)
                playerViewModel.resetCurrentPosition()
            },
            onClickRewind = { player?.seekTo(player?.currentPosition?.minus(10_000) ?: 0) },
            onClickPlay = { player?.playWhenReady = true },
            onClickPause = { player?.playWhenReady = false },
            onClickForward = { player?.seekTo(player?.currentPosition?.plus(10_000) ?: 0) },
            onClickNext = {
                viewModel.setNextVideo(video?.uid)
                playerViewModel.resetCurrentPosition()
            }
        )
    }
}

@Composable
fun Media3AndroidView(player: ExoPlayer?, isBuffering: Boolean) {
    Box(modifier = Modifier.fillMaxWidth()) {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                PlayerView(context).apply {
                    this.player = player
                    keepScreenOn = true
                }
            },
            update = { playerView ->
                playerView.player = player
            }
        )
        if (isBuffering)
            CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}

@Composable
fun PlayerButtons(
    onClickPrevious: () -> Unit,
    onClickRewind: () -> Unit,
    onClickPlay: () -> Unit,
    onClickPause: () -> Unit,
    onClickForward: () -> Unit,
    onClickNext: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconPlayerButton(icon = R.drawable.ic_arrow_left) {
            onClickPrevious()
        }
        IconPlayerButton(icon = R.drawable.ic_rewind_10_seconds) {
            onClickRewind()
        }
        IconPlayerButton(icon = R.drawable.ic_play) {
            onClickPlay()
        }
        IconPlayerButton(icon = R.drawable.ic_pause) {
            onClickPause()
        }
        IconPlayerButton(icon = R.drawable.ic_forward_10_seconds) {
            onClickForward()
        }
        IconPlayerButton(icon = R.drawable.ic_arrow_right) {
            onClickNext()
        }
    }
}

@Composable
fun IconPlayerButton(icon: Int, onClick: () -> Unit) {
    IconButton(onClick = { onClick() }) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = icon.toString(),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(8.dp)
        )
    }
}
