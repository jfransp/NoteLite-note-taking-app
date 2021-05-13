package com.example.notelite.ui.newnotescreen

import androidx.lifecycle.ViewModel
import com.example.notelite.data.Dao
import com.example.notelite.data.NoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewNoteScreenViewModel @Inject constructor(
    val dao: Dao,
    val applicationScope: CoroutineScope
): ViewModel() {

    //Calls add note function from dao inside a coroutine
    fun addNewNote(note: NoteEntity) = applicationScope.launch {
        dao.addNote(note)
    }

}
