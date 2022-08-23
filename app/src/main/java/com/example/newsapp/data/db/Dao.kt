package com.example.newsapp.data.db

import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {
    @Query("SELECT * FROM newentity")
    suspend fun getAll(): List<NewEntity>

    @Query("SELECT * FROM newentity WHERE title LIKE :first AND " +
            "subtitle LIKE :last LIMIT 1")
    suspend fun findByName(first: String, last: String): NewEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg newEntity: NewEntity)

    @Delete
    suspend fun delete(user: NewEntity)
}