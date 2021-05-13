package com.example.notelite.ui.newnotescreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.notelite.R
import com.example.notelite.data.NoteEntity
import com.example.notelite.databinding.FragmentNewNoteScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewNoteScreenFragment: Fragment(R.layout.fragment_new_note_screen) {

    private val viewModel: NewNoteScreenViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentNewNoteScreenBinding.bind(view)

        binding.addNoteButton.setOnClickListener {

            //Creates new note item with the user input from edit texts
            val newnote = NoteEntity(
                title = binding.noteTitle.text.toString(),
                text = binding.noteText.text.toString())

            //Doesn't do anything if the field are empty
            if (newnote.text.isNotEmpty() || newnote.title.isNotEmpty()) {
                viewModel.addNewNote(newnote)
            }

            //Navigates back to main screen
            val action = NewNoteScreenFragmentDirections.actionNewNoteScreenFragmentToMainScreenFragment()
            view.findNavController().navigate(action)

        }
    }

}