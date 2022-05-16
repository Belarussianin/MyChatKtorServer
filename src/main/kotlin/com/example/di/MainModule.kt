package com.example.di

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import com.example.data.model.MessageDataSource
import com.example.data.model.MongoMessageDataSourceImpl
import com.example.room.RoomController
import org.koin.dsl.module
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import org.slf4j.LoggerFactory

val mainModule = module {
    single<CoroutineClient> {
        val loggerContext = LoggerFactory.getILoggerFactory() as LoggerContext
        val rootLogger = loggerContext.getLogger("org.mongodb.driver")
        rootLogger.level = Level.OFF
        KMongo.createClient("mongodb://mongo:pE5gxtbPAOzZMCevN4As@containers-us-west-60.railway.app:6100").coroutine
    }

    single<CoroutineDatabase> {
        get<CoroutineClient>().getDatabase("message_db")
    }

    single<MessageDataSource> {
        MongoMessageDataSourceImpl(get())
    }

    single {
        RoomController(get())
    }
}