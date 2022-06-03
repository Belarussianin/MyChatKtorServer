package com.example.data.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.litote.kmongo.coroutine.CoroutineDatabase

class MongoMessageDataSourceImpl(
    private val db: CoroutineDatabase
) : MessageDataSource {

    private val messages = db.getCollection<Message>()

    override suspend fun getAllMessages(): List<Message> {
        return withContext(Dispatchers.IO) {
            messages.find()
                .descendingSort(Message::timestamp)
                .toList()
        }
    }

    override suspend fun insertMessage(message: Message) {

            messages.findOneAndReplace(message.id, message) ?: messages.insertOne(message)

    }

    override suspend fun deleteMessage(message: Message) {
        withContext(Dispatchers.IO) {
            messages.deleteOneById(message)
        }
    }
}