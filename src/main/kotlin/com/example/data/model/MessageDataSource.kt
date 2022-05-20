package com.example.data.model

interface MessageDataSource {

    suspend fun getAllMessages(): List<Message>

    suspend fun insertMessage(message: Message)

    suspend fun deleteMessage(message: Message)

    suspend fun editMessage(message: Message)
}