package com.mlombardi.marvelcharacters.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        MarvelCharacterEntity::class,
        MarvelComicEntity::class,
    ],
    version = 1
)

abstract class MarvelDatabase : RoomDatabase() {
    abstract fun getMarvelDao(): MarvelDao

    companion object {
        const val DB_NAME = "marvel.db"
    }
}