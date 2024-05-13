package com.esgi.inspiration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.esgi.inspiration.network.TracksRepository

@Composable
internal fun TopSongScreen(tracksRepository: TracksRepository) {
    val trackState = tracksRepository.topTrackState
    val tracks = trackState.collectAsState(initial = emptyList())
    tracksRepository.updateTopTracks()

    LazyColumn (
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        if (tracks.value.isNotEmpty()) {
            item {
                Text (
                    text = "Top songs",
                    modifier = Modifier.padding(0.dp, 24.dp, 0.dp, 32.dp),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.W600
                )
            }

            items(tracks.value) { track ->
                Card (
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(0.dp, 8.dp)
                ){
                    Text (
                        text = track.name,
                        modifier = Modifier.padding(8.dp, 8.dp, 0.dp, 4.dp),
                        fontSize = 21.sp
                    )
                    Text (
                        text = "by " + track.artist,
                        modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 8.dp),
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp, 8.dp, 0.dp, 0.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            tracksRepository.updateTopTracks()
                        }) {
                        Text(
                            text = "Refresh",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(4.dp, 8.dp)
                        )
                    }
                }
            }
        } else {
            item {
                Text (
                    text = "Loading...",
                    modifier = Modifier.padding(0.dp, 24.dp, 0.dp, 32.dp),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.W600
                )
            }
        }
    }
}