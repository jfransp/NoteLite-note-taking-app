package com.example.notelite.ui.mainscreen

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.notelite.R
import com.example.notelite.data.NoteEntity
import com.example.notelite.databinding.FragmentDialogDeleteConfirmationBinding

class DialogConfirmationMainScreen (private val note: NoteEntity): DialogFragment() {

    //As this dialog screen is a child fragment from the main screen, it uses the same viewModel
    private val viewModel: MainScreenViewModel by viewModels({requireParentFragment()})

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Making the background transparent applies rounded corners to dialog screen
        this.dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val rootView: View = inflater.inflate(R.layout.fragment_dialog_delete_confirmation, container, false)

        val binding = FragmentDialogDeleteConfirmationBinding.bind(rootView)

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.confirmButton.setOnClickListener {
            viewModel.deleteNote(note)
            dismiss()
        }

        return rootView
    }
}
