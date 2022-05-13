package com.example.plugins

import com.example.room.RoomController
import com.example.routes.chatSocket
import com.example.routes.getAllMessages
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val roomController by inject<RoomController>()

    install(Routing) {
        get("/") {
            call.respond(HttpStatusCode.OK, "Hello from my server!")
        }
        chatSocket(roomController)
        getAllMessages(roomController)
    }
}
