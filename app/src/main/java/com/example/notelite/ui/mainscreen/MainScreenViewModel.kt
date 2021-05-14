package com.example.notelite.ui.mainscreen

import androidx.lifecycle.*
import com.example.notelite.data.Dao
import com.example.notelite.data.NoteEntity
import com.example.notelite.data.PreferencesManager
import com.example.notelite.data.SortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val dao: Dao,
    private val preferencesManager: PreferencesManager,
    private val applicationScope: CoroutineScope
): ViewModel() {

    //Mutable Flow to be changed according to the input on the main screen's search view
    val searchQuery = MutableStateFlow("")

    //Reference to the flow that changes according to changes on the preferences datastore
    private val preferencesFlow = preferencesManager.preferencesFlow

    //Combines both the search query and the preferences data, pulling the data from the database
    //accordingly
    private val notesFlow = combine(
        searchQuery,
        preferencesFlow
    ) { query, filterPreferences ->
        Pair(query, filterPreferences)
    }.flatMapLatest { (query, filterPreferences) ->
        dao.getNotes(query, filterPreferences.sortOrder)
    }

    //Converts notes data and searchQuery into livedata for appropriate lifecycle awareness
    val notes = notesFlow.asLiveData()
    val searchquery = searchQuery.asLiveData()

    //Updates sort order on datastore
    fun onSortOrderSelected(sortOrder: SortOrder) = viewModelScope.launch {
        preferencesManager.updateSortOrder(sortOrder)
    }

    //Deletes the given note item from database
    fun deleteNote(note: NoteEntity)  = applicationScope.launch {
        val noteid = note.id
        dao.delete(noteid)
    }

}
