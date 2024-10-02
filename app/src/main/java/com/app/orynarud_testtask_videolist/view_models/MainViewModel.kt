package com.app.orynarud_testtask_videolist.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.orynarud_testtask_videolist.model.MainRepository
import com.app.orynarud_testtask_videolist.model.local.room.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class MainViewModel(private val repo: MainRepository) : ViewModel() {
    private val _videoByUid = MutableLiveData<Video>()
    val videoByUid: LiveData<Video> = _videoByUid

    fun downloadVideoList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.downloadVideoList()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getVideoList(): Flow<List<Video>> {
        return repo.getVideoList()
    }

    fun getVideoByUid(uid: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _videoByUid.postValue(repo.getVideoByUid(uid))
        }
    }

    fun setPreviousVideo(uid: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            uid?.let {
                val videoList = repo.getVideoList().firstOrNull() ?: emptyList()
                val currentIndex = videoList.indexOfFirst { it.uid == uid }

                if (videoList.isNotEmpty()) {
                    val previousIndex =
                        if (currentIndex <= 0) videoList.size - 1 else currentIndex - 1
                    _videoByUid.postValue(videoList[previousIndex])
                }
            }
        }
    }

    fun setNextVideo(uid: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            uid?.let {
                val videoList = repo.getVideoList().firstOrNull() ?: emptyList()
                val currentIndex = videoList.indexOfFirst { it.uid == uid }

                if (videoList.isNotEmpty()) {
                    val nextIndex = if (currentIndex >= videoList.size - 1) 0 else currentIndex + 1
                    _videoByUid.postValue(videoList[nextIndex])
                }
            }
        }
    }
}