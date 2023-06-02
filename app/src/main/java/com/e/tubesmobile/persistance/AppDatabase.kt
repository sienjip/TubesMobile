package com.e.tubesmobile.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import com.e.tubesmobile.model.Komputer


@Database(entities = [Komputer::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun komputerDao() : KomputerDao
}