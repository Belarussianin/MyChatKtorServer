package com.example.data.model

interface MessageDataSource {

    suspend fun getAllMessages(): List<Message>

    suspend fun insertMessage(message: Message)

    //Todo
    //suspend fun deleteMessage(message: Message)
}