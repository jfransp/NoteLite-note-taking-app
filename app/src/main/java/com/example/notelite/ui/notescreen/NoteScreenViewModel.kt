package com.example.notelite.ui.notescreen

import androidx.lifecycle.ViewModel
import com.example.notelite.data.Dao
import com.example.notelite.data.NoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteScreenViewModel @Inject constructor(
    private val dao: Dao,
    private val applicationScope: CoroutineScope,
): ViewModel() {

    //Calls the delete function from the dao inside a coroutine
    fun deleteNote(note: NoteEntity)  = applicationScope.launch {
        val noteid = note.id
        dao.delete(noteid)
    }

}