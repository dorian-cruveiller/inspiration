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
    private var _recommendedTrackState = MutableStateFlow(listOf<Track>())
    private var _topTrackState = MutableStateFlow(listOf<Track>());
    var recommendedTrackState = _recommendedTrackState
    var topTrackState = _topTrackState

    private suspend fun fetchRecommendedTracks(): List<Track> = tracksApiDataSource.getRecommendSong(10)

    private suspend fun fetchTopTracks(): List<Track> = tracksApiDataSource.getTopSongs(10);

    fun updateRecommendedTracks() {
        coroutineScope.launch {
            _recommendedTrackState.update { fetchRecommendedTracks() }
        }
    }

    fun updateTopTracks() {
        coroutineScope.launch {
            _topTrackState.update { fetchTopTracks() }
        }
    }
}