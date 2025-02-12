package com.example.msgapp2.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.msgapp2.data.local.dao.MessageDao
import com.example.msgapp2.model.Message

@Database(entities = [Message::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun messageDao(): MessageDao

}