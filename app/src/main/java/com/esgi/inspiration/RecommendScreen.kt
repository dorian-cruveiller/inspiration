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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.esgi.inspiration.network.Recommend
import com.esgi.inspiration.network.data.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
internal fun RecommendScreen(navController: NavHostController) {

    var items by remember { mutableStateOf(listOf(Track("Loading", "please wait"))) }
    var loaded by remember { mutableStateOf(false) }

    LaunchedEffect(null) {
        CoroutineScope(Dispatchers.Main).launch {
            items = Recommend().getRecommendSong(10)
            loaded = true
        }
    }

    LazyColumn (
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {

        if (loaded) {
            item {
                Text (
                    text = "Recommended songs",
                    modifier = Modifier.padding(0.dp, 24.dp, 0.dp, 32.dp),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.W600
                )
            }

            items(items) { item ->
                Card (
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentHeight()
                        .padding(0.dp, 8.dp)
                ){
                    Text (
                        text = item.name,
                        modifier = Modifier.padding(8.dp, 8.dp, 0.dp, 4.dp),
                        fontSize = 21.sp
                    )
                    Text (
                        text = "by " + item.artist,
                        modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 8.dp),
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
            }

            item {
                Column(
                    modifier = Modifier.fillMaxSize()
                        .padding(0.dp, 8.dp, 0.dp, 0.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            loaded = false
                            CoroutineScope(Dispatchers.Main).launch {
                                items = Recommend().getRecommendSong(10)
                                loaded = true
                            }
                        }) {
                        Text(
                            text = "Get More",
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