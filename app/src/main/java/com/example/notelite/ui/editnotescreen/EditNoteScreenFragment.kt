package com.example.notelite.ui.editnotescreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notelite.R
import com.example.notelite.data.NoteEntity
import com.example.notelite.databinding.FragmentEditNoteScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditNoteScreenFragment: Fragment(R.layout.fragment_edit_note_screen) {

    private val viewModel: EditNoteScreenViewModel by viewModels()

    private val args: EditNoteScreenFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentEditNoteScreenBinding.bind(view)

        binding.apply {

            noteTitle.setText(args.title)

            noteDate.text = args.dateofcreation

            noteText.setText(args.text)

            saveNoteButton.setOnClickListener {

                //Create updated version of note with the current user input
                val updatedNote = NoteEntity(
                    id = args.id,
                    title = noteTitle.text.toString(),
                    text = noteText.text.toString(),
                    timeOfCreation = args.timeofcreation,
                    timeOfModification = System.currentTimeMillis())

                //Call update function from the viewModel
                viewModel.updateNote(updatedNote)

                //Navigates back to the note screen passing the new note as argument - this way, the data
                //from the note screen is updated with the applied edits
                val action = EditNoteScreenFragmentDirections.actionNoteEditScreenFragmentToNoteScreenFragment(updatedNote)
                view.findNavController().navigate(action)

            }

        }

    }

}
