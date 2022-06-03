package com.example.room

import com.example.data.model.Message
import com.example.data.model.MessageDataSource
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class RoomController(
    private val messageDataSource: MessageDataSource
) {
    private val members = ConcurrentHashMap<String, Member>()

    fun onJoin(
        username: String,
        sessionId: String,
        socket: WebSocketSession
    ) {
        if (members.containsKey(username)) {
            throw MemberAlreadyExistException()
        }
        members[username] = Member(username, sessionId, socket)
    }

    suspend fun sendMessage(
        senderUsername: String,
        message: String
    ) {
        val messageEntity = Message(
            text = message,
            username = senderUsername,
            timestamp = System.currentTimeMillis()
        )
        sendMessage(messageEntity)
    }

    suspend fun sendMessage(message: Message) {
        messageDataSource.insertMessage(message)
        val parseMessage = Json.encodeToString(message)

        members.values.forEach { member ->
            member.socket.send(Frame.Text(parseMessage))
        }
    }

    suspend fun getAllMessages(): List<Message> {
        return messageDataSource.getAllMessages()
    }

    suspend fun tryDisconnect(username: String) {
        members[username]?.socket?.close()
        if (members.containsKey(username)) {
            members.remove(username)
        }
    }
}