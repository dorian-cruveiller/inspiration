package com.esgi.inspiration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.esgi.inspiration.network.Authorization
import com.esgi.inspiration.network.Recommend
import com.esgi.inspiration.network.data.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
internal fun RecommendScreen(navController: NavHostController) {

    var items by remember {
        mutableStateOf(listOf(Track("Coucou", "coucouArtst"), Track("Coucou", "coucouArtst")))
    }

    LaunchedEffect(null) {
        CoroutineScope(Dispatchers.Main).launch {
            items = Recommend().getRecommendSong(5)
        }
    }

    LazyColumn (

    ) {
        items(items) { item ->
            Text (
                text = item.name,
                modifier = Modifier.padding(16.dp)
            )
            Text (
                text = item.artist,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}