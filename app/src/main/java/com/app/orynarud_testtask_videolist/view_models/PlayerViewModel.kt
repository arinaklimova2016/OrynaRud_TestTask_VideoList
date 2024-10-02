package com.app.orynarud_testtask_videolist.view_models

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlayerViewModel : ViewModel() {

    private val _playerState = MutableStateFlow<ExoPlayer?>(null)
    val playerState: StateFlow<ExoPlayer?> = _playerState

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isBuffering = MutableStateFlow(false)
    val isBuffering: StateFlow<Boolean> = _isBuffering

    private var currentPosition: Long = 0L

    fun initializePlayer(context: Context) {
        if (_playerState.value == null) {
            viewModelScope.launch {
                val exoPlayer = ExoPlayer.Builder(context).build().also {
                    it.prepare()
                    it.playWhenReady = true
                    it.addListener(object : Player.Listener {

                        override fun onPlaybackStateChanged(state: Int) {
                            when (state) {
                                ExoPlayer.STATE_READY -> {
                                    _errorMessage.value = null
                                    _isBuffering.value = false
                                }

                                ExoPlayer.STATE_IDLE -> {
                                    _errorMessage.value = null
                                    _isBuffering.value = false
                                }

                                ExoPlayer.STATE_BUFFERING -> {
                                    _errorMessage.value = null
                                    _isBuffering.value = true
                                }

                                ExoPlayer.STATE_ENDED -> {
                                    _errorMessage.value = null
                                    _isBuffering.value = false
                                }
                            }
                        }

                        override fun onPlayerError(error: PlaybackException) {
                            handleError(error)
                        }
                    })
                }
                _playerState.value = exoPlayer
            }
        }
    }

    fun resetCurrentPosition() {
        currentPosition = 0L
    }

    fun setMediaItem(videoUrl: String) {
        if (videoUrl.isNotEmpty()) {
            _playerState.value?.setMediaItem(MediaItem.fromUri(videoUrl))
            _playerState.value?.seekTo(currentPosition)
        }
    }

    fun savePlayerState() {
        _playerState.value?.let {
            currentPosition = it.currentPosition
        }
    }

    fun releasePlayer() {
        _playerState.value?.release()
        _playerState.value = null
        _errorMessage.value = null
        _isBuffering.value = false
    }

    private fun handleError(error: PlaybackException) {
        _errorMessage.value = when (error.errorCode) {
            PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED -> {
                "Network connection failed"
            }

            PlaybackException.ERROR_CODE_IO_FILE_NOT_FOUND -> {
                "File not found"
            }

            PlaybackException.ERROR_CODE_DECODER_INIT_FAILED -> {
                "Decoder initialization failed"
            }

            else -> {
                "An unknown error occurred: ${error.message}"
            }
        }
    }
}