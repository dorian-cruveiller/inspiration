package com.esgi.inspiration.network

import com.esgi.inspiration.network.data.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TracksRepository {
    private val tracksApiDataSource = TracksApiDataSource()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var _trackState = MutableStateFlow(listOf<Track>())
    var trackState = _trackState

    init {
        updateTracks()
    }

    private suspend fun fetchTracks(): List<Track> = tracksApiDataSource.getRecommendSong(10)

    fun updateTracks() {
        coroutineScope.launch {
            _trackState.update {fetchTracks() }
        }
    }
}