package com.esgi.inspiration.network
import android.util.Log
import com.esgi.inspiration.Constants
import com.esgi.inspiration.network.data.Track
import io.ktor.client.HttpClient
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import org.json.JSONObject

class Recommend {

    val TAG: String = "Recommend"
    val client = HttpClient()

    suspend fun getRecommendSong(number: Int): List<Track> {

        var maxSongs = number

        if (maxSongs > 20) {
            maxSongs = 20
        }

        val topIdList = getTopSongsIds(number)

        var idString = ""

        for (id in topIdList) {
            idString = "$idString$id,"
        }
        Log.d(TAG, "getRecommendSong: idString: " + idString)

        val response = client.get(Constants.API_URI + "recommendations") {
            headers {
                append(HttpHeaders.Authorization, "Bearer " + Constants.token)
            }

            url {
                parameters.append("market", "FR")
                parameters.append("seed_tracks", idString)
                parameters.append("limit", maxSongs.toString())
            }
        }

        Log.d(TAG, "getRecommendSong: response: " + response.bodyAsText())
        val tracksJSON = JSONObject(response.bodyAsText()).getJSONArray("tracks")

        val recommendTracks = mutableListOf<Track>()
        for (i in 0..<tracksJSON.length()) {
            val trackJSON = tracksJSON.getJSONObject(i)
            val artistJSON = trackJSON.getJSONArray("artists").getJSONObject(0)

            val track = Track(trackJSON.getString("name"), artistJSON.getString("name"))
            recommendTracks.add(track)
        }

        return recommendTracks
    }

    suspend fun getTopSongsIds(maxSongs: Int): List<String> {

        val idList = mutableListOf<String>()

        val response = client.get(Constants.API_URI + "me/top/tracks") {
            headers {
                append(HttpHeaders.Authorization, "Bearer " + Constants.token)
            }

            url {
                parameters.append("time_range", "short_term")
                parameters.append("limit", maxSongs.toString())
            }
        }

        Log.d(TAG, "getRecommendSong: response: " + response.bodyAsText())
        val responseJSON = JSONObject(response.bodyAsText())
        val tracks = responseJSON.getJSONArray("items")
        for (i in 0..<tracks.length()) {
            Log.d(TAG, "getRecommendSong: id: " + tracks.getJSONObject(i).getString("id"))
            idList.add(tracks.getJSONObject(i).getString("id"))
        }

        return idList
    }
}