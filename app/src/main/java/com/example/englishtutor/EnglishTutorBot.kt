package com.example.englishtutor

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content

class EnglishTutorBot(apiKey: String) {
    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = apiKey,
        systemInstruction = content {
            text("You are an expert English tutor. Engage in natural conversation with the user to help them practice English. " +
                 "If the user makes a grammar, syntax, or vocabulary mistake, gently correct them first, briefly explain why, " +
                 "and then continue the conversation naturally. Keep responses concise and suitable for an intermediate learner.")
        }
    )

    private val chat = generativeModel.startChat()

    suspend fun sendMessage(userMessage: String): String {
        return try {
            val response = chat.sendMessage(userMessage)
            response.text ?: "Error: Did not receive a response from the tutor."
        } catch (e: Exception) {
            "Connection error: ${e.localizedMessage}"
        }
    }
}
