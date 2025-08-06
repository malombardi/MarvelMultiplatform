package com.mlombardi.marvelcharacters.core.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MarvelDao {

    @Query("SELECT COUNT(character_id) FROM marvelcharacterentity")
    suspend fun charactersCount(): Int

    @Query("SELECT COUNT(character_id) FROM marvelcharacterentity WHERE character_id LIKE '%' || :startsWith")
    suspend fun searchCount(startsWith: String): Int

    @Query("SELECT COUNT(comic_id) FROM marvelcomicentity")
    suspend fun comicsCount(): Int

    @Query("SELECT * FROM marvelcomicentity ORDER BY title ASC")
    fun getComics(): Flow<List<MarvelComicEntity>>

    @Query("SELECT * FROM marvelcharacterentity ORDER BY name ASC")
    fun getCharacters(): Flow<List<MarvelCharacterEntity>>

    @Query("SELECT * FROM marvelcharacterentity WHERE name LIKE :startsWith || '%' ORDER BY name ASC")
    fun getCharactersWithName(startsWith: String): Flow<List<MarvelCharacterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCharacter(charactersEntity: List<MarvelCharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveComic(comicsEntity: List<MarvelComicEntity>)
}
