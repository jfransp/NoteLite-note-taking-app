package com.example.notelite.ui.notescreen

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notelite.R
import com.example.notelite.databinding.FragmentNoteScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteScreenFragment: Fragment(R.layout.fragment_note_screen) {

    //This fragment doesn't really make any calls to it's viewModel, but it's confirmation dialog screen,
    //which is it's child fragment, does - it feels more appropriate to have the viewModel referencing the
    //parent fragment. Also, I was getting some weird bugs at some point trying to make viewModels for the
    //dialog screens and this seems to fix them - it looks better in general too I think.
    private val viewModel: NoteScreenViewModel by viewModels()

    private val args: NoteScreenFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentNoteScreenBinding.bind(view)

        val note = args.note

        //Breaks the note entity apart for subsequent Safe Args use towards the edit screen. If I try to
        //pass the note object through Safe Args into the EditNoteScreen again I get weird bugs for
        //some reason - breaking it apart here and requiring 5 arguments seems to avoid that, I don't know why.
        val id = note.id
        val title = note.title
        val date = note.timeOfCreationFormatted
        val text = note.text
        val timeofcreation = note.timeOfCreation

        binding.apply {

            noteTitle.text = note.title

            noteDate.text = note.timeOfCreationFormatted

            noteText.text = note.text

            noteText.movementMethod = ScrollingMovementMethod()

            notescreeDeleteButton.setOnClickListener {
                val deleteConfirmationDialog = DialogConfirmationNoteScreen(note)
                deleteConfirmationDialog.show(childFragmentManager, "deleteDialog")
            }

            editNoteButton.setOnClickListener {
                val action = NoteScreenFragmentDirections.actionNoteScreenFragmentToNoteEditScreenFragment(id = id,
                    title = title,
                    dateofcreation = date,
                    text = text,
                    timeofcreation = timeofcreation)

                view.findNavController().navigate(action)
            }

        }

    }

}
