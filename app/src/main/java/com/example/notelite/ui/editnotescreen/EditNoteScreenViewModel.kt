package com.example.notelite.ui.editnotescreen

import androidx.lifecycle.ViewModel
import com.example.notelite.data.Dao
import com.example.notelite.data.NoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteScreenViewModel @Inject constructor(
    private val dao: Dao,
    private val applicationScope: CoroutineScope
): ViewModel() {

    //Calls update function from dao inside a coroutine
    fun updateNote(note: NoteEntity) = applicationScope.launch {
        dao.updateNote(note)
    }

}