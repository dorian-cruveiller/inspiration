package com.esgi.inspiration.network.data

import kotlinx.serialization.Serializable

@Serializable
data class Token(val access_token: String, val scope: String, val token_type: String, val expires_in: String, val refresh_token: String) {

}