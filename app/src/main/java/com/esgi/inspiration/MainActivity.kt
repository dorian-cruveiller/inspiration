package com.esgi.inspiration

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.esgi.inspiration.network.TokenManager
import com.esgi.inspiration.ui.theme.InspirationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var default = "welcome"

        val token = TokenManager(applicationContext).getToken()

        if (token != null) {
            default = "choose"
            Constants.token = token
        }

        setContent {
            InspirationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RootNavHost(default)
                }
            }
        }
    }
}