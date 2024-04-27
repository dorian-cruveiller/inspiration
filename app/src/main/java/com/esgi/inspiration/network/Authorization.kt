package com.esgi.inspiration.network

import android.content.Context
import android.util.Log
import com.esgi.inspiration.Constants
import com.esgi.inspiration.network.data.Token
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.util.Base64
import java.util.Random

class Authorization {

    private val TAG: String = "Authorization"

    private fun toBase64(string: String): String {
        return Base64.getEncoder().encodeToString(string.toByteArray())
            .replace("=", "")
            .replace("+", "-")
            .replace("\\", "_")
    }

    fun getLoginUrl(): String {
        return "https://accounts.spotify.com/authorize?client_id=" + Constants.API_CLIENT + "&response_type=code&redirect_uri=https://127.0.0.1&scope=user-top-read"
    }

    suspend fun getToken(code: String, context: Context): String {

        val client = HttpClient() {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
        val response = client.submitForm(
            url = "https://accounts.spotify.com/api/token",
            formParameters = parameters {
                append("grant_type", "authorization_code")
                append("code", code)
                append("redirect_uri", "https://127.0.0.1")
            }
        ) {
            headers {
                append(HttpHeaders.ContentType, "application/x-www-form-urlencoded")
                append(HttpHeaders.Authorization, "Basic " + toBase64(Constants.API_CLIENT + ':' + Constants.API_SECRET))
            }
        }

        val token: Token = response.body()
        TokenManager(context).saveToken(token.access_token)

        Log.d(TAG, "getToken: token: " + token.access_token)

        return token.access_token
    }
}