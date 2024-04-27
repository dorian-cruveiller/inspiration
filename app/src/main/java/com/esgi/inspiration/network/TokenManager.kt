package com.esgi.inspiration.network

import android.content.Context

class TokenManager(context: Context) {

    companion object {
        private const val PREFS_NAME = "inspiration"
        private const val TOKEN_KEY = "token"
    }

    private val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString(TOKEN_KEY, token)
        editor
        editor.apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }
}