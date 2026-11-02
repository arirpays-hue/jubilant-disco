package com.example.englishtutor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun TutorChatScreen(tutorBot: EnglishTutorBot) {
    var userInput by remember { mutableStateOf("") }
    var chatHistory by remember { mutableStateOf(listOf<Pair<String, String>>()) }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(chatHistory) { (sender, message) ->
                val isUser = sender == "You"
                Text(
                    text = "$sender: $message",
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = if (isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
                Divider(color = MaterialTheme.colorScheme.surfaceVariant)
            }
            if (isLoading) {
                item {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = userInput,
                onValueChange = { userInput = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type in English...") },
                enabled = !isLoading
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (userInput.isNotBlank()) {
                        val messageToSend = userInput
                        chatHistory = chatHistory + Pair("You", messageToSend)
                        userInput = ""
                        isLoading = true
                       
                        coroutineScope.launch {
                            val response = tutorBot.sendMessage(messageToSend)
                            chatHistory = chatHistory + Pair("Tutor", response)
                            isLoading = false
                        }
                    }
                },
                enabled = !isLoading && userInput.isNotBlank()
            ) {
                Text("Send")
            }
        }
    }
}
 
