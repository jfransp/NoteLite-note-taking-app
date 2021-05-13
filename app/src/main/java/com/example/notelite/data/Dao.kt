package com.example.notelite.data

import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: NoteEntity)

    //Pulls notes from database according to the user preferences from datastore
    fun getNotes(query: String, sortOrder: SortOrder): kotlinx.coroutines.flow.Flow<List<NoteEntity>> =
        when (sortOrder) {
            SortOrder.BY_DATE -> getNotesByDate(query)
            SortOrder.BY_TITLE -> getNotesByTitle(query)
        }


    @Query("SELECT * FROM note_data WHERE text LIKE '%' || :searchQuery || '%' ORDER BY timeOfCreation DESC")
    fun getNotesByDate(searchQuery: String): kotlinx.coroutines.flow.Flow<List<NoteEntity>>

    @Query("SELECT * FROM note_data WHERE text LIKE '%' || :searchQuery || '%' ORDER BY title ASC")
    fun getNotesByTitle(searchQuery: String): kotlinx.coroutines.flow.Flow<List<NoteEntity>>

    @Query("DELETE FROM note_data WHERE id = :noteid")
    fun delete(noteid: Int)

    @Update
    fun updateNote(note: NoteEntity)

}