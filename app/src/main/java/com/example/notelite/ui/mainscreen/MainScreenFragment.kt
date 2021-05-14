package com.example.notelite.ui.mainscreen

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notelite.R
import com.example.notelite.data.NoteEntity
import com.example.notelite.data.SortOrder
import com.example.notelite.databinding.FragmentMainscreenBinding
import com.example.notelite.util.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.absoluteValue

@AndroidEntryPoint
class MainScreenFragment: Fragment(R.layout.fragment_mainscreen),
    NotesAdapter.OnItemClickListener {

    private val viewModel: MainScreenViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMainscreenBinding.bind(view)

        val notesAdapter = NotesAdapter(this)

        //Binds values to views
        binding.apply {
            notesRecyclerview.apply {
                adapter = notesAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
            newNoteButton.setOnClickListener {
                val action = MainScreenFragmentDirections.actionMainScreenFragmentToNewNoteScreenFragment()
                view.findNavController().navigate(action)
            }
        }

        //Observers recyclerview for changes
        viewModel.notes.observe(viewLifecycleOwner) { noteList ->
            notesAdapter.submitList(noteList)
        }


        //Changes the empty recyclerview message in case the user gets an empty note list as a result
        //of a search query - the strings have to be hard coded here for some reason, referencing the string
        //resources was getting bugs, it gives some warnings but works
        viewModel.searchquery.observe(viewLifecycleOwner) { currentSearchQuery ->
            val noNotesMessage = binding.emptyRecyclerviewMessage
            if (currentSearchQuery != "") noNotesMessage.text = "no matching results" else {
                noNotesMessage.text = "you don't have any notes yet. get started!"
            }
        }

        //Displays a message in case of an empty recyclerview (no notes on the database or no results from
        //search query). For some reason the message first appears visible when you install the app with the
        //pre-inserted dummy data from the database code, disappearing once you restart the app, but shouldn't
        //be a problem with normal usage
        viewModel.notes.observe(viewLifecycleOwner) { noteList ->
            val noNotesMessage = binding.emptyRecyclerviewMessage
            when (noteList.isEmpty()) {
                true -> noNotesMessage.isVisible = true
                false -> noNotesMessage.isVisible = false
            }
        }

        setHasOptionsMenu(true)
    }

    //Handles the click events from recyclerview interface
    override fun onNoteClick(note: NoteEntity) {
        val action = MainScreenFragmentDirections.actionMainScreenFragmentToNoteScreenFragment(note)
        view?.findNavController()?.navigate(action)
    }
    override fun onDeleteClick(note: NoteEntity) {
        val deleteConfirmationDialog = DialogConfirmationMainScreen(note)
        deleteConfirmationDialog.show(childFragmentManager, "deleteDialog")
    }


    //Create action menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu_fragment, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.onQueryTextChanged {
            viewModel.searchQuery.value = it
        }

    }

    //Changes sort order on the preferences datastore
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.sort_by_title -> {
            viewModel.onSortOrderSelected(SortOrder.BY_TITLE)
                true
            }
            R.id.sort_by_date_and_time -> {
                viewModel.onSortOrderSelected(SortOrder.BY_DATE)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

}
