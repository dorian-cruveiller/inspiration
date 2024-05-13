package com.esgi.inspiration.network

import android.util.Log
import com.esgi.inspiration.Constants
import com.esgi.inspiration.network.data.Track
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import org.json.JSONObject

class TracksApiDataSource {

    private val TAG: String = "TracksApiDataSource"

    private val client = HttpClient()

    suspend fun getRecommendSong(number: Int): List<Track> {

        var maxSongs = number

        if (maxSongs > 20) {
            maxSongs = 20
        }

        val topIdList = getTopSongsIds()

        var idString = ""

        for (id in topIdList) {
            idString = "$idString$id,"
        }
        // Log.d(TAG, "getRecommendSong: idString: " + idString)

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

        // Log.d(TAG, "getRecommendSong: response: " + response.bodyAsText())
        // Log.d(TAG, "getRecommendSong: response status: " + response.status)
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

    private suspend fun getTopSongsIds(): List<String> {

        val idList = mutableListOf<String>()

        val response = client.get(Constants.API_URI + "me/top/tracks") {
            headers {
                append(HttpHeaders.Authorization, "Bearer " + Constants.token)
            }

            url {
                parameters.append("time_range", "short_term")
                parameters.append("limit", "5")
            }
        }

        Log.d(TAG, "getTopSongsIds: response: " + response.bodyAsText())
        val responseJSON = JSONObject(response.bodyAsText())
        val tracks = responseJSON.getJSONArray("items")
        for (i in 0..<tracks.length()) {
            Log.d(TAG, "getTopSongsIds: id: " + tracks.getJSONObject(i).getString("id"))
            idList.add(tracks.getJSONObject(i).getString("id"))
        }

        return idList
    }

    suspend fun getTopSongs(number: Int): List<Track> {

        val trackList = mutableListOf<Track>()

        val response = client.get(Constants.API_URI + "me/top/tracks") {
            headers {
                append(HttpHeaders.Authorization, "Bearer " + Constants.token)
            }

            url {
                parameters.append("time_range", "short_term")
                parameters.append("limit", number.toString())
            }
        }

        // Log.d(TAG, "getTopSongsIds: response: " + response.bodyAsText())
        val responseJSON = JSONObject(response.bodyAsText())
        val tracks = responseJSON.getJSONArray("items")
        for (i in 0..<tracks.length()) {
            val name = tracks.getJSONObject(i).getString("name")
            val artist = tracks.getJSONObject(i).getJSONArray("artists").getJSONObject(0).getString("name")

            // Log.d(TAG, "getTopSongs: name: $name")
            // Log.d(TAG, "getTopSongs: artist: $artist")
            val track = Track(name, artist)
            trackList.add(track)
        }

        return trackList
    }
}