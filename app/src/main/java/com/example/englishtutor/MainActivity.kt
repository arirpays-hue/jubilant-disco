package com.example.englishtutor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // משיכת המפתח שנשמר ב-local.properties
        val apiKey = BuildConfig.GEMINI_API_KEY
        val tutorBot = EnglishTutorBot(apiKey)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TutorChatScreen(tutorBot = tutorBot)
                }
            }
        }
    }
}
