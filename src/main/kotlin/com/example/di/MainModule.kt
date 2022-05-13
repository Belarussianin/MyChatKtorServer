package com.example.di

import com.example.data.model.MessageDataSource
import com.example.data.model.MongoMessageDataSourceImpl
import com.example.room.RoomController
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val mainModule = module {
    single {
        KMongo.createClient("mongodb://mongo:pE5gxtbPAOzZMCevN4As@containers-us-west-60.railway.app:6100")
            .coroutine
            .getDatabase("message_db")
    }

    single<MessageDataSource> {
        MongoMessageDataSourceImpl(get())
    }

    single {
        RoomController(get())
    }
}